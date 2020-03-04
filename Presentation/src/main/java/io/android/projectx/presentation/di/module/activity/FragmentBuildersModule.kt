package io.android.projectx.presentation.di.module.activity

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.android.projectx.presentation.di.module.viewmodel.LoginViewModelModule
import io.android.projectx.presentation.features.login.LoginFragment

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector(modules = [LoginViewModelModule::class])
    abstract fun contributesLoginFragment(): LoginFragment

}