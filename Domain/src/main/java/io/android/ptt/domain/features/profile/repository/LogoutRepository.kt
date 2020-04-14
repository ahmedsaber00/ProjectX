package io.android.ptt.domain.features.profile.repository

import io.android.ptt.domain.features.login.model.BaseMessageModel
import io.reactivex.Observable

interface LogoutRepository {

    fun logout(authorization: String, imei: String, simSerial: String): Observable<BaseMessageModel>

}