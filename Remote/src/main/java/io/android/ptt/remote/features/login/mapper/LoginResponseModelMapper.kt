package io.android.ptt.remote.features.login.mapper

import io.android.ptt.data.features.login.model.LoginEntity
import io.android.ptt.remote.base.mapper.ModelMapper
import io.android.ptt.remote.features.login.model.LoginModel
import javax.inject.Inject

open class LoginResponseModelMapper @Inject constructor() :
    ModelMapper<LoginModel, LoginEntity> {

    override fun mapFromModel(model: LoginModel): LoginEntity {
        return LoginEntity(model.accessToken)
    }

}