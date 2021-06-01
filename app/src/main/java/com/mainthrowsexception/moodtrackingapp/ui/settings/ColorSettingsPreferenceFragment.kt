package com.mainthrowsexception.moodtrackingapp.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.mainthrowsexception.moodtrackingapp.R
import com.mainthrowsexception.moodtrackingapp.ui.common.base.BasePreferenceFragment

class ColorSettingsPreferenceFragment : BasePreferenceFragment() {

    override fun getLayout(): Int {
        return R.xml.color_preferences
    }
}