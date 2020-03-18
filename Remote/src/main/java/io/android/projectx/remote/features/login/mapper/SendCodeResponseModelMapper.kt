package io.android.projectx.remote.features.login.mapper

import io.android.projectx.data.features.login.model.SendCodeEntity
import io.android.projectx.remote.base.mapper.ModelMapper
import io.android.projectx.remote.features.login.model.SendCodeModel
import javax.inject.Inject

open class SendCodeResponseModelMapper @Inject constructor() :
    ModelMapper<SendCodeModel, SendCodeEntity> {

    override fun mapFromModel(model: SendCodeModel): SendCodeEntity {
        return SendCodeEntity(model.token,model.email)
    }

}