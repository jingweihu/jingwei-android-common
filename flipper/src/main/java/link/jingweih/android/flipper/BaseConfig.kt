package link.jingweih.android.flipper

import java.io.File

internal open class BaseConfig {

    internal data class NetworkConfig(
        var isMockResponseEnabled: Boolean = true
    ) : BaseConfig()

    internal data class DatabasesConfig(
        var dbPaths: List<File> = listOf()
    ) : BaseConfig()

    internal data class SharePreferencesConfig(
        var sfConfigs: Map<String, Int> = mapOf()
    ) : BaseConfig()
}