package io.android.projectx.presentation.base.mapper

import io.android.projectx.domain.features.login.model.SendCodeModel
import io.android.projectx.presentation.base.model.SendCodeView
import javax.inject.Inject

open class SendVerificationViewMapper @Inject constructor() : Mapper<SendCodeView, SendCodeModel> {

    override fun mapToView(type: SendCodeModel): SendCodeView {
        return SendCodeView(
            type.token,type.email
        )
    }
}