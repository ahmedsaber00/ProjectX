package io.android.ptt.presentation.base.mapper

import io.android.ptt.domain.features.recipes.model.Recipe
import io.android.ptt.presentation.base.model.RecipeView
import javax.inject.Inject

open class RecipeViewMapper @Inject constructor() : Mapper<RecipeView, Recipe> {

    override fun mapToView(type: Recipe): RecipeView {
        return RecipeView(
            type.id, type.author, type.title,
            type.description, type.url, type.urlToImage, type.publishedAt,
            type.content, type.isBookmarked
        )
    }
}