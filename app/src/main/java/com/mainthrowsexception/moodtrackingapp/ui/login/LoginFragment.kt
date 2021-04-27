package com.mainthrowsexception.moodtrackingapp.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.Navigation
import com.mainthrowsexception.moodtrackingapp.R
import com.mainthrowsexception.moodtrackingapp.ui.common.base.BaseFragment
import com.mainthrowsexception.moodtrackingapp.ui.common.contract.LoginContract
import com.mainthrowsexception.moodtrackingapp.ui.common.presenter.LoginPresenter

class LoginFragment : BaseFragment(), LoginContract.View {

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
        presenter = LoginPresenter(this)
        rootView.findViewById<AppCompatButton>(R.id.fragment_login__login_button).setOnClickListener {
            navigationPresenter.addFragment(LoggingInFragment())
        }
        rootView.findViewById<AppCompatButton>(R.id.fragment_login__sign_up_button).setOnClickListener {
            navigationPresenter.addFragment(SigningUpFragment())
        }
    }

    override fun getLayout(): Int {
        return R.layout.fragment_login
    }
}
