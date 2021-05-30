package com.mainthrowsexception.moodtrackingapp.util.receivers

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.preference.PreferenceManager
import com.mainthrowsexception.moodtrackingapp.R
import com.mainthrowsexception.moodtrackingapp.ui.common.MainActivity
import java.text.SimpleDateFormat

class AlarmNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i("Alarm", "Alarm worked")
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(context!!, "notification_channel_id")

        val myIntent = Intent(context, MainActivity::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context,
            0,
            myIntent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val value = preferences.getString("notification_remove", "")!!

        val goodPendingIntent: PendingIntent = getActionPendingIntent(context,"GOOD_BEHAVIOUR")
        val neutralPendingIntent: PendingIntent = getActionPendingIntent(context,"NEUTRAL_BEHAVIOUR")
        val badPendingIntent: PendingIntent = getActionPendingIntent(context,"BAD_BEHAVIOUR")

        builder.setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_ALL)
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Behaviour")
            .setContentIntent(pendingIntent)
            .setContentText("Mark your today behaviour")
            .setDefaults(Notification.DEFAULT_LIGHTS or Notification.DEFAULT_SOUND)
            .setContentInfo("Info")
            .addAction(R.drawable.ic_very_happy_emoji, "Good", goodPendingIntent)
            .addAction(R.drawable.ic_neutral_emoji, "Neutral", neutralPendingIntent)
            .addAction(R.drawable.ic_very_sad_emoji, "Bad", badPendingIntent)

        if (value != "never") {
            val dateFormat = SimpleDateFormat("hh:mm")
            val date = dateFormat.parse(value)!!
            builder.setTimeoutAfter(date.time)
        }

        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(R.integer.behaviour_notification_id, builder.build())
    }

    private fun getActionPendingIntent(context: Context?, action: String): PendingIntent {
        val intent = Intent(context, NotificationActionReceiver::class.java).apply {
            this.action = action
        }
        val pIntent: PendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        return pIntent
    }
}