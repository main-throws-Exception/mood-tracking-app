package com.mainthrowsexception.moodtrackingapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.Navigation
import androidx.navigation.findNavController

class LoggingInFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_logging_in, container, false)
        val emailText: EditText = view.findViewById(R.id.fragment_logging_in__email)
        val passwordText: EditText = view.findViewById(R.id.fragment_logging_in__password)
        val toast: Toast = Toast.makeText(activity, "Fill all the fields first", Toast.LENGTH_SHORT)
        view.findViewById<AppCompatButton>(R.id.fragment_logging_in___logging_in_button).setOnClickListener {
            if (emailText.text.toString().trim().isEmpty() || passwordText.text.toString().trim().isEmpty()) {
                toast.cancel()
                toast.show()
            } else {
                view.findNavController().navigate(R.id.action_fragment_logging_in_to_currentDayFragment)
            }
        }
        return view
    }
}