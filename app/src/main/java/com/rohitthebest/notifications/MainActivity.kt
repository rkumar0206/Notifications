package com.rohitthebest.notifications

import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.os.SystemClock
import android.support.v4.media.session.MediaSessionCompat
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.rohitthebest.notifications.others.Constants.CHANNEL1
import com.rohitthebest.notifications.others.Constants.CHANNEL2
import com.rohitthebest.notifications.others.Constants.CHANNEL3
import com.rohitthebest.notifications.others.Constants.CHANNEL4
import com.rohitthebest.notifications.others.Constants.MESSAGE_KEY
import com.rohitthebest.notifications.others.Constants.REQUEST_CODE
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var notificationManager: NotificationManagerCompat
    private lateinit var mediaSessionCompat: MediaSessionCompat


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notificationManager = NotificationManagerCompat.from(this)

        channel1.setOnClickListener(this)
        channel2.setOnClickListener(this)
        channel3.setOnClickListener(this)
        channel4ProgressNotificationBtn.setOnClickListener(this)

        mediaSessionCompat = MediaSessionCompat(this, "MediaSessionCompat")
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

                //Setting BigIcon
                val largeIcon =
                    BitmapFactory.decodeResource(resources, R.drawable.study_related_icon)


                val notification: Notification =
                    NotificationCompat.Builder(this, CHANNEL1)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(largeIcon)
                        .setStyle(
                            NotificationCompat.BigTextStyle()
                                .bigText(getString(R.string.dummy_large_text))
                                .setBigContentTitle("Big content title")
                                .setSummaryText("Summary text")
                        ) // Setting BigTextStyle()
                        .setContentTitle(editTextTitle.text.toString().trim())
                        .setContentText(editTextDesc.text.toString().trim())
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .setColor(Color.BLACK)   //for setting the color
                        .setContentIntent(contentIntent)   //triggered when user clicks on notification
                        .setAutoCancel(true)  //Notification is not visible after the user clicks on notification
                        .setOnlyAlertOnce(true)  //notification will make sound only once+
                        .addAction(
                            R.mipmap.ic_launcher,
                            "Toast",
                            actionIntent
                        ) //Adding actions : Max actions = 3
                        .build()

                notificationManager.notify(1, notification)
            }

            channel2.id -> {

                val picture =
                    BitmapFactory.decodeResource(resources, R.drawable.study_related_icon)


                val notification =
                    NotificationCompat.Builder(this, CHANNEL2)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(picture)
                        .setStyle(
                            NotificationCompat.BigPictureStyle()
                                .bigPicture(picture)
                                .bigLargeIcon(null)
                        )
                        .setContentTitle(editTextTitle.text.toString().trim())
                        .setContentText(editTextDesc.text.toString().trim())
                        .setPriority(NotificationCompat.PRIORITY_LOW)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .build()

                notificationManager.notify(2, notification)

            }

            channel3.id -> {

                val playerThumnail =
                    BitmapFactory.decodeResource(resources, R.drawable.study_related_icon)

                val notification =
                    NotificationCompat.Builder(this, CHANNEL3)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(editTextTitle.text.toString().trim())
                        .setContentText(editTextDesc.text.toString().trim())
                        .setLargeIcon(playerThumnail)
                        .addAction(R.drawable.ic_baseline_dislike_24, "Dislike", null)
                        .addAction(R.drawable.ic_baseline_skip_previous_24, "Previous", null)
                        .addAction(R.drawable.ic_baseline_pause_24, "Pause", null)
                        .addAction(R.drawable.ic_baseline_skip_next_24, "Next", null)
                        .addAction(R.drawable.ic_baseline_like_24, "Like", null)
                        .setStyle(
                            androidx.media.app.NotificationCompat.MediaStyle()
                                .setShowActionsInCompactView(1, 2, 3)
                                .setMediaSession(mediaSessionCompat.sessionToken)
                        )
                        .setSubText("Sub Text")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .build()

                notificationManager.notify(3, notification)

            }

            channel4ProgressNotificationBtn.id -> {

                val activityIntent = Intent(this, MainActivity::class.java)

                //Pending Intent
                val contentIntent: PendingIntent = PendingIntent.getActivity(
                    this,
                    REQUEST_CODE, activityIntent, 0
                )   //flag = 0 , means we are not passing any flag

                val progressMax = 100

                val notificationBuilder = NotificationCompat.Builder(this, CHANNEL4)
                    .setSmallIcon(R.drawable.study_related_icon)
                    .setColor(Color.GREEN)
                    .setContentTitle("Download")
                    .setContentText("Download In Progress...")
                    .setContentIntent(contentIntent)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setOnlyAlertOnce(true) //for making sound only once
                    .setOngoing(true) //notification cannot be cancel
                    .setProgress(progressMax, 0, false) //Setting progress

                notificationManager.notify(4, notificationBuilder.build())

                //Updating progress
                Thread(Runnable {

                    SystemClock.sleep(2000)

                    for (progress in 0..progressMax step 10) {

                        notificationBuilder.setProgress(progressMax, progress, false)
                        notificationManager.notify(4, notificationBuilder.build())
                        SystemClock.sleep(1000)
                    }

                    notificationBuilder.setProgress(0, 0, false)
                        .setOngoing(false)
                        .setContentText("Download done.")

                    notificationManager.notify(4, notificationBuilder.build())

                }).start()
            }
        }
    }
}