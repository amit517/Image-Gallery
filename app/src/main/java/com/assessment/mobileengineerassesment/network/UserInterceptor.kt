package com.assessment.mobileengineerassesment.network

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class UserInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val userAgentRequest = chain.request()
            .newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept-Version", "v1")
            .addHeader("Authorization", "Client-ID iE-12NYM4QepbkNKyHLKbyEaYzJ5vCzNCTa_Ps8JDEw")
            .build()
        return chain.proceed(userAgentRequest)
    }
}
