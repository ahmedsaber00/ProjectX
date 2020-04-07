package io.android.projectx.presentation.base.mapper

import io.android.projectx.domain.features.profile.model.ProfileModel
import io.android.projectx.presentation.base.model.ProfileView
import javax.inject.Inject

open class ProfileViewMapper @Inject constructor() : Mapper<ProfileView, ProfileModel> {

    override fun mapToView(type: ProfileModel): ProfileView {
        return ProfileView(
            type.code,
            type.name,
            false
        )
    }
}