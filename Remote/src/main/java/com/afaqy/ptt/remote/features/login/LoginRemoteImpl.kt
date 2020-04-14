package com.afaqy.ptt.remote.features.login

import com.afaqy.ptt.data.base.model.BaseEntity
import com.afaqy.ptt.data.features.login.model.LoginEntity
import com.afaqy.ptt.data.features.login.model.SendCodeEntity
import com.afaqy.ptt.data.features.login.repository.LoginRemote
import com.afaqy.ptt.remote.features.login.mapper.BaseResponseModelMapper
import com.afaqy.ptt.remote.features.login.mapper.LoginResponseModelMapper
import com.afaqy.ptt.remote.features.login.mapper.SendCodeResponseModelMapper
import com.afaqy.ptt.remote.features.login.model.BaseMessageModel
import com.afaqy.ptt.remote.features.login.service.LoginService
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