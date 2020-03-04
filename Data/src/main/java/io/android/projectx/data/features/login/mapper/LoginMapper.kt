package io.android.projectx.data.features.login.mapper

import io.android.projectx.data.base.mapper.EntityMapper
import io.android.projectx.data.features.login.model.LoginEntity
import io.android.projectx.domain.features.login.model.LoginModel
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