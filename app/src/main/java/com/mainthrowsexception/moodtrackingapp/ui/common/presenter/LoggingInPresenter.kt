package com.mainthrowsexception.moodtrackingapp.ui.common.presenter

import com.google.firebase.auth.FirebaseAuth
import com.mainthrowsexception.moodtrackingapp.ui.common.contract.LoggingInContract
import com.mainthrowsexception.moodtrackingapp.util.UserUtil

class LoggingInPresenter(view: LoggingInContract.View) : LoggingInContract.Presenter {

    private var view: LoggingInContract.View? = view
    private val auth = FirebaseAuth.getInstance()

    override fun doLogin(email: String, password: String) {
        var isLoginSuccessful = false
        var reason: String? = null
        if (!UserUtil.validateCredentials(email, password)) {
            reason = "Invalid"
            showResult(isLoginSuccessful, reason)
        } else {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    isLoginSuccessful = true
                } else {
                    reason = "Failed"
                }

                showResult(isLoginSuccessful, reason)
            }.addOnCanceledListener {
                isLoginSuccessful = false
                reason = "Cancelled"

                showResult(isLoginSuccessful, reason)
            }
        }

    }

    override fun showResult(result: Boolean, reason: String?) {
        view?.onLoginResult(result, reason)
    }
}
