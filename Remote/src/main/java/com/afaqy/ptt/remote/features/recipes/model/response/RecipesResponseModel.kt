package com.afaqy.ptt.remote.features.recipes.model.response

import com.google.gson.annotations.SerializedName
import com.afaqy.ptt.remote.features.recipes.model.RecipeModel

class RecipesResponseModel(@SerializedName("content") val items: List<RecipeModel>)