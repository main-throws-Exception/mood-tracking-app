package com.mainthrowsexception.moodtrackingapp.ui.common.contract

import com.google.firebase.auth.FirebaseUser

interface SigningUpContract {
    interface Presenter {
        fun doSignUp(email: String, password: String)
        fun writeUser(user: FirebaseUser?)
    }

    interface View {
        fun onSignUpResult(result: Boolean, reason: String? = null)
    }
}
