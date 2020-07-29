package com.rohitthebest.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.app.NotificationManagerCompat
import com.rohitthebest.notifications.others.Constants.MESSAGE_KEY

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        val message = intent?.getStringExtra(MESSAGE_KEY)
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

        val notificationManager = context?.let { NotificationManagerCompat.from(it) }

        notificationManager?.cancel(8)
    }
}