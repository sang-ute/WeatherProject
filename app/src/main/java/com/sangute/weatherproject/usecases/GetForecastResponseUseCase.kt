package com.sangute.weatherproject.usecases

import com.sangute.weatherproject.data.repositories.ForecastResponseRepo
import com.sangute.weatherproject.domain.models.ForecastResponseModel
import javax.inject.Inject

class GetForecastResponseUseCase @Inject constructor(
    private val repository: ForecastResponseRepo
) {

    suspend fun getForecastResponse(latitude: Float, longitude: Float): ForecastResponseModel =
        repository.getForecastResponse(latitude, longitude)

    suspend fun getForecastResponse(geoId: String): ForecastResponseModel =
        repository.getForecastResponse(geoId)

}
