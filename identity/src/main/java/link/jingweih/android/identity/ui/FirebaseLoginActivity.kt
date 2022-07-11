package link.jingweih.android.identity.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import link.jingweih.android.identity.databinding.ActivityLoginBinding

@AndroidEntryPoint
class FirebaseLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentUser = FirebaseAuth.getInstance().currentUser
        Log.d(
            "FirebaseLoginActivity",
            "name: ${currentUser?.displayName} \n" +
                    "email: ${currentUser?.email} \n" +
                    "uid: ${currentUser?.uid} \n " +
                    "isEmailVerified: ${currentUser?.isEmailVerified}"
        )
    }

}