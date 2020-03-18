package io.android.projectx.domain.features.login.repository

import io.android.projectx.domain.features.login.model.ForgetPasswordModel
import io.android.projectx.domain.features.login.model.SendCodeModel
import io.reactivex.Observable

interface ForgetPasswordRepository {

    fun getVerificationCode(simSerial: String): Observable<ForgetPasswordModel>

    fun sendVerification(simSerial: String, code: String): Observable<SendCodeModel>

}