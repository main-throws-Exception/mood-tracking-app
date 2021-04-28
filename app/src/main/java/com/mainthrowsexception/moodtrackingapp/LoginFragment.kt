package com.mainthrowsexception.moodtrackingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.Navigation

class LoginFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_login, container, false)
        view.findViewById<AppCompatButton>(R.id.fragment_login__login_button).setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_loginFragment_to_fragment_logging_in)
        )
        view.findViewById<AppCompatButton>(R.id.fragment_login__sign_up_button).setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_loginFragment_to_fragment_signing_up)
        )
        return view
    }
}