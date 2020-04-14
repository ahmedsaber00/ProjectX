package io.android.ptt.domain.features.profile.repository

import io.android.ptt.domain.features.profile.model.ProfileModel
import io.reactivex.Observable

interface ProfileRepository {

    fun getProfile(authorization: String): Observable<ProfileModel>

}