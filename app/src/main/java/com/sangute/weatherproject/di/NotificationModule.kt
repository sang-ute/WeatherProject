package com.sangute.weatherproject.di

import android.content.Context
import com.sangute.weatherproject.framework.notification.WeatherNotificationManager
import com.sangute.weatherproject.framework.notification.WeatherNotificationReceiver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
object NotificationModule {
    
    @Provides
    @Singleton
    fun provideWeatherNotificationManager(
        @ApplicationContext context: Context
    ): WeatherNotificationManager {
        return WeatherNotificationManager(context)
    }

    @Provides
    fun provideWeatherNotificationReceiver(
        notificationManager: WeatherNotificationManager
    ): WeatherNotificationReceiver {
        return WeatherNotificationReceiver().apply {
            this.notificationManager = notificationManager
        }
    }
} 