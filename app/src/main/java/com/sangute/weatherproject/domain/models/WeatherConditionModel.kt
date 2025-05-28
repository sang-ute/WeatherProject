 package com.sangute.weatherproject.domain.models

data class WeatherCondition(
    val temperature: Double,
    val windSpeed: Double,
    val weatherDescription: String,
    val humidity: Int,
    val pressure: Int,
    val iconCode: String,
    val timestamp: Long = System.currentTimeMillis()
)