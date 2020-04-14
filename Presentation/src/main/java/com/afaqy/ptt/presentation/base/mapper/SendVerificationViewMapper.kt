package com.afaqy.ptt.presentation.base.mapper

import com.afaqy.ptt.domain.features.login.model.SendCodeModel
import com.afaqy.ptt.presentation.base.model.SendCodeView
import javax.inject.Inject

open class SendVerificationViewMapper @Inject constructor() : Mapper<SendCodeView, SendCodeModel> {

    override fun mapToView(type: SendCodeModel): SendCodeView {
        return SendCodeView(
            type.token,type.email
        )
    }
}