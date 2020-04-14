package com.afaqy.ptt.presentation.di.module

import dagger.Binds
import dagger.Module
import com.afaqy.ptt.data.features.channels.ChannelsDataRepository
import com.afaqy.ptt.data.features.editprofile.EditProfileDataRepository
import com.afaqy.ptt.data.features.login.ForgetPasswordDataRepository
import com.afaqy.ptt.data.features.login.LoginDataRepository
import com.afaqy.ptt.data.features.profile.LogoutDataRepository
import com.afaqy.ptt.data.features.profile.ProfileDataRepository
import com.afaqy.ptt.data.features.recipes.RecipesDataRepository
import com.afaqy.ptt.data.features.restaurants.RestaurantsDataRepository
import com.afaqy.ptt.domain.features.channels.repository.ChannelsRepository
import com.afaqy.ptt.domain.features.editprofile.repository.EditProfileRepository
import com.afaqy.ptt.domain.features.login.repository.ForgetPasswordRepository
import com.afaqy.ptt.domain.features.login.repository.LoginRepository
import com.afaqy.ptt.domain.features.profile.repository.LogoutRepository
import com.afaqy.ptt.domain.features.profile.repository.ProfileRepository
import com.afaqy.ptt.domain.features.recipes.repository.RecipesRepository
import com.afaqy.ptt.domain.features.restaurants.repository.RestaurantsRepository

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