package link.jingweih.android.flipper

import java.io.File

class FlipperConfig {

    fun addBasePlugin() = Unit

    fun addNetworkPlugin(isMockResponseEnabled: Boolean = true) = Unit

    fun addDatabasesPlugin(dbPaths: List<File> = listOf()) = Unit

    fun addSharePreferencesPlugin(sfConfigs: Map<String, Int> = mapOf()) = Unit
}