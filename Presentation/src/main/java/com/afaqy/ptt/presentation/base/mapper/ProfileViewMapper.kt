package com.afaqy.ptt.presentation.base.mapper

import com.afaqy.ptt.domain.features.profile.model.ProfileModel
import com.afaqy.ptt.presentation.base.model.ProfileView
import javax.inject.Inject

open class ProfileViewMapper @Inject constructor() : Mapper<ProfileView, ProfileModel> {

    override fun mapToView(type: ProfileModel): ProfileView {
        return ProfileView(
            type.name,
            type.email,
            type.countryCode,
            type.mobile,
            type.address,
            type.photo,
            type.ssn
        )
    }
}