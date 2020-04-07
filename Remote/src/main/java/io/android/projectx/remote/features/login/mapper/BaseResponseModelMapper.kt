package io.android.projectx.remote.features.login.mapper

import io.android.projectx.data.base.model.BaseEntity
import io.android.projectx.remote.base.mapper.ModelMapper
import io.android.projectx.remote.features.login.model.BaseMessageModel
import javax.inject.Inject

open class BaseResponseModelMapper @Inject constructor() :
    ModelMapper<BaseMessageModel, BaseEntity> {

    override fun mapFromModel(model: BaseMessageModel): BaseEntity {
        return BaseEntity(model.message)
    }

}