package com.assessment.mobileengineerassesment.view.gallery

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.assessment.base.utils.MsgUtil.somethingWentWrong
import com.assessment.mobileengineerassesment.MsgUtil.networkErrorMsg
import com.assessment.mobileengineerassesment.model.ImageResponse
import com.assessment.mobileengineerassesment.model.ImageSearchQuery
import com.assessment.mobileengineerassesment.network.UnsplashApiService
import com.assessment.mobileengineerassesment.network.model.BaseResponse
import com.assessment.mobileengineerassesment.network.model.GenericResponse
import java.io.IOException

class ImagePagingSource(
    private val imageSearchQuery: ImageSearchQuery,
    private val apiService: UnsplashApiService,
    private val pageSize : Int
) : PagingSource<Int, ImageResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageResponse> {
        var imageResponseList: List<ImageResponse>? = null
        val position: Int = params.key ?: FIRST_PAGE_ITEM

        val response: GenericResponse<List<ImageResponse>> =
            apiService.getImageCollection(position,pageSize,imageSearchQuery.orderBy)

        return when (response) {
            is BaseResponse.Success -> {
                if (response.body.isNotEmpty()) {
                    val imageResponse = response.body
                    imageResponseList = imageResponse
                    createPage(imageResponseList, position)
                } else {
                    if (position == FIRST_PAGE_ITEM) LoadResult.Error(Exception("No Image found"))
                    else createPage(imageResponseList, position)
                }
            }
            is BaseResponse.ApiError -> LoadResult.Error(Exception(response.errorBody.errors.joinToString { "\n" }))

            is BaseResponse.NetworkError -> LoadResult.Error(IOException(networkErrorMsg))

            is BaseResponse.UnknownError -> LoadResult.Error(Exception(somethingWentWrong))
        }
    }

    private fun createPage(
        imageResponse: List<ImageResponse>?,
        position: Int,
    ) = LoadResult.Page(
        data = imageResponse.orEmpty(),
        prevKey = if (position == FIRST_PAGE_ITEM) null else position - 1,
        nextKey = if (imageResponse.isNullOrEmpty()) null else position + 1
    )

    companion object {
        const val FIRST_PAGE_ITEM = 1
    }
    
    override fun getRefreshKey(state: PagingState<Int, ImageResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
