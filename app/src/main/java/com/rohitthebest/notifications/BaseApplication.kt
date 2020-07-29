package com.rohitthebest.notifications

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.rohitthebest.notifications.others.Constants.CHANNEL1
import com.rohitthebest.notifications.others.Constants.CHANNEL2
import com.rohitthebest.notifications.others.Constants.CHANNEL3
import com.rohitthebest.notifications.others.Constants.CHANNEL4
import com.rohitthebest.notifications.others.Constants.CHANNEL5
import com.rohitthebest.notifications.others.Constants.CHANNEL6

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

            val channel4 =
                NotificationChannel(
                    CHANNEL4,
                    "Channel 4",
                    NotificationManager.IMPORTANCE_DEFAULT
                )

            channel4.description = "This is Channel 4"

            val channel5 =
                NotificationChannel(
                    CHANNEL5,
                    "Channel 5",
                    NotificationManager.IMPORTANCE_DEFAULT
                )

            channel5.description = "This is Channel 5"

            val channel6 =
                NotificationChannel(
                    CHANNEL6,
                    "Channel 6",
                    NotificationManager.IMPORTANCE_DEFAULT
                )

            channel6.description = "This is Channel 6"

            val notificationManager = getSystemService(NotificationManager::class.java)

            notificationManager.createNotificationChannel(channel1)
            notificationManager.createNotificationChannel(channel2)
            notificationManager.createNotificationChannel(channel3)
            notificationManager.createNotificationChannel(channel4)
            notificationManager.createNotificationChannel(channel5)
            notificationManager.createNotificationChannel(channel6)
        }
    }

}