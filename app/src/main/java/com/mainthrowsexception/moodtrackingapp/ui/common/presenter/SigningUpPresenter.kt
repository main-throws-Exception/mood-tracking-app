package com.mainthrowsexception.moodtrackingapp.ui.common.presenter

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mainthrowsexception.moodtrackingapp.database.model.User
import com.mainthrowsexception.moodtrackingapp.ui.common.contract.SigningUpContract
import com.mainthrowsexception.moodtrackingapp.util.UserUtil

class SigningUpPresenter(view: SigningUpContract.View) : SigningUpContract.Presenter {

    private var view: SigningUpContract.View? = view
    private val auth = FirebaseAuth.getInstance()
    private val databaseRef = Firebase.database("https://goodmood-c69a1-default-rtdb.europe-west1.firebasedatabase.app/").reference

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
                    writeUser(it.result!!.user)
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

    override fun writeUser(user: FirebaseUser?) {
        if (user == null) {
            return
        }

        val email = user.email
        val username = if (email?.contains("@")!!) {
            email.split("@")[0]
        } else {
            email
        }

        val newUser = User(user.uid, username, email)

        databaseRef.child("users").child(newUser.uid).setValue(newUser)
    }

    override fun showResult(result: Boolean, reason: String?) {
        view?.onSignUpResult(result, reason)
    }
}
