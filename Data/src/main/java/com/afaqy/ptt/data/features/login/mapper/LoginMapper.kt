package com.afaqy.ptt.data.features.login.mapper

import com.afaqy.ptt.data.base.mapper.EntityMapper
import com.afaqy.ptt.data.features.login.model.LoginEntity
import com.afaqy.ptt.domain.features.login.model.LoginModel
import javax.inject.Inject

open class LoginMapper @Inject constructor() :
    EntityMapper<LoginEntity, LoginModel> {

    override fun mapFromEntity(entity: LoginEntity): LoginModel {
        return LoginModel(
            entity.accessToken
        )
    }

    override fun mapToEntity(domain: LoginModel): LoginEntity {
        return LoginEntity(
            domain.accessToken
        )
    }

}