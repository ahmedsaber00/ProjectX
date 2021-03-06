package io.android.projectx.remote.service

import io.android.projectx.remote.model.RecipesResponseModel
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path

interface RecipesService {

    @GET("search/{page}")
    fun searchRepositories(@Path("page") pageNumber: Int): Flowable<RecipesResponseModel>
}