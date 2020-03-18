package io.android.projectx.presentation.di.module

import dagger.Binds
import dagger.Module
import dagger.Provides
import io.android.projectx.data.features.channels.repository.ChannelsRemote
import io.android.projectx.data.features.login.repository.LoginRemote
import io.android.projectx.data.features.recipes.repository.RecipesRemote
import io.android.projectx.data.features.restaurants.repository.RestaurantsRemote
import io.android.projectx.presentation.BuildConfig
import io.android.projectx.remote.features.recipes.RecipesRemoteImpl
import io.android.projectx.remote.features.RemoteServiceFactory
import io.android.projectx.remote.features.channels.ChannelsRemoteImpl
import io.android.projectx.remote.features.channels.service.ChannelsService
import io.android.projectx.remote.features.login.LoginRemoteImpl
import io.android.projectx.remote.features.login.service.LoginService
import io.android.projectx.remote.features.recipes.service.RecipesService
import io.android.projectx.remote.features.restaurants.RestaurantsRemoteImpl
import io.android.projectx.remote.features.restaurants.service.RestaurantsService

@Module
abstract class  RemoteModule {

    @Module
    companion object {

        @Provides
        @JvmStatic
        fun provideRemoteServiceFactory(): RemoteServiceFactory {
            return RemoteServiceFactory(BuildConfig.API_BASE_URL, BuildConfig.DEBUG)
        }

        @Provides
        @JvmStatic
        fun provideLoginService(remoteServiceFactory: RemoteServiceFactory): LoginService {
            return remoteServiceFactory.loginService
        }

        @Provides
        @JvmStatic
        fun provideRecipesService(remoteServiceFactory: RemoteServiceFactory): RecipesService {
            return remoteServiceFactory.recipesService
        }

        @Provides
        @JvmStatic
        fun provideRestaurantsService(remoteServiceFactory: RemoteServiceFactory): RestaurantsService {
            return remoteServiceFactory.restaurantsService
        }

        @Provides
        @JvmStatic
        fun provideChannelsService(remoteServiceFactory: RemoteServiceFactory): ChannelsService {
            return remoteServiceFactory.channelsService
        }
    }

    @Binds
    abstract fun bindLoginRemote(loginRemote: LoginRemoteImpl): LoginRemote

    @Binds
    abstract fun bindRecipesRemote(recipesRemote: RecipesRemoteImpl): RecipesRemote

    @Binds
    abstract fun bindRestaurantsRemote(restaurantsRemote: RestaurantsRemoteImpl): RestaurantsRemote

    @Binds
    abstract fun bindChannelsRemote(channelsRemote: ChannelsRemoteImpl): ChannelsRemote
}