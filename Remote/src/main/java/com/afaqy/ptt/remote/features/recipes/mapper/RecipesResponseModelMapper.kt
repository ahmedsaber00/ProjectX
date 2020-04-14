package com.afaqy.ptt.remote.features.recipes.mapper

import com.afaqy.ptt.data.features.recipes.model.RecipeEntity
import com.afaqy.ptt.remote.base.mapper.ModelMapper
import com.afaqy.ptt.remote.features.recipes.model.RecipeModel
import javax.inject.Inject

open class RecipesResponseModelMapper @Inject constructor() :
    ModelMapper<RecipeModel, RecipeEntity> {

    override fun mapFromModel(model: RecipeModel): RecipeEntity {
        return RecipeEntity(
            model.id, model.author.authorName, model.title, model.description, model.url,
            model.urlToImage, model.publishedAt, model.content, false
        )
    }

}