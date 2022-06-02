package com.assessment.mobileengineerassesment.network.model

import java.io.IOException

sealed class BaseResponse<out T : Any, out U : Any> {
    data class Success<T : Any>(val body: T) : BaseResponse<T, Nothing>()

    data class ApiError<U : Any>(val errorBody: U, val code: Int) : BaseResponse<Nothing, U>()

    data class NetworkError(val error: IOException) : BaseResponse<Nothing, Nothing>()

    data class UnknownError(val error: Throwable?) : BaseResponse<Nothing, Nothing>()
}

typealias GenericResponse<S> = BaseResponse<S, GenericError>
