package io.android.ptt.data.features.profile

import io.android.ptt.data.features.profile.mapper.ProfileMapper
import io.android.ptt.data.features.profile.store.ProfileDataStoreFactory
import io.android.ptt.domain.features.profile.model.ProfileModel
import io.android.ptt.domain.features.profile.repository.ProfileRepository
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