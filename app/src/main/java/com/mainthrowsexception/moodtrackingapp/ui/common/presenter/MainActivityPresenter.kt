package com.mainthrowsexception.moodtrackingapp.ui.common.presenter

import com.mainthrowsexception.moodtrackingapp.ui.common.base.BaseFragment
import com.mainthrowsexception.moodtrackingapp.ui.common.contract.MainActivityContract
import com.mainthrowsexception.moodtrackingapp.ui.common.nav.FragmentNavigation
import com.mainthrowsexception.moodtrackingapp.ui.login.LoginFragment

class MainActivityPresenter(view: MainActivityContract.View) : MainActivityContract.Presenter, FragmentNavigation.Presenter {

    private var view: MainActivityContract.View? = view

    override fun addFragment(fragment: BaseFragment) {
        view?.setFragment(fragment)
    }

    override fun addFragment(fragment: BasePreferenceFragment) {
        view?.setFragment(fragment)
    }

    override fun startLoading() {
        view?.startLoading()
    }

    override fun stopLoading() {
        view?.stopLoading()
    }

    override fun setHomeFragment() {
        view?.setFragment(LoginFragment())
    }
}
