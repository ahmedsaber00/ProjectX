package io.android.projectx.presentation.base

import android.content.Context
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import io.android.projectx.presentation.di.DaggerAppComponent
import timber.log.Timber
import java.util.*

class BaseApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        mContext = this

        if (!PreferenceControl.loadLanguage(this).equals(PreferenceControl.ARABIC)) {
            LanguageUtil.changeLanguageType(this, Locale("en"))
        } else {
            LanguageUtil.changeLanguageType(this, Locale("ar"))
        }
        setupTimber()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }

    private fun setupTimber() {
        Timber.plant(Timber.DebugTree())
    }


    companion object {
        private var mContext: BaseApplication? = null
        @JvmStatic
        fun getContext(): Context? {
            return mContext
        }
    }
}