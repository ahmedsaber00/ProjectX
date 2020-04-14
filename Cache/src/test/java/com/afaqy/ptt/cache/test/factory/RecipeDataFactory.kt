package com.afaqy.ptt.cache.test.factory

import com.afaqy.ptt.cache.extentions.getOffsetDate
import com.afaqy.ptt.cache.features.login.model.CachedLogin
import com.afaqy.ptt.data.features.recipes.model.RecipeEntity

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