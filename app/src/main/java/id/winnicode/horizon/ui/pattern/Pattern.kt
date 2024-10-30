package id.winnicode.horizon.ui.pattern

import android.util.Patterns

object Pattern {

    fun usernamePattern(username: String): Boolean {
        return Regex("^[A-Za-z]{2,20}$")
            .matches(username)
    }

    fun namePattern(name: String): Boolean {
        return Regex("^(?=[a-z0-9._]{4,12}\$)(?!.*[_.]{2})[^_.].*[^_.]\$")
            .matches(name)
    }

    fun emailPattern(email: String): Boolean {
        return email.isNotEmpty() &&
                Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun passwordPattern(password: String): Boolean {
        return password.length >= 8
    }
}

