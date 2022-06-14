package link.jingweih.android.flipper

import java.io.File

class FlipperConfig{

    fun addBasePlugin() {
    }

    fun addNetworkPlugin(isMockResponseEnabled: Boolean = true) {
    }

    fun addDatabasesPlugin(dbPaths: List<File> = listOf()) {
    }

    fun addSharePreferencesPlugin(sfConfigs: Map<String, Int> = mapOf()) {
    }
}