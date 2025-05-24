package com.sangute.weatherproject.usecases

import com.sangute.weatherproject.data.repositories.CityInfoRepo
import com.sangute.weatherproject.domain.models.CityInfoModel
import com.sangute.weatherproject.domain.models.SearchItemModel
import javax.inject.Inject

class GetCityInfoUseCase @Inject constructor(
    private val repository: CityInfoRepo
) {
    suspend fun getCityInfo(latitude: Float, longitude: Float): CityInfoModel =
        repository.getCityInfo(latitude, longitude)

    suspend fun getCityInfo(geoId: String): CityInfoModel? = repository.getCityInfo(geoId)
    suspend fun getCityInfoList(searchItemList: List<SearchItemModel>): List<CityInfoModel> {
        val cityInfoList: MutableList<CityInfoModel> = arrayListOf()
        searchItemList.forEach { searchItem ->
            getCityInfo(searchItem.geonameid)?.let { cityInfoList.add(it) }
        }
        return cityInfoList
    }
}
