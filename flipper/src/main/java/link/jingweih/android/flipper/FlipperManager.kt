package link.jingweih.android.flipper

import android.content.Context
import com.facebook.flipper.core.FlipperClient
import com.facebook.flipper.core.FlipperPlugin
import com.facebook.flipper.plugins.crashreporter.CrashReporterPlugin
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin
import com.facebook.flipper.plugins.databases.impl.SqliteDatabaseDriver
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin
import okhttp3.Interceptor
import java.io.File

internal class FlipperManager(
    private val appContext: Context,
    private val client: FlipperClient,
    private val configs: List<BaseConfig>
) {
    private var flipperInterceptor: Interceptor? = null

    init {
        val flipperPlugins = mutableListOf<FlipperPlugin>()

        configs.forEach { config ->
            when (config) {
                is BaseConfig.NetworkConfig -> flipperPlugins += NetworkFlipperPlugin()
                is BaseConfig.DatabasesConfig -> flipperPlugins +=
                    getDatabasesFlipperPlugin(
                        appContext,
                        config.dbPaths
                    )
                is BaseConfig.SharePreferencesConfig -> flipperPlugins +=
                   getSharedPreferencesFlipperPlugin(
                        appContext,
                        config.sfConfigs
                    )
                else -> {
                    flipperPlugins += CrashReporterPlugin.getInstance()
                    flipperPlugins += InspectorFlipperPlugin(
                        appContext,
                        DescriptorMapping.withDefaults()
                    )
                }
            }
        }

        flipperPlugins.forEach {
            client.addPlugin(it)
        }
        client.start()
    }

    /**
     * Return [DatabasesFlipperPlugin] plugin.
     */
    private fun getDatabasesFlipperPlugin(appContext: Context, dbPaths: List<File>): FlipperPlugin {
        return if (dbPaths.isEmpty()) {
            DatabasesFlipperPlugin(appContext)
        } else {
            DatabasesFlipperPlugin(SqliteDatabaseDriver(appContext) {
                val databaseFiles = mutableListOf<File>()
                for (databaseName in appContext.databaseList()) {
                    databaseFiles.add(appContext.getDatabasePath(databaseName))
                }
                databaseFiles.addAll(dbPaths)
                databaseFiles
            })
        }
    }

    /**
     * Return [SharedPreferencesFlipperPlugin] plugin.
     */
    private fun getSharedPreferencesFlipperPlugin(
        appContext: Context,
        sharedPreferencesFiles: Map<String, Int>
    ): FlipperPlugin {
        return if (sharedPreferencesFiles.isEmpty()) {
            SharedPreferencesFlipperPlugin(appContext)
        } else {
            val descriptors =
                mutableListOf<SharedPreferencesFlipperPlugin.SharedPreferencesDescriptor>()
            for ((filePath, mode) in sharedPreferencesFiles) {
                descriptors.add(
                    SharedPreferencesFlipperPlugin.SharedPreferencesDescriptor(
                        filePath,
                        mode
                    )
                )
            }
            SharedPreferencesFlipperPlugin(appContext, descriptors)
        }
    }

    /**
     * Returns the network interceptor that [Flipper] needs.
     */
    fun getNetworkInterceptor(): Interceptor? {
        val networkPluginConfig = (configs.find { it is BaseConfig.NetworkConfig }
            ?: throw NotConfiguredException("Network Plugin")) as BaseConfig.NetworkConfig
        return flipperInterceptor ?: synchronized(this) {
            if (flipperInterceptor == null) {
                val networkPlugin = client.getPluginByClass(NetworkFlipperPlugin::class.java)
                flipperInterceptor = FlipperOkhttpInterceptor(
                    networkPlugin,
                    networkPluginConfig.isMockResponseEnabled
                )
            }
            return flipperInterceptor
        }
    }
}

