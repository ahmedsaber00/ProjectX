package com.afaqy.ptt.presentation.base.mapper

import com.afaqy.ptt.domain.features.login.model.LoginModel
import com.afaqy.ptt.presentation.base.model.LoginView
import javax.inject.Inject

open class LoginViewMapper @Inject constructor() : Mapper<LoginView, LoginModel> {

    override fun mapToView(type: LoginModel): LoginView {
        return LoginView(
            type.accessToken
        )
    }
}