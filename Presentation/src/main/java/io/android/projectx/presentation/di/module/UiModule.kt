package io.android.projectx.presentation.di.module

import dagger.Binds
import dagger.Module
import io.android.projectx.domain.base.executor.PostExecutionThread
import io.android.projectx.presentation.base.UiThread
import io.android.projectx.presentation.di.module.activity.ActivityBuildersModule
import io.android.projectx.presentation.di.module.activity.FragmentBuildersModule

@Module(includes = [ActivityBuildersModule::class, FragmentBuildersModule::class])
abstract class UiModule {

    @Binds
    abstract fun bindPostExecutionThread(uiThread: UiThread): PostExecutionThread
}