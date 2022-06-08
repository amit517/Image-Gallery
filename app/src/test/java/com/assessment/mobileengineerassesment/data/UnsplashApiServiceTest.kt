package com.assessment.mobileengineerassesment.data

import com.assessment.base.network.ResponseAdapterFactory
import com.assessment.base.network.ThrowableAdapter
import com.assessment.base.network.model.BaseResponse
import com.google.common.truth.Truth.assertThat
import com.squareup.moshi.Moshi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class UnsplashApiServiceTest {
    private lateinit var service: UnsplashApiService
    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
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

    private fun enqueueMockResponse(
        fileName: String,
        responseCode: Int = 200,
    ) {
        javaClass.classLoader?.let {
            val inputStream = it.getResourceAsStream(fileName)
            val source = inputStream.source().buffer()
            val mockResponse = MockResponse()
            mockResponse.setResponseCode(responseCode)
            mockResponse.setBody(source.readString(Charsets.UTF_8))
            server.enqueue(mockResponse)
        }
    }

    @Test
    fun getImageCollection_sentRequest_receivedExpected() {
        runBlocking {
            enqueueMockResponse("response.json")
            val responseBody =
                service.getImageCollection(1, 20, "latest")
            val request = server.takeRequest()
            assertThat(responseBody).isNotNull()
            assertThat(request.path).isEqualTo("/photos/?page=1&per_page=20&order_by=latest")
        }
    }

    @Test
    fun getImageCollection_receivedResponse_correctContent() {
        runBlocking {
            enqueueMockResponse("response.json")
            when (val responseBody = service.getImageCollection(1, 20, "latest")) {
                is BaseResponse.Success -> {
                    val photoList = responseBody.body
                    val photo = photoList[0]
                    assertThat(photo.id).isEqualTo("vh3IjuOIwlU")
                    assertThat(photo.color).isEqualTo("#0c2626")
                }
                else -> {}
            }
        }
    }

    @Test
    fun getImageCollection_unauthorized_request_should_gave_auth_error() {
        runBlocking {
            enqueueMockResponse("error.json", 401)
            when (val responseBody = service.getImageCollection(1, 20, "latest")) {
                is BaseResponse.ApiError -> {
                    assertThat(responseBody.code).isEqualTo(401)
                    assertThat(responseBody.errorBody.errors[0]).isEqualTo("OAuth error: The access token is invalid")
                }
                else -> {
                }
            }
        }
    }

    @After
    fun tearDown() {
        server.shutdown()
    }
}
