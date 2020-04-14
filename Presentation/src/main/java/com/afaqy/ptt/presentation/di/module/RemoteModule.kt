package com.afaqy.ptt.presentation.di.module

import dagger.Binds
import dagger.Module
import dagger.Provides
import com.afaqy.ptt.data.features.channels.repository.ChannelsRemote
import com.afaqy.ptt.data.features.editprofile.repository.EditProfileRemote
import com.afaqy.ptt.data.features.login.repository.LoginRemote
import com.afaqy.ptt.data.features.profile.repository.ProfileRemote
import com.afaqy.ptt.data.features.recipes.repository.RecipesRemote
import com.afaqy.ptt.data.features.restaurants.repository.RestaurantsRemote
import com.afaqy.ptt.presentation.BuildConfig
import com.afaqy.ptt.remote.features.recipes.RecipesRemoteImpl
import com.afaqy.ptt.remote.features.RemoteServiceFactory
import com.afaqy.ptt.remote.features.channels.ChannelsRemoteImpl
import com.afaqy.ptt.remote.features.channels.service.ChannelsService
import com.afaqy.ptt.remote.features.editprofile.EditProfileRemoteImpl
import com.afaqy.ptt.remote.features.editprofile.service.EditProfileService
import com.afaqy.ptt.remote.features.login.LoginRemoteImpl
import com.afaqy.ptt.remote.features.login.service.LoginService
import com.afaqy.ptt.remote.features.profile.ProfileRemoteImpl
import com.afaqy.ptt.remote.features.profile.service.ProfileService
import com.afaqy.ptt.remote.features.recipes.service.RecipesService
import com.afaqy.ptt.remote.features.restaurants.RestaurantsRemoteImpl
import com.afaqy.ptt.remote.features.restaurants.service.RestaurantsService

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

        @Provides
        @JvmStatic
        fun provideProfileService(remoteServiceFactory: RemoteServiceFactory): ProfileService {
            return remoteServiceFactory.profileService
        }

        @Provides
        @JvmStatic
        fun provideEditProfileService(remoteServiceFactory: RemoteServiceFactory): EditProfileService {
            return remoteServiceFactory.editProfileService
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

    @Binds
    abstract fun bindProfileRemote(profileRemote: ProfileRemoteImpl): ProfileRemote

    @Binds
    abstract fun bindEditProfileRemote(EditProfileRemote: EditProfileRemoteImpl): EditProfileRemote
}