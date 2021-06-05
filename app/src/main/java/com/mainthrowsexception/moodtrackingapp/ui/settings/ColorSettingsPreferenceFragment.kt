package com.mainthrowsexception.moodtrackingapp.ui.settings

import android.os.Bundle
import android.view.View
import androidx.preference.Preference
import com.mainthrowsexception.moodtrackingapp.R
import com.mainthrowsexception.moodtrackingapp.ui.common.base.BasePreferenceFragment
import com.skydoves.colorpickerpreference.ColorPickerPreference

class ColorSettingsPreferenceFragment : BasePreferenceFragment(), Preference.OnPreferenceClickListener {

    override fun getLayout(): Int {
        return R.xml.color_preferences
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findPreference<Preference>(getString(R.string.reset_colors_key))?.setOnPreferenceClickListener(this)
    }

    override fun onPreferenceClick(preference: Preference?): Boolean {
        when (preference?.key) {
            getString(R.string.reset_colors_key) -> {
                resetColors()
                resetPreference(findPreference<ColorPickerPreference>("very_bad_color_preference_key"))
                resetPreference(findPreference<ColorPickerPreference>("bad_color_preference_key"))
                resetPreference(findPreference<ColorPickerPreference>("neutral_color_preference_key"))
                resetPreference(findPreference<ColorPickerPreference>("good_color_preference_key"))
                resetPreference(findPreference<ColorPickerPreference>("very_good_color_preference_key"))
            }
        }
        return true
    }

    fun resetColors() {
        val sp = preferenceManager.sharedPreferences

        sp.edit().putInt("very_bad_color_preference_key", resources.getColor(R.color.very_bad_mood))
            .putInt("bad_color_preference_key", resources.getColor(R.color.bad_mood))
            .putInt("neutral_color_preference_key", resources.getColor(R.color.neutral_mood))
            .putInt("good_color_preference_key", resources.getColor(R.color.good_mood))
            .putInt("very_good_color_preference_key", resources.getColor(R.color.very_good_mood))
            .apply()
    }

    fun resetPreference(preference: ColorPickerPreference?) {
        preference?.isVisible = false
        preference?.isVisible = true
    }
}