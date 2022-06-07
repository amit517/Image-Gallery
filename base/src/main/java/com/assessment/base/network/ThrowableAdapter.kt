package com.assessment.base.network

import com.squareup.moshi.ToJson
import com.squareup.moshi.FromJson

class ThrowableAdapter {

    @ToJson
    fun toJson(throwable: Throwable): String {
        return throwable.message ?: "Error not identified"
    }

    @FromJson
    fun fromJson(message: String): Throwable {
        return Throwable(message)
    }
}
