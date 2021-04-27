package com.mainthrowsexception.moodtrackingapp.ui.common.nav

import com.mainthrowsexception.moodtrackingapp.ui.common.base.BaseFragment

interface FragmentNavigation {
    interface View {
        fun attachPresenter(presenter: Presenter)
    }

    interface Presenter {
        fun addFragment(fragment: BaseFragment)
    }
}
