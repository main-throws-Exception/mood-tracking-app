package com.mainthrowsexception.moodtrackingapp.ui.settings

import android.content.Context
import android.util.AttributeSet
import androidx.preference.DialogPreference

class TimepickerPreference(context: Context?, attrs: AttributeSet?) : DialogPreference(context, attrs) {

    init {
        positiveButtonText = "Set"
        negativeButtonText = "Cancel"
    }

    // Get saved preference value (in minutes from midnight, so 1 AM is represented as 1*60 here
    fun getPersistedTime(): String {
        return super.getPersistedString(DEFAULT_TIME)
    }

    // Save preference
    fun persistTime(minutesFromMidnight: String) {
        super.persistString(minutesFromMidnight)
        notifyChanged()
    }

    override fun onSetInitialValue(defaultValue: Any?) {
        super.onSetInitialValue(defaultValue)
        summary = getPersistedTime()
    }

    // Mostly for default values
    companion object {
        // By default we want notification to appear at 9 AM each time.
        private const val DEFAULT_TIME = "21:00"
    }

}