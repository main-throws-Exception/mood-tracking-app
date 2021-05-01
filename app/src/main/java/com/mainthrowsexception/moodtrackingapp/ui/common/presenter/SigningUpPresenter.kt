package com.mainthrowsexception.moodtrackingapp.ui.common.presenter

import com.google.firebase.auth.FirebaseAuth
import com.mainthrowsexception.moodtrackingapp.ui.common.contract.SigningUpContract
import com.mainthrowsexception.moodtrackingapp.util.UserUtil

class SigningUpPresenter(view: SigningUpContract.View) : SigningUpContract.Presenter {

    private var view: SigningUpContract.View? = view
    private val auth = FirebaseAuth.getInstance()

    override fun doSignUp(email: String, password: String) {
        val emailTrim = email.trim()
        val passwordTrim = password.trim()

        var isSignUpSuccessful = false
        var reason: String? = null
        if (!UserUtil.validateCredentials(emailTrim, passwordTrim)) {
            reason = "Invalid"
            showResult(isSignUpSuccessful, reason)
        } else {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    isSignUpSuccessful = true
                } else {
                    reason = "Failed"
                }

                showResult(isSignUpSuccessful, reason)
            }.addOnCanceledListener {
                isSignUpSuccessful = false
                reason = "Cancelled"

                showResult(isSignUpSuccessful, reason)
            }
        }
    }

    override fun showResult(result: Boolean, reason: String?) {
        view?.onSignUpResult(result, reason)
    }


}
