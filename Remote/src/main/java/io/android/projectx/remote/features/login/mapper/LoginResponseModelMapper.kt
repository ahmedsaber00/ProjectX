package io.android.projectx.remote.features.login.mapper

import io.android.projectx.data.features.login.model.LoginEntity
import io.android.projectx.remote.base.mapper.ModelMapper
import io.android.projectx.remote.features.login.model.LoginModel
import javax.inject.Inject

open class LoginResponseModelMapper @Inject constructor() :
    ModelMapper<LoginModel, LoginEntity> {

    override fun mapFromModel(model: LoginModel): LoginEntity {
        return LoginEntity(model.accessToken)
    }

}