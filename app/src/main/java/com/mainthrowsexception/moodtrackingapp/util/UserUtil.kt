package com.mainthrowsexception.moodtrackingapp.util

import android.text.TextUtils

class UserUtil {

    companion object {
        private const val passwordMinLength: Int = 8

        fun validateCredentials(email: String, password: String): Boolean {
            val emailTrim = email.trim()
            val passwordTrim = password.trim()

            return !TextUtils.isEmpty(passwordTrim) &&
                    passwordTrim.length >= passwordMinLength &&
                    !TextUtils.isEmpty(emailTrim) &&
                    android.util.Patterns.EMAIL_ADDRESS.matcher(emailTrim).matches()
        }
    }
}
