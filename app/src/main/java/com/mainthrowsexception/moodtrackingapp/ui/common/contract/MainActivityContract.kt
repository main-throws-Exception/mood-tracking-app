package com.mainthrowsexception.moodtrackingapp.ui.common.contract

import com.mainthrowsexception.moodtrackingapp.ui.common.base.BaseFragment

interface MainActivityContract {
    interface Presenter {
        fun setHomeFragment()
    }

    interface View {
        fun setFragment(fragment: BaseFragment)
        fun startLoading()
        fun stopLoading()
    }
}
