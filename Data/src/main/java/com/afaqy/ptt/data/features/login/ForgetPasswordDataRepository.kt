package com.afaqy.ptt.data.features.login

import com.afaqy.ptt.data.features.login.mapper.BaseMessageMapper
import com.afaqy.ptt.data.features.login.mapper.SendCodeMapper
import com.afaqy.ptt.data.features.login.repository.LoginCache
import com.afaqy.ptt.data.features.login.store.LoginDataStoreFactory
import com.afaqy.ptt.domain.features.login.model.BaseMessageModel
import com.afaqy.ptt.domain.features.login.model.SendCodeModel
import com.afaqy.ptt.domain.features.login.repository.ForgetPasswordRepository
import io.reactivex.Observable
import javax.inject.Inject

class ForgetPasswordDataRepository @Inject constructor(
    private val sendVerificationMapper: SendCodeMapper,
    private val mapper: BaseMessageMapper,
    private val cache: LoginCache,
    private val factory: LoginDataStoreFactory
) : ForgetPasswordRepository {

    override fun getVerificationCode(simSerial: String): Observable<BaseMessageModel> {
        return factory.getDataStore(false, true).getVerificationCode(simSerial)
            .toObservable().distinctUntilChanged()
            .map {
                mapper.mapFromEntity(it)
            }
    }

    override fun sendVerification(simSerial: String, code: String): Observable<SendCodeModel> {
        return factory.getDataStore(false, true).sendVerification(simSerial,code)
            .toObservable().distinctUntilChanged()
            .map {
                sendVerificationMapper.mapFromEntity(it)
            }
    }
}