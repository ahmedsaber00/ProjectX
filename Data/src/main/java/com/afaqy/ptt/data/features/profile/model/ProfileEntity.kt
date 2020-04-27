package com.afaqy.ptt.data.features.profile.model

class ProfileEntity(
    val username: String,
    val name: String,
    val email: String,
    var countryCode: String,
    var mobile: String,
    var address: String,
    var photo: String,
    var ssn: String
)