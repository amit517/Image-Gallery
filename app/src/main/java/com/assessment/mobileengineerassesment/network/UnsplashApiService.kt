package com.assessment.mobileengineerassesment.network

import com.assessment.mobileengineerassesment.model.ImageResponse
import com.assessment.mobileengineerassesment.network.model.GenericResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashApiService {

    @GET("photos/")
    suspend fun getImageCollection(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("order_by") orderBy: String?,
    ): GenericResponse<List<ImageResponse>>
}
