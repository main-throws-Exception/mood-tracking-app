package com.mainthrowsexception.moodtrackingapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.mainthrowsexception.moodtrackingapp.R
import com.mainthrowsexception.moodtrackingapp.ui.calendar.CalendarFragment
import com.mainthrowsexception.moodtrackingapp.ui.common.base.BaseFragment
import com.mainthrowsexception.moodtrackingapp.ui.common.contract.LoginContract
import com.mainthrowsexception.moodtrackingapp.ui.common.presenter.LoginPresenter
import com.mainthrowsexception.moodtrackingapp.ui.currentday.CurrentDayFragment

class LoginFragment : BaseFragment(), LoginContract.View, View.OnClickListener {

    private lateinit var presenter: LoginPresenter

//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
//                              savedInstanceState: Bundle?): View {
//        // Inflate the layout for this fragment
//        val view: View = inflater.inflate(R.layout.fragment_login, container, false)
//        view.findViewById<AppCompatButton>(R.id.fragment_login__login_button).setOnClickListener(
//            Navigation.createNavigateOnClickListener(R.id.action_loginFragment_to_fragment_logging_in)
//        )
//        view.findViewById<AppCompatButton>(R.id.fragment_login__sign_up_button).setOnClickListener(
//            Navigation.createNavigateOnClickListener(R.id.action_loginFragment_to_fragment_signing_up)
//        )
//        return view
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rootView.findViewById<AppCompatButton>(R.id.fragment_login__login_button).setOnClickListener(this)
        rootView.findViewById<AppCompatButton>(R.id.fragment_login__sign_up_button).setOnClickListener(this)
        rootView.findViewById<SignInButton>(R.id.fragment_login__google_sign_in_button).setOnClickListener(this)


        presenter = LoginPresenter(this)

//        navigationPresenter.stopLoading()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fragment_login__login_button -> navigationPresenter.addFragment(LoggingInFragment())
            R.id.fragment_login__sign_up_button -> navigationPresenter.addFragment(SigningUpFragment())
            R.id.fragment_login__google_sign_in_button -> onGoogleSignInStart()
        }
    }

    override fun onGoogleSignInStart() {
        navigationPresenter.startLoading()
        startActivityForResult(presenter.getGoogleSignInIntent(requireActivity(), getString(R.string.default_web_client_id)), RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d("GoogleActivity", "firebaseAuthWithGoogle:" + account.id)
                presenter.firebaseAuthWithGoogle(requireActivity(), account.idToken!!)
            } catch (e: ApiException) {
                navigationPresenter.stopLoading()
                // Google Sign In failed, update UI appropriately
                Log.w("GoogleActivity", "Google sign in failed", e)
            }
        }
    }

    override fun getLayout(): Int {
        return R.layout.fragment_login
    }

    override fun onGoogleSignInSuccess() {
        navigationPresenter.stopLoading()
        Toast.makeText(activity?.applicationContext, "Success", Toast.LENGTH_SHORT).show()
        navigationPresenter.displayNav()
        navigationPresenter.addFragment(CurrentDayFragment())
    }

    companion object {
        private const val RC_SIGN_IN = 9001
    }
}
