package com.mainthrowsexception.moodtrackingapp.ui.settings

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
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
                resetPreference(findPreference<ColorPickerPreference>(getString(R.string.very_bad_color_preference_key)))
                resetPreference(findPreference<ColorPickerPreference>(getString(R.string.bad_color_preference_key)))
                resetPreference(findPreference<ColorPickerPreference>(getString(R.string.neutral_color_preference_key)))
                resetPreference(findPreference<ColorPickerPreference>(getString(R.string.good_color_preference_key)))
                resetPreference(findPreference<ColorPickerPreference>(getString(R.string.very_good_color_preference_key)))
            }
        }
        return true
    }

    fun resetColors() {
        val sp = preferenceManager.sharedPreferences

        sp.edit().putInt(getString(R.string.very_bad_color_preference_key), ContextCompat.getColor(requireContext(), R.color.very_bad_mood))
            .putInt(getString(R.string.bad_color_preference_key), ContextCompat.getColor(requireContext(), R.color.bad_mood))
            .putInt(getString(R.string.neutral_color_preference_key), ContextCompat.getColor(requireContext(), R.color.neutral_mood))
            .putInt(getString(R.string.good_color_preference_key), ContextCompat.getColor(requireContext(), R.color.good_mood))
            .putInt(getString(R.string.very_good_color_preference_key), ContextCompat.getColor(requireContext(), R.color.very_good_mood))
            .apply()
    }

    fun resetPreference(preference: ColorPickerPreference?) {
        preference?.isVisible = false
        preference?.isVisible = true
    }
}