package com.mainthrowsexception.moodtrackingapp.ui.common.presenter

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.mainthrowsexception.moodtrackingapp.ui.common.contract.SettingsContract
import java.util.*

class SettingsPresenter(view: SettingsContract.View) : SettingsContract.Presenter {
    private var view: SettingsContract.View? = view
    override fun doLogout() {
        FirebaseAuth.getInstance().signOut()
        view?.onLogout()
    }

    override fun changeLocale(language: String) {
        when(language) {
            "en" -> view?.onChangeLocale(Locale.ENGLISH)
            "ru" -> view?.onChangeLocale(Locale("ru", "RU"))
        }
    }

}