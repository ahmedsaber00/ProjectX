package io.android.ptt.cache.features.recipes.mapper

import io.android.ptt.cache.base.mapper.CacheMapper
import io.android.ptt.cache.extentions.getDate
import io.android.ptt.cache.extentions.getOffsetDate
import io.android.ptt.cache.features.recipes.model.CachedRecipe
import io.android.ptt.data.features.recipes.model.RecipeEntity
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