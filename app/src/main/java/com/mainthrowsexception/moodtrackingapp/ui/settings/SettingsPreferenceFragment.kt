package com.mainthrowsexception.moodtrackingapp.ui.settings

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.SwitchPreferenceCompat
import com.mainthrowsexception.moodtrackingapp.R
import com.mainthrowsexception.moodtrackingapp.ui.common.base.BasePreferenceFragment
import com.mainthrowsexception.moodtrackingapp.ui.common.contract.SettingsContract
import com.mainthrowsexception.moodtrackingapp.ui.common.presenter.SettingsPresenter
import com.mainthrowsexception.moodtrackingapp.ui.login.LoginFragment
import com.mainthrowsexception.moodtrackingapp.util.receivers.AlarmNotificationReceiver
import java.util.*


class SettingsPreferenceFragment : BasePreferenceFragment(), SettingsContract.View, Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener {

    private lateinit var presenter: SettingsPresenter
    private lateinit var languagePreference: ListPreference
    private lateinit var notificationsPreference: SwitchPreferenceCompat
    private lateinit var notificationsTimePreference: Preference
    private lateinit var notificationsDisappearPreference: ListPreference
    private lateinit var logoutPreference: Preference

    override fun getLayout(): Int {
        return R.xml.root_preferences
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        languagePreference = findPreference(getString(R.string.language_preference_key))!!
        notificationsPreference = findPreference(getString(R.string.notifications_switch_preference_key))!!
        notificationsTimePreference = findPreference(getString(R.string.notification_time_preference_key))!!
        notificationsDisappearPreference = findPreference(getString(R.string.notification_remove_preference_key))!!
        logoutPreference = findPreference(getString(R.string.log_out_preference_key))!!

        languagePreference.setOnPreferenceChangeListener(this)
        notificationsPreference.setOnPreferenceChangeListener(this)
        notificationsDisappearPreference.setOnPreferenceChangeListener(this)
        logoutPreference.setOnPreferenceClickListener(this)

        setNotificationsVisibility(notificationsPreference.isChecked)

        presenter = SettingsPresenter(this)

        navigationPresenter.stopLoading()
    }

    override fun onLogout() {
        navigationPresenter.hideNav()
        navigationPresenter.addFragment(LoginFragment())
    }

    override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
        when(preference?.key) {
            getString(R.string.language_preference_key) -> requireActivity().recreate()
            getString(R.string.notifications_switch_preference_key) -> {
                setNotificationsVisibility(newValue as Boolean)

                if (newValue) {
                    createNotificationChannel()
                    setNotification()
                }
            }
            getString(R.string.notification_remove_preference_key) -> setNotification()
        }
        return true
    }

    override fun onPreferenceClick(preference: Preference?): Boolean {
        when(preference?.key) {
            getString(R.string.notification_time_preference_key) -> null
            "color" -> null
            getString(R.string.log_out_preference_key) -> presenter.doLogout()
        }
        return true
    }



    private fun setNotificationsVisibility(value: Boolean) {
            notificationsTimePreference.isVisible = value
            notificationsDisappearPreference.isVisible = value
    }

    override fun setNotification() {
        val manager =
            requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager?
        val myIntent: Intent
        val pendingIntent: PendingIntent


        myIntent = Intent(requireContext(), AlarmNotificationReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(requireContext(), 0, myIntent, PendingIntent.FLAG_CANCEL_CURRENT)

        manager!!.setRepeating(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime()+3000, AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent)
    }

    override fun onChangeLocale(locale: Locale) {
        Locale.setDefault(locale)
        val configuration = Configuration()
        configuration.setLocale(locale)
        context?.createConfigurationContext(configuration)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "behaviour_ask_channel"
            val descriptionText = "Used for quick check of user's behaviour"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("notification_channel_id", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


}
