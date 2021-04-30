package com.mainthrowsexception.moodtrackingapp.ui.common.contract

interface SigningUpContract {
    interface Presenter {
        fun doSignUp(email: String, password: String)
    }

    interface View {
        fun onSignUpResult(result: Boolean, reason: String? = null)
    }
}
