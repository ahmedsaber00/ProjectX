package io.android.ptt.data.features.profile

import io.android.ptt.data.features.login.mapper.BaseMessageMapper
import io.android.ptt.data.features.profile.store.ProfileDataStoreFactory
import io.android.ptt.domain.features.login.model.BaseMessageModel
import io.android.ptt.domain.features.profile.repository.LogoutRepository
import io.reactivex.Observable
import javax.inject.Inject

class LogoutDataRepository @Inject constructor(
    private val mapper: BaseMessageMapper,
    private val factory: ProfileDataStoreFactory
) :LogoutRepository {

    override fun logout(authorization: String, imei: String, simSerial: String): Observable<BaseMessageModel> {
        return factory.getDataStore(false, true).logout(authorization,imei,simSerial)
            .toObservable().distinctUntilChanged()
            .map {
                mapper.mapFromEntity(it)
            }
    }
}