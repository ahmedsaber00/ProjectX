package io.android.ptt.remote.base

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by Atef Etman on 4/16/19.
 */
class HeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder()
            .addHeader("Accept", "application/json")
            .addHeader("lang", "en")
            .build()
        return chain.proceed(request)
    }
}