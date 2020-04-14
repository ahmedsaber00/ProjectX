package com.afaqy.ptt.remote.features.login.mapper

import com.afaqy.ptt.data.base.model.BaseEntity
import com.afaqy.ptt.remote.base.mapper.ModelMapper
import com.afaqy.ptt.remote.features.login.model.BaseMessageModel
import javax.inject.Inject

open class BaseResponseModelMapper @Inject constructor() :
    ModelMapper<BaseMessageModel, BaseEntity> {

    override fun mapFromModel(model: BaseMessageModel): BaseEntity {
        return BaseEntity(model.message)
    }

}