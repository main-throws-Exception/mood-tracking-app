package com.mainthrowsexception.moodtrackingapp.ui.common.base

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.preference.PreferenceFragmentCompat
import com.mainthrowsexception.moodtrackingapp.ui.common.nav.FragmentNavigation

abstract class BasePreferenceFragment : PreferenceFragmentCompat(), FragmentNavigation.View {
    protected lateinit var rootView: View
    protected lateinit var navigationPresenter: FragmentNavigation.Presenter

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(getLayout(), rootKey)
        Log.i("test", "on create preference")
    }

    protected abstract fun getLayout(): Int

    override fun attachPresenter(presenter: FragmentNavigation.Presenter) {
        navigationPresenter = presenter
    }
}