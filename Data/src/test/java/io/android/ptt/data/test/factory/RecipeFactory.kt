package io.android.ptt.data.test.factory

import io.android.ptt.data.features.recipes.model.RecipeEntity
import io.android.ptt.domain.features.recipes.model.Recipe

object RecipeFactory {

    fun makeRecipeEntity(): RecipeEntity {
        return RecipeEntity(
            DataFactory.uniqueId(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomDate(),
            DataFactory.randomString(),
            DataFactory.randomBoolean()
        )
    }

    fun makeRecipe(): Recipe {
        return Recipe(
            DataFactory.uniqueId(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomDate(),
            DataFactory.randomString(),
            DataFactory.randomBoolean()
        )
    }

}