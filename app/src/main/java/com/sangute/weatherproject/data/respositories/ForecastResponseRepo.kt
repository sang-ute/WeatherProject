package com.sangute.weatherproject.data.repositories

import com.sangute.weatherproject.data.datasources.openweather.ForecastResponseService
import com.sangute.weatherproject.domain.models.ForecastResponseModel
import javax.inject.Inject

class ForecastResponseRepo @Inject constructor(
    private val api: ForecastResponseService
){

    suspend fun getForecastResponse(latitude: Float, longitude: Float): ForecastResponseModel {
        return api.getForecastResponse(latitude, longitude)
    }

    suspend fun getForecastResponse(geoId: String): ForecastResponseModel {
        return api.getForecastResponse(geoId)
    }
}
