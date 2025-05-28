package com.sangute.weatherproject.domain.repository

import com.sangute.weatherproject.domain.models.WeatherCondition
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getCurrentWeather(): WeatherCondition
    fun observeWeatherUpdates(): Flow<WeatherCondition>
} 