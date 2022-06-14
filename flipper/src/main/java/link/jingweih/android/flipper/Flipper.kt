package link.jingweih.android.flipper

import android.content.Context
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.soloader.SoLoader
import okhttp3.Interceptor

object Flipper {
    @Volatile
    private var flipperManager: FlipperManager? = null

    fun init(context: Context, config: FlipperConfig.() -> Unit) {
        val flipperConfig = FlipperConfig()
        config(flipperConfig)
        SoLoader.init(context, false)
        synchronized(this) {
            if (flipperManager == null) {
                flipperManager = FlipperManager(
                    context,
                    AndroidFlipperClient.getInstance(context),
                    flipperConfig.configs
                )
            }
        }
    }

    fun getNetworkInterceptor(): Interceptor? {
        return flipperManager?.getNetworkInterceptor()
    }


}