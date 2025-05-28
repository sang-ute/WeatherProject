package com.sangute.weatherproject.data.repository

import android.util.Log
import com.sangute.weatherproject.data.datasources.openweather.CityInfoService
import com.sangute.weatherproject.domain.models.WeatherCondition
import com.sangute.weatherproject.domain.repository.WeatherRepository
import com.sangute.weatherproject.framework.helpers.DataStoreHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepositoryImpl @Inject constructor(
    private val cityInfoService: CityInfoService,
    private val dataStoreHelper: DataStoreHelper
) : WeatherRepository {
    override suspend fun getCurrentWeather(): WeatherCondition {
        try {
            // Get the last saved coordinates and geoname ID from DataStore
            val lastLatitude = dataStoreHelper.read(DataStoreHelper.LAST_LATITUDE_KEY).first() as? Float
            val lastLongitude = dataStoreHelper.read(DataStoreHelper.LAST_LONGITUDE_KEY).first() as? Float
            val isCoordinated = dataStoreHelper.read(DataStoreHelper.LAST_SAVED_ARE_COORDINATES).first() as? Boolean
            val lastGeonameId = dataStoreHelper.read(DataStoreHelper.LAST_GEO_ID_KEY).first() as? String
            
            Log.d("WeatherDebug", "Coordinates: lat=$lastLatitude, lon=$lastLongitude, isCoordinated=$isCoordinated, geonameId=$lastGeonameId")
            
            val cityInfo = if (isCoordinated == true && lastLatitude != null && lastLongitude != null) {
                // Use saved coordinates
                Log.d("WeatherDebug", "Using saved coordinates for API call")
                cityInfoService.getCityInfo(lastLatitude, lastLongitude)
            } else if (lastGeonameId != null) {
                // Use saved geoname ID
                Log.d("WeatherDebug", "Using saved geoname ID for API call: $lastGeonameId")
                cityInfoService.getCityInfo(lastGeonameId)
            } else {
                // Use default location (New York City) if no coordinates or geoname ID are saved
                Log.d("WeatherDebug", "Using default coordinates (NYC) for API call")
                cityInfoService.getCityInfo(40.7128f, -74.0060f)
            } ?: throw IllegalStateException("Failed to get city info from API")
            
            // Log the API response for debugging
            Log.d("WeatherDebug", "API Response - City: ${cityInfo.name}, Country: ${cityInfo.country.countryId}")
            Log.d("WeatherDebug", "API Response - Temperature: ${cityInfo.mainInfo.temp}°C")
            Log.d("WeatherDebug", "API Response - Feels like: ${cityInfo.mainInfo.thermalSensation}°C")
            Log.d("WeatherDebug", "API Response - Weather: ${cityInfo.iconId.firstOrNull()?.idIcon}")
            
            return WeatherCondition(
                temperature = cityInfo.mainInfo.temp,
                windSpeed = cityInfo.windVelocity.speed,
                weatherDescription = cityInfo.iconId.firstOrNull()?.idIcon ?: "Unknown",
                humidity = cityInfo.mainInfo.humidity,
                pressure = 1013, // Default pressure since it's not available in the API response
                iconCode = cityInfo.iconId.firstOrNull()?.idIcon ?: "01d",
                timestamp = System.currentTimeMillis()
            )
        } catch (e: Exception) {
            Log.e("WeatherDebug", "Error getting current weather", e)
            throw e
        }
    }

    override fun observeWeatherUpdates(): Flow<WeatherCondition> = flow {
        while (true) {
            val weather = getCurrentWeather()
            Log.d("WeatherDebug", "Emitting weather update: ${weather.temperature}°C")
            emit(weather)
            kotlinx.coroutines.delay(1800000) // Update every 30 minutes
        }
    }
} 