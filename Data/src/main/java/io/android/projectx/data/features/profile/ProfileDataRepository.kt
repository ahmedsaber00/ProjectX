package io.android.projectx.data.features.profile

import io.android.projectx.data.features.profile.mapper.ProfileMapper
import io.android.projectx.data.features.profile.store.ProfileDataStoreFactory
import io.android.projectx.domain.features.profile.model.ProfileModel
import io.android.projectx.domain.features.profile.repository.ProfileRepository
import io.reactivex.Observable
import javax.inject.Inject

class ProfileDataRepository @Inject constructor(
    private val mapper: ProfileMapper,
    private val factory: ProfileDataStoreFactory
) : ProfileRepository {

    override fun getProfile(authorization: String, page: String): Observable<ProfileModel> {
        return factory.getDataStore(true, true).getProfile(authorization,page)
            .toObservable().distinctUntilChanged()
            .map {
                mapper.mapFromEntity(it)
            }
    }

}