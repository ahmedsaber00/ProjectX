package com.afaqy.ptt.data.features.profile.store

import com.afaqy.ptt.data.features.profile.repository.ProfileDataStore
import javax.inject.Inject

open class ProfileDataStoreFactory @Inject constructor(
    private val profileRemoteDataStore: ProfileRemoteDataStore
) {

    open fun getDataStore(loginCached: Boolean, cacheExpired: Boolean): ProfileDataStore {
        return profileRemoteDataStore
    }

}