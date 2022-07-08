package link.jingweih.android.identity.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

internal const val EXTRA_APPLICATION_NAME = "application_name"

class FirebaseLoginActivityContract : ActivityResultContract<String, Boolean>() {

    override fun createIntent(context: Context, appName: String): Intent {
        return Intent(context, FirebaseLoginActivity::class.java).apply {
            putExtra(EXTRA_APPLICATION_NAME, appName)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
        return resultCode == Activity.RESULT_OK
    }
}