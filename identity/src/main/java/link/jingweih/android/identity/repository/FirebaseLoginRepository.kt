package link.jingweih.android.identity.repository

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.suspendCancellableCoroutine
import link.jingweih.android.identity.model.User
import javax.inject.Inject
import kotlin.coroutines.resume

class FirebaseLoginRepository @Inject constructor(private val auth: FirebaseAuth) {

    suspend fun login(email: String, password: String): User? {
        return suspendCancellableCoroutine { cont ->
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                val firebaseUser = task.result.user
                if (task.isSuccessful && firebaseUser != null) {
                    cont.resume(User.fromFirebaseUser(firebaseUser))
                } else {
                    cont.resume(null)
                }
            }
        }
    }

    suspend fun register(email: String, password: String): User? {
        return suspendCancellableCoroutine { cont ->
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                val firebaseUser = task.result.user
                if (task.isSuccessful && firebaseUser != null) {
                    firebaseUser.sendEmailVerification()
                    cont.resume(User.fromFirebaseUser(firebaseUser))
                } else {
                    cont.resume(null)
                }
            }
        }
    }
}