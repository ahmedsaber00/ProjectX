package io.android.projectx.domain.features.profile.repository

import io.android.projectx.domain.features.login.model.BaseMessageModel
import io.reactivex.Observable

interface LogoutRepository {

    fun logout(authorization: String, imei: String, simSerial: String): Observable<BaseMessageModel>

}