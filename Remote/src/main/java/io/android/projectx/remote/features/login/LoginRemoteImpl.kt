package io.android.projectx.remote.features.login

import io.android.projectx.data.features.login.model.LoginEntity
import io.android.projectx.data.features.login.repository.LoginRemote
import io.android.projectx.remote.features.login.mapper.LoginResponseModelMapper
import io.android.projectx.remote.features.login.service.LoginService
import io.reactivex.Flowable
import javax.inject.Inject

class LoginRemoteImpl @Inject constructor(
    private val service: LoginService,
    private val mapper: LoginResponseModelMapper
) : LoginRemote {
    override fun login(username: String, password: String, imei: String): Flowable<LoginEntity> {
        return service.login(username, password, imei)
            .map {
                  mapper.mapFromModel(it.loginModel)
            }
    }
}