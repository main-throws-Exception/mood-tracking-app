package com.mainthrowsexception.moodtrackingapp.ui.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mainthrowsexception.moodtrackingapp.ui.common.nav.FragmentNavigation

abstract class BaseFragment : Fragment(), FragmentNavigation.View {
    protected lateinit var rootView: View
    protected lateinit var navigationPresenter: FragmentNavigation.Presenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(getLayout(), container, false)
        return rootView
    }

    protected abstract fun getLayout(): Int

    override fun attachPresenter(presenter: FragmentNavigation.Presenter) {
        navigationPresenter = presenter
    }
}
