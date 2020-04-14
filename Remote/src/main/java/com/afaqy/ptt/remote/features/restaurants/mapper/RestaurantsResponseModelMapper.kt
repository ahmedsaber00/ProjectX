package com.afaqy.ptt.remote.features.restaurants.mapper

import com.afaqy.ptt.data.features.restaurants.model.RestaurantEntity
import com.afaqy.ptt.remote.base.mapper.ModelMapper
import com.afaqy.ptt.remote.features.restaurants.model.RestaurantModel
import javax.inject.Inject

open class RestaurantsResponseModelMapper @Inject constructor() :
    ModelMapper<RestaurantModel, RestaurantEntity> {

    override fun mapFromModel(model: RestaurantModel): RestaurantEntity {
        return RestaurantEntity(
            model.id, model.title, model.description, model.url,
            model.urlToImage
        )
    }

}