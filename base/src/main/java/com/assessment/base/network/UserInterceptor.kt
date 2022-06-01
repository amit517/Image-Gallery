package com.assessment.base.network

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class UserInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val userAgentRequest = chain.request()
            .newBuilder()
            .addHeader("Accept", "application/json")
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept-Version", "v1")
            //.addHeader("client_id", "v1") todo will replace this from gradle string later
            .build()
        return chain.proceed(userAgentRequest)
    }
}
