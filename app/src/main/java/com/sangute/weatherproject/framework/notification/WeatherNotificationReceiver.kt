package com.sangute.weatherproject.framework.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build

class WeatherNotificationReceiver : BroadcastReceiver() {
    lateinit var notificationManager: WeatherNotificationManager

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == ACTION_CHECK_WEATHER) {
            val serviceIntent = Intent(context, WeatherNotificationService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(serviceIntent)
            } else {
                context.startService(serviceIntent)
            }
        }
    }

    companion object {
        const val ACTION_CHECK_WEATHER = "com.sangute.weatherproject.ACTION_CHECK_WEATHER"
    }
} 