package com.assessment.base.network

import com.assessment.base.network.model.BaseResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Converter
import java.lang.reflect.Type

class ResponseAdapter<S : Any, E : Any>(
    private val successType: Type,
    private val errorBodyConverter: Converter<ResponseBody, E>,
) : CallAdapter<S, Call<BaseResponse<S, E>>> {

    override fun responseType(): Type = successType

    override fun adapt(call: Call<S>): Call<BaseResponse<S, E>> {
        return ResponseCall(call, errorBodyConverter)
    }
}
