package io.android.ptt.presentation.di.module

import dagger.Binds
import dagger.Module
import io.android.ptt.data.features.channels.ChannelsDataRepository
import io.android.ptt.data.features.editprofile.EditProfileDataRepository
import io.android.ptt.data.features.login.ForgetPasswordDataRepository
import io.android.ptt.data.features.login.LoginDataRepository
import io.android.ptt.data.features.profile.LogoutDataRepository
import io.android.ptt.data.features.profile.ProfileDataRepository
import io.android.ptt.data.features.recipes.RecipesDataRepository
import io.android.ptt.data.features.restaurants.RestaurantsDataRepository
import io.android.ptt.domain.features.channels.repository.ChannelsRepository
import io.android.ptt.domain.features.editprofile.repository.EditProfileRepository
import io.android.ptt.domain.features.login.repository.ForgetPasswordRepository
import io.android.ptt.domain.features.login.repository.LoginRepository
import io.android.ptt.domain.features.profile.repository.LogoutRepository
import io.android.ptt.domain.features.profile.repository.ProfileRepository
import io.android.ptt.domain.features.recipes.repository.RecipesRepository
import io.android.ptt.domain.features.restaurants.repository.RestaurantsRepository

@Module
abstract class DataModule {

    @Binds
    abstract fun bindForgetPasswordRepository(forgetPasswordDataRepository: ForgetPasswordDataRepository): ForgetPasswordRepository

    @Binds
    abstract fun bindLoginRepository(loginRepository: LoginDataRepository): LoginRepository

    @Binds
    abstract fun bindDataRepository(dataRepository: RecipesDataRepository): RecipesRepository

    @Binds
    abstract fun bindRestaurantsRepository(dataRepository: RestaurantsDataRepository): RestaurantsRepository

    @Binds
    abstract fun bindChannelsRepository(dataRepository: ChannelsDataRepository): ChannelsRepository

    @Binds
    abstract fun bindProfileRepository(dataRepository: ProfileDataRepository): ProfileRepository

    @Binds
    abstract fun bindLogoutRepository(dataRepository: LogoutDataRepository): LogoutRepository

    @Binds
    abstract fun bindEditProfileRepository(dataRepository: EditProfileDataRepository): EditProfileRepository
}