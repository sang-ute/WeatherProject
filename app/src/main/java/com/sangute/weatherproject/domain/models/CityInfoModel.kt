package com.sangute.weatherproject.domain.models

import com.google.gson.annotations.SerializedName
import com.sangute.weatherproject.domain.models.common.IconIdModel
import com.sangute.weatherproject.domain.models.common.MainInfoModel

data class CityInfoModel(
    val name: String,
    @SerializedName("sys") val country: CountryModel,
    @SerializedName("weather") val iconId: List<IconIdModel>, //The list is to be able to parse the JSON correctly
    @SerializedName("main") val mainInfo: MainInfoModel,
    @SerializedName("wind") val windVelocity: WindVelocityModel
)
