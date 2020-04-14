package io.android.ptt.presentation.di.module.activity

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.android.ptt.presentation.di.module.viewmodel.*
import io.android.ptt.presentation.features.bookmarked.BookmarkedActivity
import io.android.ptt.presentation.features.browse.BrowseActivity
import io.android.ptt.presentation.features.channels.ChannelsActivity
import io.android.ptt.presentation.features.editprofile.EditProfileActivity
import io.android.ptt.presentation.features.profile.ProfileActivity
import io.android.ptt.presentation.features.restaurants.RestaurantsActivity

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

}