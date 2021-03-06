package com.rohitthebest.notifications

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.rohitthebest.notifications.others.Constants.CHANNEL1
import com.rohitthebest.notifications.others.Constants.CHANNEL2
import com.rohitthebest.notifications.others.Constants.CHANNEL3

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        createNotificationChannel()
    }


    private fun createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel1 =
                NotificationChannel(
                    CHANNEL1,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH
                )

            channel1.description = "This is Channel 1"

            val channel2 =
                NotificationChannel(
                    CHANNEL2,
                    "Channel 2",
                    NotificationManager.IMPORTANCE_LOW
                )

            channel2.description = "This is Channel 2"

            val channel3 =
                NotificationChannel(
                    CHANNEL3,
                    "Channel 3",
                    NotificationManager.IMPORTANCE_DEFAULT
                )

            channel3.description = "This is Channel 3"

            val notificationManager = getSystemService(NotificationManager::class.java)

            notificationManager.createNotificationChannel(channel1)
            notificationManager.createNotificationChannel(channel2)
            notificationManager.createNotificationChannel(channel3)

        }
    }

}