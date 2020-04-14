package com.afaqy.ptt.data.features.login.mapper

import com.afaqy.ptt.data.base.mapper.EntityMapper
import com.afaqy.ptt.data.features.login.model.SendCodeEntity
import com.afaqy.ptt.domain.features.login.model.SendCodeModel
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