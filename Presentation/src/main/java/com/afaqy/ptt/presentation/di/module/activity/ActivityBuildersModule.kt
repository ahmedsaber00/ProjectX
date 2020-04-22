package com.afaqy.ptt.presentation.di.module.activity

import dagger.Module
import dagger.android.ContributesAndroidInjector
import com.afaqy.ptt.presentation.di.module.viewmodel.*
import com.afaqy.ptt.presentation.features.bookmarked.BookmarkedActivity
import com.afaqy.ptt.presentation.features.browse.BrowseActivity
import com.afaqy.ptt.presentation.features.channels.ChannelsActivity
import com.afaqy.ptt.presentation.features.editpassword.EditPasswordActivity
import com.afaqy.ptt.presentation.features.editprofile.EditProfileActivity
import com.afaqy.ptt.presentation.features.profile.ProfileActivity
import com.afaqy.ptt.presentation.features.restaurants.RestaurantsActivity

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = [BrowseViewModelModule::class])
    abstract fun contributesBrowseActivity(): BrowseActivity

    @ContributesAndroidInjector(modules = [BookmarkedViewModelModule::class])
    abstract fun contributesBookmarkedActivity(): BookmarkedActivity

    @ContributesAndroidInjector(modules = [RestaurantsViewModelModule::class])
    abstract fun contributesRestaurantsActivity(): RestaurantsActivity

    @ContributesAndroidInjector(modules = [ChannelsViewModelModule::class])
    abstract fun contributesChannelsActivity(): ChannelsActivity

    @ContributesAndroidInjector(modules = [ProfileViewModelModule::class])
    abstract fun contributesProfileActivity(): ProfileActivity

    @ContributesAndroidInjector(modules = [EditProfileViewModelModule::class])
    abstract fun contributesEditProfileActivity(): EditProfileActivity

    @ContributesAndroidInjector(modules = [EditPasswordViewModelModule::class])
    abstract fun contributesEditPasswordActivity(): EditPasswordActivity

}