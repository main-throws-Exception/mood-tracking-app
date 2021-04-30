package com.mainthrowsexception.moodtrackingapp.ui.common.presenter

import com.mainthrowsexception.moodtrackingapp.ui.common.contract.SigningUpContract
import com.mainthrowsexception.moodtrackingapp.util.UserUtil

class SigningUpPresenter(view: SigningUpContract.View) : SigningUpContract.Presenter {

    private var view: SigningUpContract.View? = view

    override fun doSignUp(email: String, password: String) {
        var isSignUpSuccessful = false
        var reason: String? = null
        if (!UserUtil.validateCredentials(email, password)) {
            reason = "Invalid"
        } else {
            isSignUpSuccessful = true
        }

        view?.onSignUpResult(isSignUpSuccessful, reason)
    }
}
