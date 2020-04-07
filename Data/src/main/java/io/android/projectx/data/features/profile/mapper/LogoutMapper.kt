package io.android.projectx.data.features.profile.mapper

import io.android.projectx.data.base.mapper.EntityMapper
import io.android.projectx.data.base.model.BaseEntity
import io.android.projectx.domain.features.login.model.BaseMessageModel
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