package io.android.ptt.presentation.base.mapper

import io.android.ptt.domain.features.login.model.LoginModel
import io.android.ptt.presentation.base.model.LoginView
import javax.inject.Inject

open class LoginViewMapper @Inject constructor() : Mapper<LoginView, LoginModel> {

    override fun mapToView(type: LoginModel): LoginView {
        return LoginView(
            type.accessToken
        )
    }
}