package com.mainthrowsexception.moodtrackingapp.util

import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import java.util.*

class AppUtil {
    companion object {
        fun changeLang(context: Context?, language: String) : ContextWrapper {
            val configuration = context!!.resources.configuration
            val sysLocale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                configuration.locales.get(0)
            } else {
                configuration.locale
            }

            var newContext = context
            if (language != "" && sysLocale.language != language) {
                val locale = Locale(language)
                Locale.setDefault(locale)
                configuration.setLocale(locale)

                newContext = context.createConfigurationContext(configuration)
            }

            return ContextWrapper(newContext)
        }
    }
}