package com.rohitthebest.notifications

import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.rohitthebest.notifications.others.Constants.CHANNEL1
import com.rohitthebest.notifications.others.Constants.CHANNEL2
import com.rohitthebest.notifications.others.Constants.CHANNEL3
import com.rohitthebest.notifications.others.Constants.MESSAGE_KEY
import com.rohitthebest.notifications.others.Constants.REQUEST_CODE
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

                /**
                 * Content Intent is useful when user clicks or taps on the notification.
                 */

                val activityIntent = Intent(this, MainActivity::class.java)

                //Pending Intent
                val contentIntent: PendingIntent = PendingIntent.getActivity(
                    this,
                    REQUEST_CODE, activityIntent, 0
                )   //flag = 0 , means we are not passing any flag


                /**
                 * By adding action intent a user can perform any task.
                 * For this we have to broadcastReceiver
                 */

                val broadcastIntent = Intent(this, NotificationReceiver::class.java)
                broadcastIntent.putExtra(MESSAGE_KEY, editTextDesc.text.toString().trim())

                val actionIntent = PendingIntent.getBroadcast(
                    this,
                    REQUEST_CODE, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT
                )

                val notification: Notification =
                    NotificationCompat.Builder(this, CHANNEL1)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(editTextTitle.text.toString().trim())
                        .setContentText(editTextDesc.text.toString().trim())
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .setColor(Color.BLACK)   //for setting the color
                        .setContentIntent(contentIntent)   //triggered when user clicks on notification
                        .setAutoCancel(true)  //Notification is not visible after the user clicks on notification
                        .setOnlyAlertOnce(true)
                        .addAction(R.mipmap.ic_launcher, "Toast", actionIntent) //Adding actions : Max actions = 3
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