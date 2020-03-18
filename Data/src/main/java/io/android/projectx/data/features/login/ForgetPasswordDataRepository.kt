package io.android.projectx.data.features.login

import io.android.projectx.data.features.login.mapper.ForgetPasswordMapper
import io.android.projectx.data.features.login.mapper.SendCodeMapper
import io.android.projectx.data.features.login.repository.LoginCache
import io.android.projectx.data.features.login.store.LoginDataStoreFactory
import io.android.projectx.domain.features.login.model.ForgetPasswordModel
import io.android.projectx.domain.features.login.model.SendCodeModel
import io.android.projectx.domain.features.login.repository.ForgetPasswordRepository
import io.reactivex.Observable
import javax.inject.Inject

class ForgetPasswordDataRepository @Inject constructor(
    private val sendVerificationMapper: SendCodeMapper,
    private val mapper: ForgetPasswordMapper,
    private val cache: LoginCache,
    private val factory: LoginDataStoreFactory
) : ForgetPasswordRepository {

    override fun getVerificationCode(simSerial: String): Observable<ForgetPasswordModel> {
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