package com.afaqy.ptt.presentation.test.factory

import com.afaqy.ptt.domain.features.login.model.LoginModel
import com.afaqy.ptt.presentation.base.model.RecipeView

object RecipeFactory {

    fun makeRecipeView(): RecipeView {
        return RecipeView(
            DataFactory.uniqueId(), DataFactory.randomString(),
            DataFactory.randomString(), DataFactory.randomString(),
            DataFactory.randomString(), DataFactory.randomString(),
            DataFactory.randomDate(), DataFactory.randomString(),
            DataFactory.randomBoolean()
        )
    }

    fun makeRecipe(): LoginModel {
        return LoginModel(
            DataFactory.uniqueId(), DataFactory.randomString(),
            DataFactory.randomString(), DataFactory.randomString(),
            DataFactory.randomString(), DataFactory.randomString(),
            DataFactory.randomDate(), DataFactory.randomString(),
            DataFactory.randomBoolean()
        )
    }

    fun makeRecipeViewList(count: Int): List<RecipeView> {
        val recipes = mutableListOf<RecipeView>()
        repeat(count) {
            recipes.add(makeRecipeView())
        }
        return recipes
    }

    fun makeRecipeList(count: Int): List<LoginModel> {
        val recipes = mutableListOf<LoginModel>()
        repeat(count) {
            recipes.add(makeRecipe())
        }
        return recipes
    }

}