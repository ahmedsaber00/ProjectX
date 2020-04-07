package io.android.projectx.presentation.base.mapper

import io.android.projectx.presentation.base.model.BaseMessageView
import io.android.projectx.domain.features.login.model.BaseMessageModel
import javax.inject.Inject

open class BaseViewMapper @Inject constructor() :
    Mapper<BaseMessageView, BaseMessageModel> {

    override fun mapToView(type: BaseMessageModel): BaseMessageView {
        return BaseMessageView(
            type.message
        )
    }
}