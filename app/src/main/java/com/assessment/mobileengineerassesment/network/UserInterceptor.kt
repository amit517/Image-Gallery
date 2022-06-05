package com.assessment.mobileengineerassesment.network

import com.assessment.base.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class UserInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val userAgentRequest = chain.request()
            .newBuilder()
            .addHeader(CONTENT_TYPE, APPLICATION_JSON)
            .addHeader(ACCEPT_VERSION, "v1")
            .addHeader(AUTHORIZATION, "Client-ID ${BuildConfig.UNSPLASH_API_API_TOKEN}}")
            .build()
        return chain.proceed(userAgentRequest)
    }

    companion object{
        private const val CONTENT_TYPE = "Content-Type"
        private const val APPLICATION_JSON = "application/json"
        private const val ACCEPT_VERSION = "Accept-Version"
        private const val AUTHORIZATION = "Authorization"
    }
}
