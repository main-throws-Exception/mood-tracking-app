package com.mainthrowsexception.moodtrackingapp.ui.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mainthrowsexception.moodtrackingapp.R
import com.mainthrowsexception.moodtrackingapp.ui.common.base.BaseFragment
import com.mainthrowsexception.moodtrackingapp.ui.common.contract.MainActivityContract
import com.mainthrowsexception.moodtrackingapp.ui.common.presenter.MainActivityPresenter

class MainActivity : AppCompatActivity(), MainActivityContract.View {

    private lateinit var presenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_main)

        presenter = MainActivityPresenter(this)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.activity_main__nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNavigationView
            .setupWithNavController(navController)
        bottomNavigationView
            .setOnNavigationItemSelectedListener {
                navController.popBackStack(R.id.currentDayFragment, false)
                navController.navigate(it.itemId)

                true
            }
        findViewById<AppCompatButton>(R.id.bottom_nav_view_button)
            .setOnClickListener {
//                navController.navigate(R.id.entryFragment, null, NavOptions.Builder().setPopUpTo(R.id.currentDayFragment, false).build())
                navController.navigate(R.id.entryFragment)
            }
    }

    override fun setFragment(fragment: BaseFragment) {
        fragment.attachPresenter(presenter)
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_container, fragment)
            .commit()
    }
}
