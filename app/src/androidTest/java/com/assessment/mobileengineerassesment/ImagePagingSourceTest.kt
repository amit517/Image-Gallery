package com.assessment.mobileengineerassesment

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.recyclerview.widget.RecyclerView
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.assessment.base.network.ResponseAdapterFactory
import com.assessment.base.network.ThrowableAdapter
import com.assessment.mobileengineerassesment.data.UnsplashApiService
import com.assessment.mobileengineerassesment.model.ImageResponse
import com.assessment.mobileengineerassesment.model.ImageSearchQuery
import com.assessment.mobileengineerassesment.view.gallery.ImageListRepository
import com.assessment.mobileengineerassesment.view.gallery.ImagePagingSource
import com.assessment.mobileengineerassesment.view.gallery.adapters.ImagePageAdapter
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import mockwebserver3.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


@RunWith(AndroidJUnit4::class)
@SmallTest
class ImagePagingSourceTest {
    private lateinit var recyclerView: RecyclerView

    private val adapter: ImagePageAdapter by lazy {
        ImagePageAdapter({ imageResponse, bitmap ->

        })
    }
    private lateinit var service: UnsplashApiService
    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        recyclerView = RecyclerView(context)
        recyclerView.adapter = adapter


        val moshi = Moshi.Builder()
            .add(ThrowableAdapter())
            .build()
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))//We will use MockWebServers url
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(ResponseAdapterFactory())
            .build()
            .create(UnsplashApiService::class.java)
    }

    private lateinit var pager: Flow<PagingData<ImageResponse>>

    private val context: Context
        get() {
            return InstrumentationRegistry.getInstrumentation().targetContext
        }

    @Before
    fun setup() {
        pager = Pager(
            config = PagingConfig(
                pageSize = 20,
                initialLoadSize = 1,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                ImagePagingSource(
                    imageSearchQuery = ImageSearchQuery("latest"),
                    apiService = service,
                    pageSize = ImageListRepository.GALLERY_PAGE_SIZE
                )
            }
        ).flow
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun should_success_get_data_and_not_retrieve_anymore_page_if_not_reached_threshold() {
        runBlocking {
            val job = executeLaunch(this)
            delay(1000)

            /*Assert.assertEquals(1, mappingCountCallHandler[1])
            Assert.assertEquals(0, mappingCountCallHandler[2])*/
            Assert.assertEquals(20, adapter.itemCount)
            job.cancel()
        }
    }

    private fun executeLaunch(coroutineScope: CoroutineScope) = coroutineScope.launch {
        val res = pager.cachedIn(this)
        res.collectLatest {
            adapter.submitData(it)
        }
    }
}
