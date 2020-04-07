package io.android.projectx.data.features.profile

import io.android.projectx.data.features.login.mapper.BaseMessageMapper
import io.android.projectx.data.features.login.mapper.SendCodeMapper
import io.android.projectx.data.features.login.repository.LoginCache
import io.android.projectx.data.features.login.store.LoginDataStoreFactory
import io.android.projectx.data.features.profile.store.ProfileDataStoreFactory
import io.android.projectx.domain.features.login.model.BaseMessageModel
import io.android.projectx.domain.features.login.model.SendCodeModel
import io.android.projectx.domain.features.login.repository.ForgetPasswordRepository
import io.android.projectx.domain.features.profile.model.ProfileModel
import io.android.projectx.domain.features.profile.repository.LogoutRepository
import io.android.projectx.domain.features.profile.repository.ProfileRepository
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