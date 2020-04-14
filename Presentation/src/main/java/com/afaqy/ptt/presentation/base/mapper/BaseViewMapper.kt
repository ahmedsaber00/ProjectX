package com.afaqy.ptt.presentation.base.mapper

import com.afaqy.ptt.presentation.base.model.BaseMessageView
import com.afaqy.ptt.domain.features.login.model.BaseMessageModel
import javax.inject.Inject

open class BaseViewMapper @Inject constructor() :
    Mapper<BaseMessageView, BaseMessageModel> {

    override fun mapToView(type: BaseMessageModel): BaseMessageView {
        return BaseMessageView(
            type.message
        )
    }
}