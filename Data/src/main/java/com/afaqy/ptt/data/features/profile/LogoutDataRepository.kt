package com.afaqy.ptt.data.features.profile

import com.afaqy.ptt.data.features.login.mapper.BaseMessageMapper
import com.afaqy.ptt.data.features.profile.store.ProfileDataStoreFactory
import com.afaqy.ptt.domain.features.login.model.BaseMessageModel
import com.afaqy.ptt.domain.features.profile.repository.LogoutRepository
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