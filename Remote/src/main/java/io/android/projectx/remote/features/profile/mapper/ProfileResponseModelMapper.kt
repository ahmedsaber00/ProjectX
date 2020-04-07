package io.android.projectx.remote.features.profile.mapper

import io.android.projectx.data.features.profile.model.ProfileEntity
import io.android.projectx.remote.base.mapper.ModelMapper
import io.android.projectx.remote.features.profile.model.ProfileModel
import javax.inject.Inject

open class ProfileResponseModelMapper @Inject constructor() :
    ModelMapper<ProfileModel, ProfileEntity> {

    override fun mapFromModel(model: ProfileModel): ProfileEntity {
        return ProfileEntity(
            model.code
        )
    }

}