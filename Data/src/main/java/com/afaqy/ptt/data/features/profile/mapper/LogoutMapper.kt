package com.afaqy.ptt.data.features.profile.mapper

import com.afaqy.ptt.data.base.mapper.EntityMapper
import com.afaqy.ptt.data.base.model.BaseEntity
import com.afaqy.ptt.domain.features.login.model.BaseMessageModel
import javax.inject.Inject

open class LogoutMapper @Inject constructor() :
    EntityMapper<BaseEntity, BaseMessageModel> {

    override fun mapFromEntity(entity: BaseEntity): BaseMessageModel {
        return BaseMessageModel(
            entity.message
        )
    }

    override fun mapToEntity(domain: BaseMessageModel): BaseEntity {
        return BaseEntity(
            domain.message
        )
    }

}