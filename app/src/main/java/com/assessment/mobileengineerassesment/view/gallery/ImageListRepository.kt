package com.assessment.mobileengineerassesment.view.gallery

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.assessment.mobileengineerassesment.model.ImageResponse
import com.assessment.mobileengineerassesment.model.ImageSearchQuery
import com.assessment.mobileengineerassesment.data.UnsplashApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ImageListRepository @Inject constructor(
    private val apiService: UnsplashApiService,
) {
    fun getImageFromRepository(searchData: ImageSearchQuery): Flow<PagingData<ImageResponse>> =
        Pager(
            config = PagingConfig(
                pageSize = GALLERY_PAGE_SIZE,
                initialLoadSize = GALLERY_PAGE_SIZE * 2,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                ImagePagingSource(
                    imageSearchQuery = searchData,
                    apiService = apiService,
                    pageSize = GALLERY_PAGE_SIZE
                )
            }
        ).flow

    companion object {
        const val GALLERY_PAGE_SIZE = 20
    }
}
