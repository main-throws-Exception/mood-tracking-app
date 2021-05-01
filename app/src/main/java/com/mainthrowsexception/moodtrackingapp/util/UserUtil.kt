package com.mainthrowsexception.moodtrackingapp.util

import android.text.TextUtils

class UserUtil {

    companion object {
        private const val passwordMinLength: Int = 8

        fun validateCredentials(email: String, password: String): Boolean {

            return !TextUtils.isEmpty(password) &&
                    password.length >= passwordMinLength &&
                    !TextUtils.isEmpty(email) &&
                    android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }
}
