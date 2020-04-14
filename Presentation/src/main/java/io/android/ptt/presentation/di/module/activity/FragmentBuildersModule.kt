package io.android.ptt.presentation.di.module.activity

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.android.ptt.presentation.di.module.viewmodel.LoginViewModelModule
import io.android.ptt.presentation.features.login.ForgetPasswordFragment
import io.android.ptt.presentation.features.login.LoginFragment
import io.android.ptt.presentation.features.login.VerifyFragment

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector(modules = [LoginViewModelModule::class])
    abstract fun contributesLoginFragment(): LoginFragment

    @ContributesAndroidInjector(modules = [LoginViewModelModule::class])
    abstract fun contributesForgetPasswordFragment(): ForgetPasswordFragment

    @ContributesAndroidInjector(modules = [LoginViewModelModule::class])
    abstract fun contributesVerifyFragment(): VerifyFragment

}