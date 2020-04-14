package com.afaqy.ptt.presentation.di.module.activity

import dagger.Module
import dagger.android.ContributesAndroidInjector
import com.afaqy.ptt.presentation.di.module.viewmodel.LoginViewModelModule
import com.afaqy.ptt.presentation.features.login.ForgetPasswordFragment
import com.afaqy.ptt.presentation.features.login.LoginFragment
import com.afaqy.ptt.presentation.features.login.VerifyFragment

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector(modules = [LoginViewModelModule::class])
    abstract fun contributesLoginFragment(): LoginFragment

    @ContributesAndroidInjector(modules = [LoginViewModelModule::class])
    abstract fun contributesForgetPasswordFragment(): ForgetPasswordFragment

    @ContributesAndroidInjector(modules = [LoginViewModelModule::class])
    abstract fun contributesVerifyFragment(): VerifyFragment

}