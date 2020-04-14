package io.android.ptt.remote.features.recipes.model.response

import com.google.gson.annotations.SerializedName
import io.android.ptt.remote.features.recipes.model.RecipeModel

class RecipesResponseModel(@SerializedName("content") val items: List<RecipeModel>)