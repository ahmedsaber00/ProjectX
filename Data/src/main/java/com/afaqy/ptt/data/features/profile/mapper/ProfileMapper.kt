package com.afaqy.ptt.data.features.profile.mapper

import com.afaqy.ptt.data.base.mapper.EntityMapper
import com.afaqy.ptt.data.features.profile.model.ProfileEntity
import com.afaqy.ptt.domain.features.profile.model.ProfileModel
import javax.inject.Inject

open class ProfileMapper @Inject constructor() :
    EntityMapper<ProfileEntity, ProfileModel> {

    override fun mapFromEntity(entity: ProfileEntity): ProfileModel {
        return ProfileModel(
            entity.name,entity.email,
            entity.countryCode,entity.mobile,
            entity.address,entity.photo,entity.ssn
        )
    }

    override fun mapToEntity(domain: ProfileModel): ProfileEntity {
        return ProfileEntity(
            domain.name?:"",domain.email?:"",
            domain.countryCode?:"",domain.mobile?:"",
            domain.address?:"",domain.photo?:"",domain.ssn?:""
        )
    }

}