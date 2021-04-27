package com.mainthrowsexception.moodtrackingapp.ui.common.presenter

import com.mainthrowsexception.moodtrackingapp.ui.common.base.BaseFragment
import com.mainthrowsexception.moodtrackingapp.ui.common.contract.MainActivityContract
import com.mainthrowsexception.moodtrackingapp.ui.common.nav.FragmentNavigation

class MainActivityPresenter(view: MainActivityContract.View) : MainActivityContract.Presenter, FragmentNavigation.Presenter {

    private var view: MainActivityContract.View? = view

    override fun addFragment(fragment: BaseFragment) {
        view?.setFragment(fragment)
    }
}
