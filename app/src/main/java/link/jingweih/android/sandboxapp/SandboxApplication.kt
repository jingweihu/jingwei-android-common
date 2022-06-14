package link.jingweih.android.sandboxapp

import android.app.Application
import link.jingweih.android.flipper.Flipper

class SandboxApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initFlipper()
    }

    private fun initFlipper() {
        val sharePreferences = mapOf(
            packageName + "_preferences" to MODE_PRIVATE
        )
        Flipper.init(this) {
            addBasePlugin()
            addDatabasesPlugin()
            addNetworkPlugin(true)
            addSharePreferencesPlugin(sharePreferences)
        }
    }
}