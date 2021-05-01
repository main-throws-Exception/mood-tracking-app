package com.mainthrowsexception.moodtrackingapp.ui.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.Navigation
import com.google.android.gms.common.SignInButton
import com.mainthrowsexception.moodtrackingapp.R
import com.mainthrowsexception.moodtrackingapp.ui.common.base.BaseFragment
import com.mainthrowsexception.moodtrackingapp.ui.common.contract.LoginContract
import com.mainthrowsexception.moodtrackingapp.ui.common.presenter.LoginPresenter

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
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fragment_login__login_button -> navigationPresenter.addFragment(LoggingInFragment())
            R.id.fragment_login__sign_up_button -> navigationPresenter.addFragment(SigningUpFragment())
            R.id.fragment_login__google_sign_in_button -> Toast.makeText(context, "Not available", Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun getLayout(): Int {
        return R.layout.fragment_login
    }
}
