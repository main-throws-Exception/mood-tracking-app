package com.mainthrowsexception.moodtrackingapp.ui.common.contract

import java.util.*

interface SettingsContract {
    interface Presenter {
        fun doLogout()
        fun changeLocale(language: String)
    }

    interface View {
        fun onLogout()
        fun setNotification()
        fun onChangeLocale(locale: Locale)
    }
}