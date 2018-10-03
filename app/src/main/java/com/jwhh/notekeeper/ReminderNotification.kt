package com.jwhh.notekeeper

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.support.v4.media.session.MediaSessionCompat
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.content.ContextCompat
import android.support.v4.media.app.NotificationCompat.MediaStyle


/**
 * Helper class for showing and canceling basic default
 * notifications.
 *
 *
 * This class makes heavy use of the [NotificationCompat.Builder] helper
 * class to create notifications in a backward-compatible way.
 */
object ReminderNotification {
  /**
   * The unique identifier for this type of notification.
   */
  private const val NOTIFICATION_TAG = "Reminder"

  const val REMINDER_CHANNEL = "reminders"

  const val KEY_TEXT_REPLY = "keyTextReply"

  /**
   * Shows the notification, or updates a previously shown notification of
   * this type, with the given parameters.
   *
   */
  fun notify(context: Context, note: NoteInfo, notePosition: Int) {

    val intent = Intent(context, NoteActivity::class.java)
    intent.putExtra(NOTE_POSITION, notePosition)

    val pendingIntent = TaskStackBuilder.create(context)
        .addNextIntentWithParentStack(intent)
        .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)

    val builder = NotificationCompat.Builder(context, REMINDER_CHANNEL)

        // Set appropriate defaults for the notification light, sound,
        // and vibration.
      //  .setDefaults(Notification.DEFAULT_ALL)

        // Set required fields, including the small icon, the
        // notification title, and text.
        .setSmallIcon(R.drawable.ic_stat_reminder)
        .setContentTitle("Comments about " + note.title)
        .setLargeIcon(BitmapFactory
            .decodeResource(context.resources, R.drawable.pluralsight_large))

        // All fields below this line are optional.

        // Use a default priority (recognized on devices running Android
        // 4.1 or later)
       // .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        // Set ticker text (preview) information for this notification.
        .setTicker("Comments about " + note.title)

        // Set the pending intent to be initiated when the user touches
        // the notification.
        .setContentIntent(pendingIntent)

        // Automatically dismiss the notification when it is touched.
        .setAutoCancel(true)

       // .setColor(ContextCompat.getColor(context, R.color.darkOrange))

       // .setColorized(true)

        .setOnlyAlertOnce(true)

        .addAction(R.drawable.ic_thumb_down_black_24dp, "Thumbs Down", pendingIntent)
        .addAction(R.drawable.ic_skip_previous_black_24dp, "Previous", pendingIntent)
        .addAction(R.drawable.ic_play_arrow_black_24dp, "Playback", pendingIntent)
        .addAction(R.drawable.ic_skip_next_black_24dp, "Next", pendingIntent)
        .addAction(R.drawable.ic_thumb_up_black_24dp, "Thumbs Up", pendingIntent)

        .setPriority(NotificationManager.IMPORTANCE_MAX)
        .setCategory(Notification.CATEGORY_TRANSPORT)
        .setStyle(MediaStyle()
        //.setMediaSession(token)
        .setShowCancelButton(true)
            .setShowActionsInCompactView(1, 2, 3)
        )
//        .setCancelButtonIntent(
//            MediaButtonReceiver.buildMediaButtonPendingIntent(
//                mContext, PlaybackStateCompat.ACTION_STOP)))
        .setOnlyAlertOnce(true)
       // .setContentIntent(createContentIntent())
        .setContentTitle("Album")
        .setContentText("Artist")
        .setSubText("Song Name")
        .setVisibility(Notification.VISIBILITY_PUBLIC)


    notify(context, builder.build())
  }

  @TargetApi(Build.VERSION_CODES.ECLAIR)
  private fun notify(context: Context, notification: Notification) {
    val nm = context
        .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
      nm.notify(NOTIFICATION_TAG, 0, notification)
    } else {
      nm.notify(NOTIFICATION_TAG.hashCode(), notification)
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
