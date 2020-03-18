package io.android.projectx.presentation.di.module

import dagger.Binds
import dagger.Module
import io.android.projectx.data.features.channels.ChannelsDataRepository
import io.android.projectx.data.features.login.ForgetPasswordDataRepository
import io.android.projectx.data.features.login.LoginDataRepository
import io.android.projectx.data.features.recipes.RecipesDataRepository
import io.android.projectx.data.features.restaurants.RestaurantsDataRepository
import io.android.projectx.domain.features.channels.repository.ChannelsRepository
import io.android.projectx.domain.features.login.repository.ForgetPasswordRepository
import io.android.projectx.domain.features.login.repository.LoginRepository
import io.android.projectx.domain.features.recipes.repository.RecipesRepository
import io.android.projectx.domain.features.restaurants.repository.RestaurantsRepository

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
}