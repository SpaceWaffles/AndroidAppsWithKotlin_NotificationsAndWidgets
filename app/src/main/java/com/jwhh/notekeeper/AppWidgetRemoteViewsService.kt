package com.jwhh.notekeeper

import android.content.Intent
import android.widget.RemoteViewsService

class AppWidgetRemoteViewsService : RemoteViewsService() {
  override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
    return AppWidgetRemoteViewsFactory(applicationContext)
  }
}