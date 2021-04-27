package com.mainthrowsexception.moodtrackingapp.ui.common.contract

interface LoggingInContract {
    interface Presenter {
        fun doLogin(email: String, password: String)
    }

    interface View {
        fun onLoginResult(result: Boolean, reason: String? = null)
    }
}
