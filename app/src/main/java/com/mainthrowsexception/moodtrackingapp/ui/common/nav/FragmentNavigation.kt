package com.mainthrowsexception.moodtrackingapp.ui.common.nav

import com.mainthrowsexception.moodtrackingapp.ui.common.base.BaseFragment
import com.mainthrowsexception.moodtrackingapp.ui.common.base.BasePreferenceFragment

interface FragmentNavigation {
    interface View {
        fun attachPresenter(presenter: Presenter)
    }

    interface Presenter {
        fun addFragment(fragment: BaseFragment)
        fun addFragment(fragment: BasePreferenceFragment)
        fun startLoading()
        fun stopLoading()
        fun displayNav()
        fun hideNav()
    }
}
