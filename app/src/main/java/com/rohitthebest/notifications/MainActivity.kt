package com.rohitthebest.notifications

import android.app.Notification
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.rohitthebest.notifications.others.Constants.CHANNEL1
import com.rohitthebest.notifications.others.Constants.CHANNEL2
import com.rohitthebest.notifications.others.Constants.CHANNEL3
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var notificationManager: NotificationManagerCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notificationManager = NotificationManagerCompat.from(this)

        channel1.setOnClickListener(this)
        channel2.setOnClickListener(this)
        channel3.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        when (v?.id) {

            channel1.id -> {

                val notification: Notification =
                    NotificationCompat.Builder(this, CHANNEL1)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(editTextTitle.text.toString().trim())
                        .setContentText(editTextDesc.text.toString().trim())
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .build()

                notificationManager.notify(1, notification)
            }
            channel2.id -> {

                val notification =
                    NotificationCompat.Builder(this, CHANNEL2)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(editTextTitle.text.toString().trim())
                        .setContentText(editTextDesc.text.toString().trim())
                        .setPriority(NotificationCompat.PRIORITY_LOW)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .build()

                notificationManager.notify(2, notification)

            }

            channel3.id -> {

                val notification =
                    NotificationCompat.Builder(this, CHANNEL3)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(editTextTitle.text.toString().trim())
                        .setContentText(editTextDesc.text.toString().trim())
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .build()

                notificationManager.notify(3, notification)

            }
        }
    }
}