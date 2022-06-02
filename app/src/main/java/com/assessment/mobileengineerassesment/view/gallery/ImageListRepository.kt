package com.assessment.mobileengineerassesment.view.gallery

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.assessment.mobileengineerassesment.model.ImageResponse
import com.assessment.mobileengineerassesment.model.ImageSearchQuery
import com.assessment.mobileengineerassesment.network.UnsplashApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ImageListRepository @Inject constructor(
    private val apiService: UnsplashApiService,
) {
    fun getImageFromRepository(searchData: ImageSearchQuery): Flow<PagingData<ImageResponse>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                initialLoadSize = PAGE_SIZE * 2,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                ImagePagingSource(
                    imageSearchQuery = searchData,
                    apiService = apiService
                )
            }
        ).flow

    companion object {
        const val PAGE_SIZE = 10
    }
}
