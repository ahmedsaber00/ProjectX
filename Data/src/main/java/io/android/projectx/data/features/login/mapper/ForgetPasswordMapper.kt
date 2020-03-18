package io.android.projectx.data.features.login.mapper

import io.android.projectx.data.base.mapper.EntityMapper
import io.android.projectx.data.base.model.BaseEntity
import io.android.projectx.data.features.login.model.LoginEntity
import io.android.projectx.domain.features.login.model.ForgetPasswordModel
import javax.inject.Inject

open class ForgetPasswordMapper @Inject constructor() :
    EntityMapper<BaseEntity, ForgetPasswordModel> {

    override fun mapFromEntity(entity: BaseEntity): ForgetPasswordModel {
        return ForgetPasswordModel(
            entity.message
        )
    }

    override fun mapToEntity(domain: ForgetPasswordModel): BaseEntity {
        return BaseEntity(
            domain.message
        )
    }

}