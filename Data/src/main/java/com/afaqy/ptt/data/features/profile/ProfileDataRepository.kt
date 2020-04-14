package com.afaqy.ptt.data.features.profile

import com.afaqy.ptt.data.features.profile.mapper.ProfileMapper
import com.afaqy.ptt.data.features.profile.store.ProfileDataStoreFactory
import com.afaqy.ptt.domain.features.profile.model.ProfileModel
import com.afaqy.ptt.domain.features.profile.repository.ProfileRepository
import io.reactivex.Observable
import javax.inject.Inject

class ProfileDataRepository @Inject constructor(
    private val mapper: ProfileMapper,
    private val factory: ProfileDataStoreFactory
) : ProfileRepository {

    override fun getProfile(authorization: String): Observable<ProfileModel> {
        return factory.getDataStore(true, true).getProfile(authorization)
            .toObservable().distinctUntilChanged()
            .map {
                mapper.mapFromEntity(it)
            }
    }

}