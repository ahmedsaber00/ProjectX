package com.afaqy.ptt.remote.test.factory

import com.afaqy.ptt.data.features.recipes.model.RecipeEntity
import com.afaqy.ptt.remote.features.recipes.model.AuthorModel
import com.afaqy.ptt.remote.features.recipes.model.RecipeModel
import com.afaqy.ptt.remote.features.recipes.model.response.RecipesResponseModel

object RecipeDataFactory {

    fun makeAuthor(): AuthorModel {
        return AuthorModel(
            DataFactory.uniqueId(),
            DataFactory.randomString(),
            DataFactory.randomString()
        )
    }

    fun makeRecipe(): RecipeModel {
        return RecipeModel(
            DataFactory.uniqueId(),
            makeAuthor(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomDate(),
            DataFactory.randomString()
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

    fun makeRecipesResponse(): RecipesResponseModel {
        return RecipesResponseModel(
            listOf(
                makeRecipe(),
                makeRecipe()
            )
        )
    }

}