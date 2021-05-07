package com.mainthrowsexception.moodtrackingapp.ui.common.presenter

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mainthrowsexception.moodtrackingapp.database.model.User
import com.mainthrowsexception.moodtrackingapp.ui.calendar.CalendarFragment
import com.mainthrowsexception.moodtrackingapp.ui.common.contract.LoginContract

class LoginPresenter(view: LoginContract.View) : LoginContract.Presenter {

    private var view: LoginContract.View? = view
    private lateinit var googleSignInClient: GoogleSignInClient
    private val auth = FirebaseAuth.getInstance()
    private val databaseRef = Firebase.database("https://goodmood-c69a1-default-rtdb.europe-west1.firebasedatabase.app/").reference

    override fun getGoogleSignInIntent(activity: FragmentActivity, webClientId: String): Intent {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(webClientId)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(activity, gso)

        return googleSignInClient.signInIntent
    }

    override fun firebaseAuthWithGoogle(activity: FragmentActivity, idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    Log.d("GoogleActivity", "signInWithCredential:success")
                    view?.onGoogleSignInSuccess()
                    if (task.result?.additionalUserInfo?.isNewUser == true) {
                        writeUser(task.result!!.user)
                    }
                } else {
                    Log.w("GoogleActivity", "signInWithCredential:failure")
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
}
