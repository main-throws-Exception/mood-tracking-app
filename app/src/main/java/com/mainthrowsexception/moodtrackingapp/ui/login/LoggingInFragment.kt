package com.mainthrowsexception.moodtrackingapp.ui.login

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.FragmentContainerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mainthrowsexception.moodtrackingapp.R
import com.mainthrowsexception.moodtrackingapp.ui.calendar.CalendarFragment
import com.mainthrowsexception.moodtrackingapp.ui.common.base.BaseFragment
import com.mainthrowsexception.moodtrackingapp.ui.common.contract.LoggingInContract
import com.mainthrowsexception.moodtrackingapp.ui.common.presenter.LoggingInPresenter

class LoggingInFragment : BaseFragment(), LoggingInContract.View, View.OnClickListener {

    private lateinit var presenter: LoggingInPresenter
    private lateinit var emailText: EditText
    private lateinit var passwordText: EditText
    private lateinit var loginButton: AppCompatButton
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var bottomNavigationViewButton: AppCompatButton
    private lateinit var navHostFragmentContainerView: FragmentContainerView
    private var loginFailedToast: Toast? = null


//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        // Inflate the layout for this fragment
//        val view: View = inflater.inflate(R.layout.fragment_logging_in, container, false)
//        val emailText: EditText = view.findViewById(R.id.fragment_logging_in__email)
//        val passwordText: EditText = view.findViewById(R.id.fragment_logging_in__password)
//        val toast: Toast = Toast.makeText(activity, "Fill all the fields first", Toast.LENGTH_SHORT)
//        val bottomNavigationView: BottomNavigationView = requireActivity().findViewById(R.id.bottom_nav_view)
//        val bottomNavigationViewButton: AppCompatButton = requireActivity().findViewById(R.id.bottom_nav_view_button)
//        val navHostFragmentContainerView: FragmentContainerView = requireActivity().findViewById(R.id.activity_main__nav_host_fragment)
//
//        view.findViewById<AppCompatButton>(R.id.fragment_logging_in___logging_in_button).setOnClickListener {
//            if (emailText.text.toString().trim().isEmpty() || passwordText.text.toString().trim().isEmpty()) {
//                toast.cancel()
//                toast.show()
//            } else {
//                view.findNavController().navigate(R.id.action_fragment_logging_in_to_currentDayFragment)
//                navHostFragmentContainerView.setPadding(0, 0, 0, (resources.displayMetrics.density * 60 + 0.5f).toInt())
//                bottomNavigationView.visibility = View.VISIBLE
//                bottomNavigationViewButton.visibility = View.VISIBLE
//            }
//        }
//
//        return view
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emailText = rootView.findViewById(R.id.fragment_logging_in__email)
        passwordText = rootView.findViewById(R.id.fragment_logging_in__password)
        loginButton = rootView.findViewById(R.id.fragment_logging_in___logging_in_button)
//        bottomNavigationView = requireActivity().findViewById(R.id.bottom_nav_view)
//        bottomNavigationViewButton = requireActivity().findViewById(R.id.bottom_nav_view_button)
//        navHostFragmentContainerView = requireActivity().findViewById(R.id.activity_main__nav_host_fragment)

        Log.i("LoggingIn", "Setting up the click listener")
        loginButton.setOnClickListener(this)

        presenter = LoggingInPresenter(this)

        navigationPresenter.stopLoading()
    }

    override fun onClick(view: View) {
        Log.i("LoggingIn", "Button clicked")
        loginButton.isEnabled = false
        presenter.doLogin(emailText.text.toString(), passwordText.text.toString())
    }

    override fun onLoginResult(result: Boolean, reason: String?) {
        Log.i("LoggingIn", "OnLoginResult result - $result reason - $reason")
        val mContext = activity?.applicationContext
        Log.i("LoggingIn", "activity: $activity, Context: $mContext")
        loginButton.isEnabled = true
        if (result) {
//            navHostFragmentContainerView.setPadding(0, 0, 0, (resources.displayMetrics.density * 60 + 0.5f).toInt())
//            bottomNavigationView.visibility = View.VISIBLE
//            bottomNavigationViewButton.visibility = View.VISIBLE
            Toast.makeText(activity?.applicationContext, "Success", Toast.LENGTH_SHORT).show() // TEMPORARY MEASURE
            navigationPresenter.addFragment(CalendarFragment())
        } else {
            loginFailedToast?.cancel()

            var toastMsg = "Error"
            when (reason) {
                "Invalid" -> toastMsg = "Invalid email or password"
                "Wrong" -> toastMsg = "Wrong email or password"
                "Failed" -> toastMsg = "Login failed"
            }
            loginFailedToast = Toast.makeText(activity?.applicationContext, toastMsg, Toast.LENGTH_SHORT)

            Log.i("LoggingIn", "toast: $loginFailedToast.")

            loginFailedToast?.show()
        }
    }

    override fun getLayout(): Int {
        return R.layout.fragment_logging_in
    }
}
