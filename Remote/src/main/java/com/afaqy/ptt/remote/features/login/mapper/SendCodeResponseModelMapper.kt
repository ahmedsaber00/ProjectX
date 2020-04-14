package com.afaqy.ptt.remote.features.login.mapper

import com.afaqy.ptt.data.features.login.model.SendCodeEntity
import com.afaqy.ptt.remote.base.mapper.ModelMapper
import com.afaqy.ptt.remote.features.login.model.SendCodeModel
import javax.inject.Inject

open class SendCodeResponseModelMapper @Inject constructor() :
    ModelMapper<SendCodeModel, SendCodeEntity> {

    override fun mapFromModel(model: SendCodeModel): SendCodeEntity {
        return SendCodeEntity(model.token,model.email)
    }

}