package com.mainthrowsexception.moodtrackingapp.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mainthrowsexception.moodtrackingapp.R

class LoggingInFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_logging_in, container, false)
        val emailText: EditText = view.findViewById(R.id.fragment_logging_in__email)
        val passwordText: EditText = view.findViewById(R.id.fragment_logging_in__password)
        val toast: Toast = Toast.makeText(activity, "Fill all the fields first", Toast.LENGTH_SHORT)
        val bottomNavigationView: BottomNavigationView = requireActivity().findViewById(R.id.bottom_nav_view)
        val bottomNavigationViewButton: AppCompatButton = requireActivity().findViewById(R.id.bottom_nav_view_button)
        val navHostFragmentContainerView: FragmentContainerView = requireActivity().findViewById(R.id.activity_main__nav_host_fragment)
        view.findViewById<AppCompatButton>(R.id.fragment_logging_in___logging_in_button).setOnClickListener {
            if (emailText.text.toString().trim().isEmpty() || passwordText.text.toString().trim().isEmpty()) {
                toast.cancel()
                toast.show()
            } else {
                view.findNavController().navigate(R.id.action_fragment_logging_in_to_currentDayFragment)
                navHostFragmentContainerView.setPadding(0, 0, 0, (resources.displayMetrics.density * 60 + 0.5f).toInt())
                bottomNavigationView.visibility = View.VISIBLE
                bottomNavigationViewButton.visibility = View.VISIBLE
            }
        }
        return view
    }
}
