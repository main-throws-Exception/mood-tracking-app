package com.mainthrowsexception.moodtrackingapp.ui.common.contract

import com.mainthrowsexception.moodtrackingapp.ui.common.base.BaseFragment

interface MainActivityContract {
    interface Presenter

    interface View {
        fun setFragment(fragment: BaseFragment)
    }
}
