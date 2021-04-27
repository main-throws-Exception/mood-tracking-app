package com.mainthrowsexception.moodtrackingapp.ui.common.presenter

import com.mainthrowsexception.moodtrackingapp.ui.common.contract.LoginContract

class LoginPresenter(view: LoginContract.View) : LoginContract.Presenter {

    private var view: LoginContract.View? = view

}
