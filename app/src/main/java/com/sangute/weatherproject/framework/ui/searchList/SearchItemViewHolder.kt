package com.sangute.weatherproject.framework.ui.searchList

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.sangute.weatherproject.R
import com.sangute.weatherproject.databinding.SearchItemBinding
import com.sangute.weatherproject.domain.models.CityInfoModel
import com.sangute.weatherproject.domain.models.SearchItemModel
import com.sangute.weatherproject.utils.LANG

class SearchItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = SearchItemBinding.bind(view)

    fun render(
        searchItem: SearchItemModel,
        onClickListener: (SearchItemModel) -> Unit,
        cityInfo: CityInfoModel
    ) {
        val countryName = getCountryName(cityInfo, searchItem)

        binding.cityAndCountry.text =
            itemView.context.getString(R.string.city_name, searchItem.name, countryName)
        binding.subcountry.text = searchItem.subcountry
        binding.temperature.text =
            itemView.context.getString(R.string.temperature, cityInfo.mainInfo.temp.toInt())

        loadIcon(getImageUrl(cityInfo.iconId[0].idIcon), binding.imageView)

        itemView.setOnClickListener { onClickListener(searchItem) }
    }

    private fun getCountryName(cityInfo: CityInfoModel, searchItem: SearchItemModel): String {
        return searchItem.country
    }

    private fun loadIcon(url: String, imageView: ImageView) {
        Glide.with(itemView).load(url).error(R.drawable.ic_launcher_foreground)
            .diskCacheStrategy(DiskCacheStrategy.ALL).fitCenter().into(imageView)
    }

    private fun getImageUrl(idIcon: String): String {
        return "https://openweathermap.org/img/wn/$idIcon@4x.png"
    }

}