package io.android.ptt.presentation.base.mapper

import io.android.ptt.domain.features.login.model.SendCodeModel
import io.android.ptt.presentation.base.model.SendCodeView
import javax.inject.Inject

open class SendVerificationViewMapper @Inject constructor() : Mapper<SendCodeView, SendCodeModel> {

    override fun mapToView(type: SendCodeModel): SendCodeView {
        return SendCodeView(
            type.token,type.email
        )
    }
}