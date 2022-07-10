package link.jingweih.android.identity.repository

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@Singleton
class FirebaseLoginRepository @Inject constructor(private val auth: FirebaseAuth) {

    suspend fun login(email: String, password: String): AuthResult {
        return suspendCancellableCoroutine { cont ->
            auth.signInWithEmailAndPassword(email, password).addOnSuccessListener { authResult ->
                cont.resume(authResult)
            }.addOnFailureListener {
                cont.resumeWithException(it)
            }
        }
    }

    suspend fun register(email: String, password: String): AuthResult {
        return suspendCancellableCoroutine { cont ->
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { authResult ->
                    val firebaseUser = authResult.user
                    firebaseUser?.sendEmailVerification()
                    cont.resume(authResult)
                }.addOnFailureListener {
                    cont.resumeWithException(it)
                }
        }
    }

    suspend fun sendPasswordResetEmail(email: String): Boolean {
        return suspendCancellableCoroutine { cont ->
            auth.sendPasswordResetEmail(email)
                .addOnSuccessListener {
                    cont.resume(true)
                }.addOnFailureListener {
                    cont.resumeWithException(it)
                }
        }
    }
}