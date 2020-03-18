package io.android.projectx.data.features.login.mapper

import io.android.projectx.data.base.mapper.EntityMapper
import io.android.projectx.data.features.login.model.SendCodeEntity
import io.android.projectx.domain.features.login.model.SendCodeModel
import javax.inject.Inject

open class SendCodeMapper @Inject constructor() :
    EntityMapper<SendCodeEntity, SendCodeModel> {

    override fun mapFromEntity(entity: SendCodeEntity): SendCodeModel {
        return SendCodeModel(
            entity.token,entity.email
        )
    }

    override fun mapToEntity(domain: SendCodeModel): SendCodeEntity {
        return SendCodeEntity(
            domain.token, domain.email
        )
    }

}