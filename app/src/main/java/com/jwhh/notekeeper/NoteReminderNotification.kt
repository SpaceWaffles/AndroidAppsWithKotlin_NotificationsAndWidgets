package com.jwhh.notekeeper

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.support.v4.app.NotificationCompat

/**
 * Helper class for showing and canceling new message
 * notifications.
 *
 *
 * This class makes heavy use of the [NotificationCompat.Builder] helper
 * class to create notifications in a backward-compatible way.
 */
object NoteReminderNotification {
  /**
   * The unique identifier for this type of notification.
   */
  private val NOTIFICATION_TAG = "NewMessage"

  /**
   * Shows the notification, or updates a previously shown notification of
   * this type, with the given parameters.
   *
   *
   * TODO: Customize this method's arguments to present relevant content in
   * the notification.
   *
   *
   * TODO: Customize the contents of this method to tweak the behavior and
   * presentation of new message notifications. Make
   * sure to follow the
   * [
 * Notification design guidelines](https://developer.android.com/design/patterns/notifications.html) when doing so.
   *
   * @see .cancel
   */
  fun notify(context: Context, titleText: String,
             noteText: String) {

    val intent = Intent(context, ItemsActivity::class.java)
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)

    val builder = NotificationCompat.Builder(context, "reminders")

        // Set appropriate defaults for the notification light, sound,
        // and vibration.
        .setDefaults(Notification.DEFAULT_ALL)

        // Set required fields, including the small icon, the
        // notification title, and text.
        .setSmallIcon(R.drawable.ic_stat_new_message)
        .setContentTitle(titleText)
        .setContentText(noteText)

        // Use a default priority (recognized on devices running Android
        // 4.1 or later)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        // Set ticker text (preview) information for this notification.
        .setTicker(titleText)

        // Set the pending intent to be initiated when the user touches
        // the notification.
        .setContentIntent(
            PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT))

        // Automatically dismiss the notification when it is touched.
        .setAutoCancel(true)

    val nm = context
        .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val channel = NotificationChannel("reminders", "Note Reminders", NotificationManager.IMPORTANCE_DEFAULT)
      nm.createNotificationChannel(channel)
    }

    notify(builder.build(), nm)
  }

  @TargetApi(Build.VERSION_CODES.ECLAIR)
  private fun notify(notification: Notification, notificationManager: NotificationManager) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
      notificationManager.notify(NOTIFICATION_TAG, 0, notification)
    } else {
      notificationManager.notify(NOTIFICATION_TAG.hashCode(), notification)
    }
  }

  /**
   * Cancels any notifications of this type previously shown using
   * [.notify].
   */
  @TargetApi(Build.VERSION_CODES.ECLAIR)
  fun cancel(context: Context) {
    val nm = context
        .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
      nm.cancel(NOTIFICATION_TAG, 0)
    } else {
      nm.cancel(NOTIFICATION_TAG.hashCode())
    }
  }
}
