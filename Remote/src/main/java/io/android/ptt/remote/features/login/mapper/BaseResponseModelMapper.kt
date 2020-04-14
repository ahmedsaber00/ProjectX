package io.android.ptt.remote.features.login.mapper

import io.android.ptt.data.base.model.BaseEntity
import io.android.ptt.remote.base.mapper.ModelMapper
import io.android.ptt.remote.features.login.model.BaseMessageModel
import javax.inject.Inject

open class BaseResponseModelMapper @Inject constructor() :
    ModelMapper<BaseMessageModel, BaseEntity> {

    override fun mapFromModel(model: BaseMessageModel): BaseEntity {
        return BaseEntity(model.message)
    }

}