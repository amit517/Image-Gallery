package com.assessment.mobileengineerassesment

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.assessment.mobileengineerassesment.mocprovider.FakeUnsplashApiService
import com.assessment.mobileengineerassesment.mocprovider.ImageFactory
import com.assessment.mobileengineerassesment.model.ImageResponse
import com.assessment.mobileengineerassesment.model.ImageSearchQuery
import com.assessment.mobileengineerassesment.view.gallery.ImagePagingSource
import com.assessment.mobileengineerassesment.view.gallery.adapters.ImagePageAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@SmallTest
class ImagePagingSourceTest {
    private val imageFactory = ImageFactory()
    private lateinit var recyclerView: RecyclerView
    private lateinit var pager: Flow<PagingData<ImageResponse>>

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

    private val context: Context
        get() {
            return InstrumentationRegistry.getInstrumentation().targetContext
        }
    private val adapter: ImagePageAdapter by lazy {
        ImagePageAdapter { _, _ ->
        }
    }

    @Before
    fun setup() {
        recyclerView = RecyclerView(context)
        recyclerView.adapter = adapter

        pager = Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                initialLoadSize = PAGE_SIZE * 2,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                ImagePagingSource(
                    imageSearchQuery = ImageSearchQuery("latest"),
                    apiService = fakeApi,
                    pageSize = PAGE_SIZE
                )
            }
        ).flow
    }

    @Test
    fun should_success_get_data_and_not_retrieve_anymore_page_if_not_reached_threshold() {
        runBlocking {
            val job = executeLaunch(this)
            delay(2000)
            Assert.assertEquals(PAGE_SIZE * 2, adapter.snapshot().size)
            Assert.assertEquals(fakeImages[0], adapter.snapshot()[0])
            job.cancel()
        }
    }

    private fun executeLaunch(coroutineScope: CoroutineScope) = coroutineScope.launch {
        pager.collectLatest {
            adapter.submitData(it)
            adapter.notifyDataSetChanged()
        }
    }

    companion object{
        const val PAGE_SIZE = 2
    }
}
