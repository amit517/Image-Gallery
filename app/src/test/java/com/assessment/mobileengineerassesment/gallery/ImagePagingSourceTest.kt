package com.assessment.mobileengineerassesment.gallery

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.recyclerview.widget.RecyclerView
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.assessment.mobileengineerassesment.data.UnsplashApiService
import com.assessment.mobileengineerassesment.model.ImageResponse
import com.assessment.mobileengineerassesment.view.gallery.ImageListRepository
import com.assessment.mobileengineerassesment.view.gallery.ImagePagingSource
import com.assessment.mobileengineerassesment.view.gallery.adapters.ImagePageAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@RunWith(AndroidJUnit4::class)
class ImagePagingSourceTest {
    private lateinit var recyclerView: RecyclerView
    private val query = "A"
    private val totalPage = 4

    private val service: UnsplashApiService by lazy {
        Retrofit.Builder()
            .baseUrl("http://localhost:8080")
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(UnsplashApiService::class.java)
    }

    private val mappingCountCallHandler: HashMap<Int, Int> = HashMap<Int, Int>().apply {
        for (i in 0..totalPage) {
            this[i] = 0
        }
    }

    private val adapter: ImagePageAdapter by lazy {
        ImagePageAdapter({ imageResponse, bitmap ->

        })
    }

    private lateinit var pager: Flow<PagingData<ImageResponse>>

    private lateinit var mockWebServer: MockWebServer

    private val context: Context
        get() {
            return InstrumentationRegistry.getInstrumentation().targetContext
        }

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start(8080)

        recyclerView = RecyclerView(context)
        recyclerView.adapter = adapter

        mockWebServer.dispatcher = SearchMoviePagingDispatcher(context, ::receiveCallback)
        pager = Pager(
            config = PagingConfig(
                pageSize = ImageListRepository.GALLERY_PAGE_SIZE,
                initialLoadSize = ImageListRepository.GALLERY_PAGE_SIZE * 2,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                ImagePagingSource(
                    imageSearchQuery = searchData,
                    apiService = apiService,
                    pageSize = ImageListRepository.GALLERY_PAGE_SIZE
                )
            }
        ).flow
    }

    @After
    fun tearDown() {
        mockWebServer.dispatcher.shutdown()
        mockWebServer.shutdown()
    }

    @Test
    fun should_success_get_data_and_not_retrieve_anymore_page_if_not_reached_treshold() {
        runBlocking {
            val job = executeLaunch(this)
            delay(1000)
            // adapter.forcePrefetch(10)
            delay(1000)

            Assert.assertEquals(1, mappingCountCallHandler[1])
            Assert.assertEquals(0, mappingCountCallHandler[2])
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

    private fun receiveCallback(reqPage: Int) {
        val prev = mappingCountCallHandler[reqPage]!!
        mappingCountCallHandler[reqPage] = prev + 1
    }
}
