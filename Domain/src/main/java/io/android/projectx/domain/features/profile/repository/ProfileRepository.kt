package io.android.projectx.domain.features.profile.repository

import io.android.projectx.domain.features.profile.model.ProfileModel
import io.reactivex.Observable

interface ProfileRepository {

    fun getProfile(authorization: String,page:String): Observable<ProfileModel>

}