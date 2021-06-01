package com.mainthrowsexception.moodtrackingapp.util

import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.util.Log
import android.view.View
import androidx.preference.PreferenceManager
import com.mainthrowsexception.moodtrackingapp.R
import java.util.*

class AppUtil {
    enum class Mood {
        VERY_GOOD,
        GOOD,
        NEUTRAL,
        BAD,
        VERY_BAD,
    }

    companion object {
        fun changeLang(context: Context?, language: String) : ContextWrapper {
            val configuration = context!!.resources.configuration
            val sysLocale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                configuration.locales.get(0)
            } else {
                @Suppress("DEPRECATION")
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

        fun getPreferenceColors(context: Context): Map<Mood, Int> {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)

            val map = mapOf(Mood.VERY_GOOD to preferences.getInt("very_good_color_preference_key", 1),
            Mood.GOOD to preferences.getInt("very_good_color_preference_key", 1),
            Mood.NEUTRAL to preferences.getInt("very_good_color_preference_key", 1),
            Mood.BAD to preferences.getInt("very_good_color_preference_key", 1),
            Mood.VERY_BAD to preferences.getInt("very_good_color_preference_key", 1))

            return map
        }
    }
}