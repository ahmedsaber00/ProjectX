package io.android.ptt.presentation.di.module

import dagger.Binds
import dagger.Module
import io.android.ptt.domain.base.executor.PostExecutionThread
import io.android.ptt.presentation.base.UiThread
import io.android.ptt.presentation.di.module.activity.ActivityBuildersModule
import io.android.ptt.presentation.di.module.activity.FragmentBuildersModule

@Module(includes = [ActivityBuildersModule::class, FragmentBuildersModule::class])
abstract class UiModule {

    @Binds
    abstract fun bindPostExecutionThread(uiThread: UiThread): PostExecutionThread
}