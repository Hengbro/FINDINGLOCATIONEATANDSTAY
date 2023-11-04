package com.inyonghengki.findinglocation.core.data.source.remote.network

import com.inyonghengki.findinglocation.util.Pref
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
            .header("token", Pref.token)
        return chain.proceed(requestBuilder.build())
    }
}