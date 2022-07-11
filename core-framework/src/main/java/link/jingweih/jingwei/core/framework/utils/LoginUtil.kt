package link.jingweih.jingwei.core.framework.utils

import android.util.Patterns

const val PASSWORD_LENGTH = 5
object LoginUtil {

    fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // A placeholder password validation check
    fun isPasswordValid(password: String): Boolean {
        return password.length > PASSWORD_LENGTH
    }

    fun isSamePassword(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }
}