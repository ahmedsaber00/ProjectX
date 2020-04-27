package com.afaqy.ptt.presentation.base.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProfileView (
    var username: String?,
    var name: String?,
    var email: String?,
    var countryCode: String?,
    var mobile: String?,
    var address: String?,
    var photo: String?,
    var ssn: String?
): Parcelable
