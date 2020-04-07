package io.android.projectx.remote.features.login

import io.android.projectx.data.base.model.BaseEntity
import io.android.projectx.data.features.login.model.LoginEntity
import io.android.projectx.data.features.login.model.SendCodeEntity
import io.android.projectx.data.features.login.repository.LoginRemote
import io.android.projectx.remote.features.login.mapper.BaseResponseModelMapper
import io.android.projectx.remote.features.login.mapper.LoginResponseModelMapper
import io.android.projectx.remote.features.login.mapper.SendCodeResponseModelMapper
import io.android.projectx.remote.features.login.model.BaseMessageModel
import io.android.projectx.remote.features.login.service.LoginService
import io.reactivex.Flowable
import javax.inject.Inject

class LoginRemoteImpl @Inject constructor(
    private val service: LoginService,
    private val loginMapper: LoginResponseModelMapper,
    private val sendCodeResponseModelMapper: SendCodeResponseModelMapper,
    private val forgetPasswordmapper: BaseResponseModelMapper
) : LoginRemote {
    override fun login(
        username: String,
        password: String,
        imei: String,
        simSerial: String
    ): Flowable<LoginEntity> {
        return service.login(username, password, imei, simSerial)
            .map {
                loginMapper.mapFromModel(it.loginModel)
            }
    }

    override fun getVerificationCode(simSerial: String): Flowable<BaseEntity> {
        return service.getVerificationCode(simSerial)
            .map {
                forgetPasswordmapper.mapFromModel(BaseMessageModel(it.message))
            }
    }

    override fun sendVerification(simSerial: String, code: String): Flowable<SendCodeEntity> {
        return service.sendVerification(simSerial,code)
            .map {
                sendCodeResponseModelMapper.mapFromModel(it.sendCodeModel)
            }
    }

    override fun resetPassword(token: String,simSerial: String,password: String,passwordConfirmation: String): Flowable<BaseEntity> {
        return service.resetPassword(token,simSerial,password,passwordConfirmation)
            .map {
                forgetPasswordmapper.mapFromModel(BaseMessageModel(it.message))
            }
    }
}