package io.android.ptt.remote.features.restaurants.mapper

import io.android.ptt.data.features.restaurants.model.RestaurantEntity
import io.android.ptt.remote.base.mapper.ModelMapper
import io.android.ptt.remote.features.restaurants.model.RestaurantModel
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