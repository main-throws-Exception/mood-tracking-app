package com.mainthrowsexception.moodtrackingapp.ui.common

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mainthrowsexception.moodtrackingapp.R
import com.mainthrowsexception.moodtrackingapp.ui.common.base.BaseFragment
import com.mainthrowsexception.moodtrackingapp.ui.common.base.BasePreferenceFragment
import com.mainthrowsexception.moodtrackingapp.ui.common.contract.MainActivityContract
import com.mainthrowsexception.moodtrackingapp.ui.common.presenter.MainActivityPresenter
import com.mainthrowsexception.moodtrackingapp.ui.login.LoginFragment
import com.mainthrowsexception.moodtrackingapp.ui.settings.SettingsPreferenceFragment
import com.mainthrowsexception.moodtrackingapp.util.AppUtil

class MainActivity : AppCompatActivity(), MainActivityContract.View {

    private lateinit var presenter: MainActivityPresenter
    private lateinit var loading: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_main)

        loading = findViewById(R.id.loading_panel)

        presenter = MainActivityPresenter(this)

        val homeFragment = LoginFragment()

        homeFragment.attachPresenter(presenter)
        homeFragment.onAttach(applicationContext)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, homeFragment)
            .commit()

//        presenter.setHomeFragment()

//        val navHostFragment =
//            supportFragmentManager.findFragmentById(R.id.activity_main__nav_host_fragment) as NavHostFragment
//        val navController = navHostFragment.navController
//        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
//        bottomNavigationView
//            .setupWithNavController(navController)
//        bottomNavigationView
//            .setOnNavigationItemSelectedListener {
//                navController.popBackStack(R.id.currentDayFragment, false)
//                navController.navigate(it.itemId)
//
//                true
//            }
//        findViewById<AppCompatButton>(R.id.bottom_nav_view_button)
//            .setOnClickListener {
////                navController.navigate(R.id.entryFragment, null, NavOptions.Builder().setPopUpTo(R.id.currentDayFragment, false).build())
//                navController.navigate(R.id.entryFragment)
//            }
    }

    override fun attachBaseContext(newBase: Context?) {
        val language = PreferenceManager.getDefaultSharedPreferences(newBase).getString("language", "")!!
        val context = AppUtil.changeLang(newBase, language)
        super.attachBaseContext(context)
    }

    override fun setFragment(fragment: BaseFragment) {
        fragment.attachPresenter(presenter)
        fragment.onAttach(applicationContext)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
        startLoading()
    }

    override fun startLoading() {
        loading.visibility = View.VISIBLE
    }

    override fun stopLoading() {
        loading.visibility = View.GONE
    }

    override fun setFragment(fragment: BasePreferenceFragment) {
        fragment.attachPresenter(presenter)
        fragment.onAttach(applicationContext)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
        startLoading()
    }
}
