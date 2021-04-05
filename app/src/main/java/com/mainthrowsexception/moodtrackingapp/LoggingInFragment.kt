package com.mainthrowsexception.moodtrackingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.Navigation

class LoggingInFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_logging_in, container, false)
        view.findViewById<AppCompatButton>(R.id.fragment_logging_in___logging_in_button).setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_fragment_logging_in_to_currentDayFragment)
        )
        return view
    }
}