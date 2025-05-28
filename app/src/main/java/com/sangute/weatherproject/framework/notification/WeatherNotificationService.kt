package com.sangute.weatherproject.framework.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.sangute.weatherproject.R
import com.sangute.weatherproject.domain.models.WeatherCondition
import com.sangute.weatherproject.domain.repository.WeatherRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class WeatherNotificationService : Service() {
    @Inject
    lateinit var weatherRepository: WeatherRepository

    @Inject
    lateinit var notificationManager: WeatherNotificationManager

    private val serviceScope = CoroutineScope(Dispatchers.IO + Job())
    private var isRunning = false
    private var isForeground = false

    companion object {
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "WeatherServiceChannel"
        private const val CHANNEL_NAME = "Weather Service"
        private const val TAG = "WeatherNotificationService"
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "WeatherNotificationService created")
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "WeatherNotificationService onStartCommand called")
        
        // If already running as foreground, just restart weather checks
        if (isForeground) {
            Log.d(TAG, "Service already running as foreground, restarting weather checks")
            if (!isRunning) {
                startWeatherChecks()
            }
            return START_STICKY
        }

        // Start as foreground service immediately
        try {
            val notification = createNotification()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                startForeground(NOTIFICATION_ID, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE)
            } else {
                startForeground(NOTIFICATION_ID, notification)
            }
            isForeground = true
            Log.d(TAG, "Service started as foreground")
            
            if (!isRunning) {
                startWeatherChecks()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error starting foreground service: ${e.message}", e)
            stopSelf()
            return START_NOT_STICKY
        }

        return START_STICKY
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                val channel = NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_LOW
                ).apply {
                    description = "Used for the weather notification service"
                }
                val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
                Log.d(TAG, "Created notification channel: $CHANNEL_ID")
            } catch (e: Exception) {
                Log.e(TAG, "Error creating notification channel: ${e.message}", e)
            }
        }
    }

    private fun createNotification(): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Weather Service")
            .setContentText("Monitoring weather conditions")
            .setSmallIcon(R.drawable.ic_notification_alert)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }

    private fun startWeatherChecks() {
        if (isRunning) {
            Log.d(TAG, "Weather checks already running")
            return
        }
        
        isRunning = true
        serviceScope.launch {
            try {
                Log.d(TAG, "Starting weather check")
                val currentWeather = weatherRepository.getCurrentWeather()
                Log.d(TAG, "Received weather data - Temperature: ${currentWeather.temperature}°C")
                checkWeatherConditions(currentWeather)
            } catch (e: Exception) {
                Log.e(TAG, "Error in weather check: ${e.message}", e)
            } finally {
                isRunning = false
            }
        }
    }

    private fun checkWeatherConditions(weather: WeatherCondition) {
        Log.d(TAG, "Checking weather conditions - Temperature: ${weather.temperature}°C")
        // Check for extreme weather conditions
        when {
            weather.temperature >= 35 -> {
                Log.d(TAG, "Heatwave condition detected - Temperature: ${weather.temperature}°C")
                notificationManager.showWeatherAlert(
                    "Heatwave Alert",
                    "High temperature of ${weather.temperature}°C. Stay hydrated and avoid prolonged sun exposure."
                )
            }
            weather.temperature <= 0 -> {
                Log.d(TAG, "Freezing condition detected - Temperature: ${weather.temperature}°C")
                notificationManager.showWeatherAlert(
                    "Freezing Temperature Alert",
                    "Temperature is ${weather.temperature}°C. Dress warmly and be cautious of icy conditions."
                )
            }
            weather.windSpeed >= 30 -> {
                Log.d(TAG, "Strong wind condition detected - Speed: ${weather.windSpeed} km/h")
                notificationManager.showWeatherAlert(
                    "Strong Wind Alert",
                    "Strong winds of ${weather.windSpeed} km/h. Secure loose objects and be cautious outdoors."
                )
            }
            weather.weatherDescription.contains("storm", ignoreCase = true) -> {
                Log.d(TAG, "Storm condition detected")
                notificationManager.showWeatherAlert(
                    "Storm Alert",
                    "Storm conditions detected. Stay indoors and monitor local weather updates."
                )
            }
        }

        // Check for weather suggestions
        when {
            weather.weatherDescription.contains("sunny", ignoreCase = true) -> {
                Log.d(TAG, "Sunny condition detected")
                notificationManager.showWeatherSuggestion(
                    "Sunny Day Reminder",
                    "Don't forget your hat and sunscreen! UV index is high today."
                )
            }
            weather.weatherDescription.contains("rain", ignoreCase = true) -> {
                Log.d(TAG, "Rain condition detected")
                notificationManager.showWeatherSuggestion(
                    "Rain Alert",
                    "Don't forget your umbrella! Rain is expected today."
                )
            }
            weather.weatherDescription.contains("snow", ignoreCase = true) -> {
                Log.d(TAG, "Snow condition detected")
                notificationManager.showWeatherSuggestion(
                    "Snow Alert",
                    "Bundle up! Snow is expected today."
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "WeatherNotificationService destroyed")
        isRunning = false
        isForeground = false
    }
} 