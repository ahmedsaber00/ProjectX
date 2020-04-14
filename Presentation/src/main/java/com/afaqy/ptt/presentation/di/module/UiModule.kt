package com.afaqy.ptt.presentation.di.module

import dagger.Binds
import dagger.Module
import com.afaqy.ptt.domain.base.executor.PostExecutionThread
import com.afaqy.ptt.presentation.base.UiThread
import com.afaqy.ptt.presentation.di.module.activity.ActivityBuildersModule
import com.afaqy.ptt.presentation.di.module.activity.FragmentBuildersModule

@Module(includes = [ActivityBuildersModule::class, FragmentBuildersModule::class])
abstract class UiModule {

    @Binds
    abstract fun bindPostExecutionThread(uiThread: UiThread): PostExecutionThread
}