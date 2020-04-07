package io.android.projectx.remote.features

import com.google.gson.Gson
import io.android.projectx.remote.base.RemoteFactory
import io.android.projectx.remote.features.channels.service.ChannelsService
import io.android.projectx.remote.features.login.service.LoginService
import io.android.projectx.remote.features.profile.service.ProfileService
import io.android.projectx.remote.features.recipes.service.RecipesService
import io.android.projectx.remote.features.restaurants.service.RestaurantsService
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
    }

}