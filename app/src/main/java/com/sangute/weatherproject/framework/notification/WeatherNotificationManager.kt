package com.sangute.weatherproject.framework.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.sangute.weatherproject.R
import com.sangute.weatherproject.framework.ui.main.MainActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherNotificationManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    companion object {
        const val CHANNEL_ID_WEATHER_ALERTS = "weather_alerts"
        const val CHANNEL_ID_WEATHER_SUGGESTIONS = "weather_suggestions"
        const val NOTIFICATION_ID_ALERT = 1
        const val NOTIFICATION_ID_SUGGESTION = 2
    }

    init {
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val alertChannel = NotificationChannel(
                CHANNEL_ID_WEATHER_ALERTS,
                "Weather Alerts",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Important weather alerts and warnings"
                enableVibration(true)
            }

            val suggestionChannel = NotificationChannel(
                CHANNEL_ID_WEATHER_SUGGESTIONS,
                "Weather Suggestions",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Weather-based suggestions and tips"
                enableVibration(false)
            }

            notificationManager.createNotificationChannels(listOf(alertChannel, suggestionChannel))
        }
    }

    fun showWeatherAlert(title: String, message: String) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID_WEATHER_ALERTS)
            .setSmallIcon(R.drawable.ic_notification_alert)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(NOTIFICATION_ID_ALERT, notification)
    }

    fun showWeatherSuggestion(title: String, message: String) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID_WEATHER_SUGGESTIONS)
            .setSmallIcon(R.drawable.ic_notification_suggestion)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(NOTIFICATION_ID_SUGGESTION, notification)
    }
} 