package io.android.ptt.remote.base.response

open class BaseResponseModel(
    var message: String = "",
    var errors: List<String> = emptyList()
)