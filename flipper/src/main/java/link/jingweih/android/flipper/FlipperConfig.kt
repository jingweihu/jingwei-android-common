package link.jingweih.android.flipper

import java.io.File

class FlipperConfig{
    internal val configs = mutableListOf<BaseConfig>()

    fun addBasePlugin() {
        configs.add(BaseConfig())
    }

    fun addNetworkPlugin(isMockResponseEnabled: Boolean = true) {
        configs.add(BaseConfig.NetworkConfig(isMockResponseEnabled))
    }

    fun addDatabasesPlugin(dbPaths: List<File> = listOf()) {
        configs.add(BaseConfig.DatabasesConfig(dbPaths))
    }

    fun addSharePreferencesPlugin(sfConfigs: Map<String, Int> = mapOf()) {
        configs.add(BaseConfig.SharePreferencesConfig(sfConfigs))
    }
}