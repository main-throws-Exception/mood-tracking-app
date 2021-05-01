package com.mainthrowsexception.moodtrackingapp.ui.login

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.mainthrowsexception.moodtrackingapp.R
import com.mainthrowsexception.moodtrackingapp.ui.common.base.BaseFragment
import com.mainthrowsexception.moodtrackingapp.ui.common.contract.SigningUpContract
import com.mainthrowsexception.moodtrackingapp.ui.common.presenter.SigningUpPresenter

class SigningUpFragment : BaseFragment(), SigningUpContract.View, View.OnClickListener {

    private lateinit var presenter: SigningUpPresenter
    private lateinit var emailText: EditText
    private lateinit var passwordText: EditText
    private lateinit var signUpButton: AppCompatButton
    private var signUpFailedToast: Toast? = null

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        // Inflate the layout for this fragment
//        val view: View = inflater.inflate(R.layout.fragment_signing_up, container, false)
//        val emailText: EditText = view.findViewById(R.id.fragment_signing_up__email)
//        val passwordText: EditText = view.findViewById(R.id.fragment_signing_up__password)
//        val toast: Toast = Toast.makeText(activity, "Fill all the fields first", Toast.LENGTH_SHORT)
//        view.findViewById<AppCompatButton>(R.id.fragment_signing_up___signing_up_button).setOnClickListener {
//            if (emailText.text.toString().trim().isEmpty() || passwordText.text.toString().trim().isEmpty()) {
//                toast.cancel()
//                toast.show()
//            } else {
//                view.findNavController().navigate(R.id.action_fragment_signing_up_to_loginFragment)
//            }
//        }
//        return view
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emailText = view.findViewById(R.id.fragment_signing_up__email)
        passwordText = view.findViewById(R.id.fragment_signing_up__password)
        signUpButton = rootView.findViewById(R.id.fragment_signing_up___signing_up_button)

        signUpButton.setOnClickListener(this)

        presenter = SigningUpPresenter(this)
    }

    override fun getLayout(): Int {
        return R.layout.fragment_signing_up
    }

    override fun onSignUpResult(result: Boolean, reason: String?) {
        Log.i("SigningUp", "OnSignUpResult result - $result reason - $reason")
        signUpButton.isEnabled = true
        if (result) {
            Toast.makeText(context?.applicationContext, "Success", Toast.LENGTH_SHORT).show() // TEMPORARY MEASURE
            navigationPresenter.addFragment(LoginFragment())
        } else {
            signUpFailedToast?.cancel()

            var toastMsg = "Error"
            when (reason) {
                "Invalid" -> toastMsg = "Invalid email or password"
                "Failed" -> toastMsg = "Registration failed"
                "Cancelled" -> toastMsg = "Registration cancelled"
            }
            signUpFailedToast = Toast.makeText(context?.applicationContext, toastMsg, Toast.LENGTH_SHORT)

            signUpFailedToast?.show()
        }
    }

    override fun onClick(v: View?) {
        signUpButton.isEnabled = false
        presenter.doSignUp(emailText.text.toString(), passwordText.text.toString())
    }
}
