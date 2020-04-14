package com.afaqy.ptt.data.features.editprofile.store

import com.afaqy.ptt.data.features.editprofile.repository.EditProfileDataStore
import com.afaqy.ptt.data.features.profile.repository.ProfileDataStore
import javax.inject.Inject

open class EditProfileDataStoreFactory @Inject constructor(
    private val editProfileRemoteDataStore: EditProfileRemoteDataStore
) {

    open fun getDataStore(loginCached: Boolean, cacheExpired: Boolean): EditProfileDataStore {
        return editProfileRemoteDataStore
    }

}