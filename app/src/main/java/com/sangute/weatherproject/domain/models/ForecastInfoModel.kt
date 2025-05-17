package com.sangute.weatherproject.domain.models

import com.google.gson.annotations.SerializedName
import com.sangute.weatherproject.domain.models.common.IconIdModel
import com.sangute.weatherproject.domain.models.common.MainInfoModel

data class ForecastInfoModel (
    @SerializedName("main") val mainInfo: MainInfoModel,
    @SerializedName("weather") val iconId: List<IconIdModel>, //The list is to be able to parse the JSON correctly
    @SerializedName("dt_txt") val date: String
)
