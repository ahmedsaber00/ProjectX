package com.afaqy.ptt.remote.features.editprofile.mapper

import com.afaqy.ptt.data.features.profile.model.ProfileEntity
import com.afaqy.ptt.remote.base.mapper.ModelMapper
import com.afaqy.ptt.remote.features.profile.model.ProfileModel
import javax.inject.Inject

open class EditProfileResponseModelMapper @Inject constructor() :
    ModelMapper<ProfileModel, ProfileEntity> {

    override fun mapFromModel(model: ProfileModel): ProfileEntity {
        return ProfileEntity(
            model.name?:"",model.email?:"",
            model.countryCode?:"",model.mobile?:"",
            model.address?:"",model.photo?:"",model.ssn?:""
        )
    }

}