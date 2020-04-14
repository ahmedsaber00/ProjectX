package com.afaqy.ptt.cache.features.recipes.mapper

import com.afaqy.ptt.cache.base.mapper.CacheMapper
import com.afaqy.ptt.cache.extentions.getDate
import com.afaqy.ptt.cache.extentions.getOffsetDate
import com.afaqy.ptt.cache.features.recipes.model.CachedRecipe
import com.afaqy.ptt.data.features.recipes.model.RecipeEntity
import java.util.*
import javax.inject.Inject

class CachedRecipeMapper @Inject constructor() :
    CacheMapper<CachedRecipe, RecipeEntity> {

    override fun mapFromCached(type: CachedRecipe): RecipeEntity {
        return RecipeEntity(
            type.id, type.author, type.title, type.description,
            type.url, type.urlToImage, type.publishedAt.getDate() ?: Date(), type.content,
            type.isBookmarked
        )
    }

    override fun mapToCached(type: RecipeEntity): CachedRecipe {
        return CachedRecipe(
            type.id, type.author, type.title, type.description,
            type.url, type.urlToImage, type.publishedAt.getOffsetDate(), type.content,
            type.isBookmarked
        )
    }

}