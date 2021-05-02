package com.mainthrowsexception.moodtrackingapp.ui.common.contract

import android.content.Intent
import androidx.fragment.app.FragmentActivity

interface LoginContract {
    interface Presenter {
        fun getGoogleSignInIntent(activity: FragmentActivity, webClientId: String): Intent
        fun firebaseAuthWithGoogle(activity: FragmentActivity, idToken: String)
    }

    interface View {
        fun onGoogleSignInResult(result: Boolean)
    }
}
