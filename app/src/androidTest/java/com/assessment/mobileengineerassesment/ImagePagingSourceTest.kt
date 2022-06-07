package com.assessment.mobileengineerassesment

import android.content.Context
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import co.infinum.retromock.Retromock
import com.assessment.mobileengineerassesment.model.ImageResponse
import com.assessment.mobileengineerassesment.view.gallery.adapters.ImagePageAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class ImagePagingSourceTest {
    private val adapter: ImagePageAdapter by lazy {
        ImagePageAdapter({ imageResponse, bitmap ->

        })
    }

    private lateinit var pager: Flow<PagingData<ImageResponse>>

    private val context: Context
        get() {
            return InstrumentationRegistry.getInstrumentation().targetContext
        }

    @Inject
    fun getRetrofit(retrofit: Retrofit): Retromock? {
        return Retromock.Builder()
            .retrofit(retrofit)
            .build()
    }

    @Before
    fun setup() {

        /*    pager = Pager(
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
            ).flow*/
    }

    @After
    fun tearDown() {

    }

    @Test
    fun should_success_get_data_and_not_retrieve_anymore_page_if_not_reached_threshold() {
        runBlocking {
            val job = executeLaunch(this)
            /*delay(1000)
             adapter.forcePrefetch(10)
            delay(1000)*/

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

    private fun receiveCallback(reqPage: Int) {
        /*  val prev = mappingCountCallHandler[reqPage]!!
          mappingCountCallHandler[reqPage] = prev + 1*/
    }
}
