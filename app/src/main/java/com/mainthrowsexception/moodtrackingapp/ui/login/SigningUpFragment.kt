package com.mainthrowsexception.moodtrackingapp.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.findNavController
import com.mainthrowsexception.moodtrackingapp.R
import com.mainthrowsexception.moodtrackingapp.ui.common.base.BaseFragment

class SigningUpFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_signing_up, container, false)
        val emailText: EditText = view.findViewById(R.id.fragment_signing_up__email)
        val passwordText: EditText = view.findViewById(R.id.fragment_signing_up__password)
        val toast: Toast = Toast.makeText(activity, "Fill all the fields first", Toast.LENGTH_SHORT)
        view.findViewById<AppCompatButton>(R.id.fragment_signing_up___signing_up_button).setOnClickListener {
            if (emailText.text.toString().trim().isEmpty() || passwordText.text.toString().trim().isEmpty()) {
                toast.cancel()
                toast.show()
            } else {
                view.findNavController().navigate(R.id.action_fragment_signing_up_to_loginFragment)
            }
        }
        return view
    }

    override fun getLayout(): Int {
        return R.layout.fragment_signing_up
    }
}
