package io.android.projectx.presentation.base.mapper

import io.android.projectx.presentation.base.model.ForgetPasswordView
import io.android.projectx.domain.features.login.model.ForgetPasswordModel
import javax.inject.Inject

open class ForgetPasswordViewMapper @Inject constructor() :
    Mapper<ForgetPasswordView, ForgetPasswordModel> {

    override fun mapToView(type: ForgetPasswordModel): ForgetPasswordView {
        return ForgetPasswordView(
            type.message
        )
    }
}