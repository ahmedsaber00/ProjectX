package io.android.projectx.remote.base.response

open class BaseResponseModel(
    var message: String = "",
    var errors: List<String> = emptyList()
)