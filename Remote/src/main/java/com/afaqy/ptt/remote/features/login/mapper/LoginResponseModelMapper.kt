package com.afaqy.ptt.remote.features.login.mapper

import com.afaqy.ptt.data.features.login.model.LoginEntity
import com.afaqy.ptt.remote.base.mapper.ModelMapper
import com.afaqy.ptt.remote.features.login.model.LoginModel
import javax.inject.Inject

open class LoginResponseModelMapper @Inject constructor() :
    ModelMapper<LoginModel, LoginEntity> {

    override fun mapFromModel(model: LoginModel): LoginEntity {
        return LoginEntity(model.accessToken)
    }

}