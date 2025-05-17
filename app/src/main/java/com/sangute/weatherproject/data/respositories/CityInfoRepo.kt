package com.sangute.weatherproject.data.repositories

import com.sangute.weatherproject.data.datasources.openweather.CityInfoService
import com.sangute.weatherproject.domain.models.CityInfoModel
import javax.inject.Inject

class CityInfoRepo @Inject constructor(
    private val api: CityInfoService
) {
    suspend fun getCityInfo(latitude: Float, longitude: Float): CityInfoModel {
        return api.getCityInfo(latitude, longitude)
    }

    suspend fun getCityInfo(geoId: String): CityInfoModel? {
        return api.getCityInfo(geoId)
    }
}