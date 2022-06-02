package com.assessment.mobileengineerassesment.network

import com.assessment.mobileengineerassesment.model.ImageResponse
import com.assessment.mobileengineerassesment.network.model.GenericResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashApiService {

    @GET("photos/")
    suspend fun getImageCollection(@Query("page") page: Int): GenericResponse<List<ImageResponse>>
}
