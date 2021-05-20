package com.mainthrowsexception.moodtrackingapp.ui.common.contract

import com.mainthrowsexception.moodtrackingapp.ui.common.base.BaseFragment
import com.mainthrowsexception.moodtrackingapp.ui.common.base.BasePreferenceFragment

interface MainActivityContract {
    interface Presenter {
        fun setHomeFragment()
    }

    interface View {
        fun setFragment(fragment: BaseFragment)
        fun setFragment(fragment: BasePreferenceFragment)
        fun startLoading()
        fun stopLoading()
    }
}
