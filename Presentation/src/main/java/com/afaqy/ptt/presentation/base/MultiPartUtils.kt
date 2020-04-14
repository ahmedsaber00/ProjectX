package com.afaqy.ptt.presentation.base

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File


fun createPartFromString(descriptionString: String): RequestBody {
    return descriptionString.toRequestBody(MultipartBody.FORM)
}

fun prepareImagePart(partName: String, file: File): MultipartBody.Part {
    return MultipartBody.Part.createFormData(
        partName, file.name, file.asRequestBody("image/*".toMediaTypeOrNull())
    )
}