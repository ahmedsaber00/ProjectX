package com.afaqy.ptt.domain.features.login.repository

import com.afaqy.ptt.domain.features.login.model.BaseMessageModel
import com.afaqy.ptt.domain.features.login.model.SendCodeModel
import io.reactivex.Observable

interface ForgetPasswordRepository {

    fun getVerificationCode(simSerial: String): Observable<BaseMessageModel>

    fun sendVerification(simSerial: String, code: String): Observable<SendCodeModel>

}