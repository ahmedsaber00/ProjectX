package com.afaqy.ptt.remote.features

import com.google.gson.Gson
import com.afaqy.ptt.remote.base.RemoteFactory
import com.afaqy.ptt.remote.features.channels.service.ChannelsService
import com.afaqy.ptt.remote.features.editprofile.service.EditProfileService
import com.afaqy.ptt.remote.features.login.service.LoginService
import com.afaqy.ptt.remote.features.profile.service.ProfileService
import com.afaqy.ptt.remote.features.recipes.service.RecipesService
import com.afaqy.ptt.remote.features.restaurants.service.RestaurantsService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Inject

class RemoteServiceFactory @Inject constructor(baseUrl: String, isDebug: Boolean) {

    // Services
    var profileService: ProfileService
    var channelsService: ChannelsService
    var loginService: LoginService
    var recipesService: RecipesService
    var restaurantsService: RestaurantsService
    var editProfileService: EditProfileService

    companion object {
        private lateinit var loggingInterceptor: HttpLoggingInterceptor
        private lateinit var okHttpClient: OkHttpClient
        private lateinit var gson: Gson
        private lateinit var retrofit: Retrofit
    }

    init {
        loggingInterceptor = RemoteFactory.makeLoggingInterceptor((isDebug))
        okHttpClient = RemoteFactory.makeOkHttpClient(loggingInterceptor)
        gson = RemoteFactory.makeGson()
        retrofit = RemoteFactory.makeRetrofit(baseUrl, okHttpClient, gson)
        // Services
        loginService = retrofit.create(LoginService::class.java)
        recipesService = retrofit.create(RecipesService::class.java)
        restaurantsService = retrofit.create(RestaurantsService::class.java)
        channelsService = retrofit.create(ChannelsService::class.java)
        profileService = retrofit.create(ProfileService::class.java)
        editProfileService = retrofit.create(EditProfileService::class.java)
    }

}