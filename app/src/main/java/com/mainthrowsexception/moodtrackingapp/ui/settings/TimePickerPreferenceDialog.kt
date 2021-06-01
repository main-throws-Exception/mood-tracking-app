package com.mainthrowsexception.moodtrackingapp.ui.settings

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TimePicker
import androidx.preference.PreferenceDialogFragmentCompat

class TimePickerPreferenceDialog : PreferenceDialogFragmentCompat() {

    lateinit var timepicker: TimePicker

    override fun onCreateDialogView(context: Context?): View {
        timepicker = TimePicker(context)
        return timepicker
    }

    override fun onBindDialogView(view: View?) {
        super.onBindDialogView(view)
        timepicker.setIs24HourView(true)
    }

    override fun onDialogClosed(positiveResult: Boolean) {
        if(positiveResult) {
            val time: String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                timepicker.hour.toString() + ":" + timepicker.minute.toString()
            } else {
                @Suppress("DEPRECATION")
                timepicker.currentHour.toString() + ":" + timepicker.currentMinute.toString()
            }
            (preference as TimepickerPreference).persistTime(time)
            preference.summary = time

        }
    }

    companion object {
        fun newInstance(key: String): TimePickerPreferenceDialog {
            val fragment = TimePickerPreferenceDialog()
            val bundle = Bundle(1)
            bundle.putString(ARG_KEY, key)
            fragment.arguments = bundle

            return fragment
        }
    }
}