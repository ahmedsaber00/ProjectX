package io.android.ptt.presentation.di.module

import dagger.Binds
import dagger.Module
import dagger.Provides
import io.android.ptt.data.features.channels.repository.ChannelsRemote
import io.android.ptt.data.features.editprofile.repository.EditProfileRemote
import io.android.ptt.data.features.login.repository.LoginRemote
import io.android.ptt.data.features.profile.repository.ProfileRemote
import io.android.ptt.data.features.recipes.repository.RecipesRemote
import io.android.ptt.data.features.restaurants.repository.RestaurantsRemote
import io.android.ptt.presentation.BuildConfig
import io.android.ptt.remote.features.recipes.RecipesRemoteImpl
import io.android.ptt.remote.features.RemoteServiceFactory
import io.android.ptt.remote.features.channels.ChannelsRemoteImpl
import io.android.ptt.remote.features.channels.service.ChannelsService
import io.android.ptt.remote.features.editprofile.EditProfileRemoteImpl
import io.android.ptt.remote.features.editprofile.service.EditProfileService
import io.android.ptt.remote.features.login.LoginRemoteImpl
import io.android.ptt.remote.features.login.service.LoginService
import io.android.ptt.remote.features.profile.ProfileRemoteImpl
import io.android.ptt.remote.features.profile.service.ProfileService
import io.android.ptt.remote.features.recipes.service.RecipesService
import io.android.ptt.remote.features.restaurants.RestaurantsRemoteImpl
import io.android.ptt.remote.features.restaurants.service.RestaurantsService

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