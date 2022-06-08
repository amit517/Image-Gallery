package com.assessment.mobileengineerassesment.mocprovider

import com.assessment.base.network.model.BaseResponse
import com.assessment.base.network.model.GenericResponse
import com.assessment.mobileengineerassesment.data.UnsplashApiService
import com.assessment.mobileengineerassesment.model.ImageResponse
import retrofit2.http.Query
import java.io.IOException

class FakeUnsplashApiService : UnsplashApiService {
    private var failureMsg: String? = null
    private val mutableImageList = mutableListOf<ImageResponse>()

    fun addImage(post: ImageResponse) {
        mutableImageList.add(post)
    }

    override suspend fun getImageCollection(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("order_by") orderBy: String?,
    ): GenericResponse<List<ImageResponse>> {
        failureMsg?.let {
            throw IOException(it)
        }
        val subList = sliceListAccordingToGivenParams(page, perPage)

        return BaseResponse.Success(subList)
    }

    private fun sliceListAccordingToGivenParams(
        page: Int,
        perPage: Int,
    ): MutableList<ImageResponse> {
        var i = 0
        val initialPosition = 2 * (page - 1)
        val subList = mutableListOf<ImageResponse>()
        while (i < perPage) {
            if (mutableImageList.toList().size > (initialPosition + i)) {
                subList.add(mutableImageList[initialPosition + i])
            }
            i++
        }
        return subList
    }
}
