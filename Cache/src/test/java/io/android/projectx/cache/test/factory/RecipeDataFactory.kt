package io.android.projectx.cache.test.factory

import io.android.projectx.cache.extentions.getOffsetDate
import io.android.projectx.cache.features.login.model.CachedLogin
import io.android.projectx.data.features.recipes.model.RecipeEntity

object RecipeDataFactory {

    fun makeCachedRecipe(): CachedLogin {
        return CachedLogin(
            DataFactory.uniqueId(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomDate().getOffsetDate(),
            DataFactory.randomString(),
            false
        )
    }

    fun makeBookmarkedCachedRecipe(): CachedLogin {
        return CachedLogin(
            DataFactory.uniqueId(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomDate().getOffsetDate(),
            DataFactory.randomString(),
            true
        )
    }

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

    fun makeBookmarkedRecipeEntity(): RecipeEntity {
        return RecipeEntity(
            DataFactory.uniqueId(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomDate(),
            DataFactory.randomString(),
            true
        )
    }

}