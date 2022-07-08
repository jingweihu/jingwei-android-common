package link.jingweih.android.identity.model

import com.google.firebase.auth.FirebaseUser

data class User(
    val email: String?,
    val isEmailVerified: Boolean,
    val name: String?,
    val uid: String
) {

    companion object {

        fun fromFirebaseUser(firebaseUser: FirebaseUser): User {
            return User(
                email = firebaseUser.email,
                isEmailVerified = firebaseUser.isEmailVerified,
                name = firebaseUser.displayName,
                uid = firebaseUser.uid
            )
        }

    }

}
