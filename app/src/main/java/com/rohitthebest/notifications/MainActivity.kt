package com.rohitthebest.notifications

import android.app.Notification
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.os.SystemClock
import android.support.v4.media.session.MediaSessionCompat
import android.view.View
import android.widget.RemoteViews
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.rohitthebest.notifications.others.Constants.CHANNEL1
import com.rohitthebest.notifications.others.Constants.CHANNEL2
import com.rohitthebest.notifications.others.Constants.CHANNEL3
import com.rohitthebest.notifications.others.Constants.CHANNEL4
import com.rohitthebest.notifications.others.Constants.CHANNEL5
import com.rohitthebest.notifications.others.Constants.CHANNEL6
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
        channel5GroupNotification.setOnClickListener(this)
        channel6CustomNotification.setOnClickListener(this)

        mediaSessionCompat = MediaSessionCompat(this, "MediaSessionCompat")
    }

    override fun onClick(v: View?) {

        when (v?.id) {

            channel1.id -> {

                /**
                 * Content Intent is useful when user clicks or taps on the notification.
                 */

                val activityIntent = Intent(this, MainActivity::class.java)

                /* //Pending Intent
                 val contentIntent: PendingIntent = PendingIntent.getActivity(
                     this,
                     REQUEST_CODE, activityIntent, 0
                 )   //flag = 0 , means we are not passing any flag
 */
                //Another way to create pending intent
                val contentIntent = TaskStackBuilder.create(this).run {
                    addNextIntentWithParentStack(activityIntent)
                    getPendingIntent(REQUEST_CODE, PendingIntent.FLAG_UPDATE_CURRENT)
                }


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

            channel5GroupNotification.id -> {


                /**For API level greater than 23 the groups are made automatically but in lower API groups
                are not created automatically, but we will have to make it ourselves.
                 **/
/*

                val notification =
                    NotificationCompat.Builder(this, CHANNEL5)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(editTextTitle.text.toString().trim())
                        .setContentText(editTextDesc.text.toString().trim())
                        .setPriority(NotificationCompat.PRIORITY_LOW)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .build()



                for (i in 0 until 5) {

                    SystemClock.sleep(1000)
                    notificationManager.notify(i, notification)

                }
*/

                /**
                 * Making Group notification manually
                 */

                val title1 = "title 1"
                val message1 = "message 1"
                val title2 = "title 2"
                val message2 = "message 2"


                val notification1 = NotificationCompat.Builder(this, CHANNEL5)
                    .setContentTitle(title1)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentText(message1)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setGroup("example_group")
                    .build()

                val notification2 = NotificationCompat.Builder(this, CHANNEL5)
                    .setContentTitle(title2)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentText(message2)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setGroup("example_group")
                    .build()

                //We will have to make summary notification so that the notifications can be grouped.
                //It is necessary for making groups

                val activityIntent = Intent(this, MainActivity::class.java)
                val contentIntent = PendingIntent.getActivity(
                    this,
                    REQUEST_CODE, activityIntent, 0
                )

                val summaryNotification = NotificationCompat.Builder(this, CHANNEL5)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setStyle(
                        NotificationCompat.InboxStyle()
                            .addLine("$title2 $message2")
                            .addLine("$title1 $message1")
                            .setBigContentTitle("2 new messages")
                            .setSummaryText("user@example.com")

                    )         //it is not necessary but inbox Style is very convenient for lower API for making groups
                    .setGroupSummary(true)
                    .setGroupAlertBehavior(NotificationCompat.GROUP_ALERT_CHILDREN)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setGroup("example_group")
                    .setContentIntent(contentIntent)
                    .setAutoCancel(true)
                    .build()

                notificationManager.notify(5, notification1)
                notificationManager.notify(6, notification2)
                notificationManager.notify(7, summaryNotification)

            }

            channel6CustomNotification.id -> {

                /**
                 * There are some limitation in making custom layouts for notification
                 * RemoteViews is limited to support for the following layouts:
                 *
                 *   {@link android.widget.AdapterViewFlipper}</li>
                 *   {@link android.widget.FrameLayout}</li>
                 *   {@link android.widget.GridLayout}</li>
                 *   {@link android.widget.GridView}</li>
                 *   {@link android.widget.LinearLayout}</li>
                 *   {@link android.widget.ListView}</li>
                 *   {@link android.widget.RelativeLayout}</li>
                 *   {@link android.widget.StackView}</li>
                 *   {@link android.widget.ViewFlipper}</li>
                 *
                 * <p>And the following widgets:</p>
                 *
                 *   {@link android.widget.AnalogClock}</li>
                 *   {@link android.widget.Button}</li>
                 *   {@link android.widget.Chronometer}</li>
                 *   {@link android.widget.ImageButton}</li>
                 *   {@link android.widget.ImageView}</li>
                 *   {@link android.widget.ProgressBar}</li>
                 *   {@link android.widget.TextClock}</li>
                 *   {@link android.widget.TextView}</li>
                 *
                 * <p>Descendants of these classes are not supported.</p>
                 */

                val remoteViewsCollapsed = RemoteViews(
                    packageName,
                    R.layout.notification_collapsed
                )

                val remoteViewsExpanded = RemoteViews(
                    packageName,
                    R.layout.notification_expanded
                )

                //changing views texts and images dynamically
                remoteViewsCollapsed.setTextViewText(
                    R.id.textView_collapsed_1,
                    "We can set the text dynamically."
                )
                remoteViewsExpanded.setImageViewResource(
                    R.id.imageView_expanded,
                    R.drawable.study_related_icon
                )

                //Setting Click Listeners

                val clickIntent = Intent(this, NotificationReceiver::class.java)
                clickIntent.putExtra(MESSAGE_KEY, "RemoteViews :  setting clicks.")

                val pendingClickIntent = PendingIntent.getBroadcast(
                    this,
                    REQUEST_CODE,
                    clickIntent,
                    0
                )

                remoteViewsExpanded.setOnClickPendingIntent(
                    R.id.imageView_expanded,
                    pendingClickIntent
                )

                val notification = NotificationCompat.Builder(this, CHANNEL6)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setCustomContentView(remoteViewsCollapsed)
                    .setCustomBigContentView(remoteViewsExpanded)
                    .setStyle(NotificationCompat.DecoratedCustomViewStyle())
                    .build()

                notificationManager.notify(8, notification)
            }
        }
    }
}