package com.assessment.mobileengineerassesment.view.gallery

import `test-common`.FakeUnsplashApiService
import `test-common`.ImageFactory
import androidx.paging.PagingSource
import com.assessment.mobileengineerassesment.model.ImageResponse
import com.assessment.mobileengineerassesment.model.ImageSearchQuery
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class ImagePagingSourceTest {
    private val imageSearchQuery = ImageSearchQuery("latest")
    private val imageFactory = ImageFactory()
    private val fakeImages = listOf(
        imageFactory.createImageResponse(),
        imageFactory.createImageResponse(),
        imageFactory.createImageResponse(),
        imageFactory.createImageResponse(),
        imageFactory.createImageResponse(),
        imageFactory.createImageResponse()
    )
    private val fakeApi = FakeUnsplashApiService().apply {
        fakeImages.forEach { post -> addImage(post) }
    }

    @Test
    fun imagePagingSourceShouldReturnPage() = runTest {
        val pagingSource = ImagePagingSource(imageSearchQuery, fakeApi, 2)
        assertEquals(
            expected = PagingSource.LoadResult.Page(
                data = listOf(fakeImages[0], fakeImages[1]),
                prevKey = null,
                nextKey = 2
            ),
            actual = pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun imagePagingSourceLastElement() = runTest {
        val pagingSource = ImagePagingSource(imageSearchQuery, fakeApi, 2)
        assertEquals(
            expected = PagingSource.LoadResult.Page(
                data = emptyList<ImageResponse>(),
                prevKey = 4,
                nextKey = null
            ),
            actual = pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 5,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }
}
