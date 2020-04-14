package io.android.ptt.data.features.editprofile.mapper

import io.android.ptt.data.base.mapper.EntityMapper
import io.android.ptt.data.base.model.BaseEntity
import io.android.ptt.domain.features.login.model.BaseMessageModel
import javax.inject.Inject

open class EditProfileMapper @Inject constructor() :
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