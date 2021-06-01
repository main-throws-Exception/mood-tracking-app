package com.mainthrowsexception.moodtrackingapp.ui.settings

import android.content.Context
import android.os.Bundle
import android.view.View
import com.mainthrowsexception.moodtrackingapp.R
import com.mainthrowsexception.moodtrackingapp.ui.common.base.BaseFragment
import com.mainthrowsexception.moodtrackingapp.ui.common.contract.SettingsContract
import com.mainthrowsexception.moodtrackingapp.ui.common.nav.FragmentNavigation

class SettingsFragment : BaseFragment(){
    var settingsFragment = SettingsPreferenceFragment()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container_settings, settingsFragment)
            .commit()

    }

    override fun attachPresenter(presenter: FragmentNavigation.Presenter) {
        settingsFragment.attachPresenter(presenter)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        settingsFragment.onAttach(context)
    }

    override fun getLayout(): Int {
        return R.layout.fragment_settings
    }
}