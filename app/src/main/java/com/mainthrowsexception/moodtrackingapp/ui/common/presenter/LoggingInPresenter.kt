package com.mainthrowsexception.moodtrackingapp.ui.common.presenter

import com.mainthrowsexception.moodtrackingapp.ui.common.contract.LoggingInContract
import com.mainthrowsexception.moodtrackingapp.util.UserUtil

class LoggingInPresenter(view: LoggingInContract.View) : LoggingInContract.Presenter {

    private var view: LoggingInContract.View? = view

    override fun doLogin(email: String, password: String) {
        var isLoginSuccessful = false
        var reason: String? = null
        if (!UserUtil.validateCredentials(email, password)) {
            reason = "Invalid"
        } else {
            isLoginSuccessful = true
        }

        view?.onLoginResult(isLoginSuccessful, reason)
    }
}
