package com.mainthrowsexception.moodtrackingapp.ui.common.contract

import android.content.Intent
import androidx.fragment.app.FragmentActivity
import com.google.firebase.auth.FirebaseUser

interface LoginContract {
    interface Presenter {
        fun getGoogleSignInIntent(activity: FragmentActivity, webClientId: String): Intent
        fun firebaseAuthWithGoogle(activity: FragmentActivity, idToken: String)
        fun writeUser(user: FirebaseUser?)
    }

    interface View {
        fun onGoogleSignInSuccess()
    }
}
