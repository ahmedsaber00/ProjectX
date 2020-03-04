package io.android.projectx.presentation.test.factory

import io.android.projectx.domain.features.login.model.LoginModel
import io.android.projectx.presentation.base.model.RecipeView

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