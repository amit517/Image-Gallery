package `test-common`

import com.assessment.base.network.model.GenericResponse
import com.assessment.mobileengineerassesment.data.UnsplashApiService
import com.assessment.mobileengineerassesment.model.ImageResponse
import retrofit2.http.Query
import java.io.IOException


class FakeUnsplashApiService : UnsplashApiService {
    var failureMsg: String? = null

    override suspend fun getImageCollection(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("order_by")orderBy: String?,
    ): GenericResponse<List<ImageResponse>> {
        failureMsg?.let {
            throw IOException(it)
        }


        TODO("Not yet implemented")
    }
}
