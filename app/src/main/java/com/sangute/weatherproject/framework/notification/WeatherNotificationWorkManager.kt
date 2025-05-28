package com.sangute.weatherproject.framework.notification

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherNotificationWorkManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private const val WEATHER_CHECK_WORK_NAME = "weather_check_work"
        private const val WEATHER_CHECK_INTERVAL_HOURS = 3L // Check every 3 hours
    }

    fun schedulePeriodicWeatherChecks() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val weatherCheckWorkRequest = PeriodicWorkRequestBuilder<WeatherCheckWorker>(
            WEATHER_CHECK_INTERVAL_HOURS,
            TimeUnit.HOURS
        )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            WEATHER_CHECK_WORK_NAME,
            ExistingPeriodicWorkPolicy.UPDATE,
            weatherCheckWorkRequest
        )
    }

    fun cancelPeriodicWeatherChecks() {
        WorkManager.getInstance(context).cancelUniqueWork(WEATHER_CHECK_WORK_NAME)
    }
} 