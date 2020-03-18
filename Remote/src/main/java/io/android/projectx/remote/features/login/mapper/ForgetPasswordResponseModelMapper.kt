package io.android.projectx.remote.features.login.mapper

import io.android.projectx.data.base.model.BaseEntity
import io.android.projectx.remote.base.mapper.ModelMapper
import io.android.projectx.remote.features.login.model.ForgetPasswordModel
import javax.inject.Inject

open class ForgetPasswordResponseModelMapper @Inject constructor() :
    ModelMapper<ForgetPasswordModel, BaseEntity> {

    override fun mapFromModel(model: ForgetPasswordModel): BaseEntity {
        return BaseEntity(model.message)
    }

}