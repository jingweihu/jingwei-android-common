package link.jingweih.jingwei.core.framework.utils

import android.util.Patterns

object LoginUtil {

    fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // A placeholder password validation check
    fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

    fun isSamePassword(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }
}