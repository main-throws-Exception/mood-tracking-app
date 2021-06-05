package com.mainthrowsexception.moodtrackingapp.util.receivers

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.mainthrowsexception.moodtrackingapp.R
import com.mainthrowsexception.moodtrackingapp.database.api.DbApi
import com.mainthrowsexception.moodtrackingapp.database.model.Entry

class NotificationActionReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationManager: NotificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(R.integer.behaviour_notification_id)

        var mood: Int = -1
        when(intent?.action) {
            "GOOD_BEHAVIOUR" -> mood = 4
            "NEUTRAL_BEHAVIOUR" -> mood = 2
            "BAD_BEHAVIOUR" -> mood = 0
        }

        createEntry(mood)
    }

    private fun createEntry(mood: Int) {
        val entry = Entry()
        entry.mood = mood
        DbApi().createOrUpdateEntry(entry)
    }

}