package com.assessment.mobileengineerassesment.network

import android.content.Context
import com.assessment.base.BuildConfig
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitApiModule {
    @Singleton
    @Provides
    fun provideCache(@ApplicationContext context: Context): Cache {
        val httpCacheDirectory = File(context.cacheDir, "offlineCache")
        return Cache(httpCacheDirectory, 10 * 1024 * 1024)
    }

    @Singleton
    @Provides
    fun provideLoggerInterceptor() = if (BuildConfig.DEBUG) {
        val logger = HttpLoggingInterceptor()
        logger.setLevel(HttpLoggingInterceptor.Level.BODY)
    } else {
        val logger = HttpLoggingInterceptor()
        logger.setLevel(HttpLoggingInterceptor.Level.NONE)
    }

    @Singleton
    @Provides
    fun provideUserInterceptor() = UserInterceptor()

    @Singleton
    @Provides
    fun provideThrowableAdapter() = ThrowableAdapter()

    @Singleton
    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        userInterceptor: UserInterceptor,
        cache: Cache,
        @Named("CacheInterceptor") cacheInterceptor: Interceptor,
        @Named("OfflineCacheInterceptor") offlineCacheInterceptor: Interceptor,
    ) = OkHttpClient.Builder()
        .cache(cache)
        .addNetworkInterceptor(loggingInterceptor)
        .addInterceptor(userInterceptor)
        .addNetworkInterceptor(cacheInterceptor)
        .addInterceptor(offlineCacheInterceptor)
        .connectTimeout(2, TimeUnit.MINUTES)
        .readTimeout(1, TimeUnit.MINUTES)
        .writeTimeout(1, TimeUnit.MINUTES)
        .build()

    @Singleton
    @Provides
    fun provideMoshi(throwableAdapter: ThrowableAdapter) = Moshi.Builder()
        .add(throwableAdapter)
        .build()

    @Singleton
    @Provides
    fun createRetrofit(client: OkHttpClient, moshi: Moshi) = Retrofit.Builder()
        .client(client)
        .baseUrl(BuildConfig.BASE_URL)
        .addCallAdapterFactory(ResponseAdapterFactory())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(UnsplashApiService::class.java)

    @Provides
    @Singleton
    @Named("CacheInterceptor")
    fun provideCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request: Request = chain.request()
            val originalResponse: Response = chain.proceed(request)
            val cacheControl: String? = originalResponse.header("Cache-Control")
            if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
                cacheControl.contains("must-revalidate") || cacheControl.contains("max-stale=0")
            ) {
                val cc = CacheControl.Builder()
                    .maxStale(1, TimeUnit.DAYS)
                    .build()
                request = request.newBuilder()
                    .removeHeader("Pragma")
                    .cacheControl(cc)
                    .build()
                chain.proceed(request)
            } else {
                originalResponse

            }
        }
    }

    @Provides
    @Singleton
    @Named("OfflineCacheInterceptor")
    fun provideOfflineCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            try {
                return@Interceptor chain.proceed(chain.request())
            } catch (e: Exception) {
                val cacheControl = CacheControl.Builder()
                    .onlyIfCached()
                    .maxStale(1, TimeUnit.DAYS)
                    .build()
                val offlineRequest: Request = chain.request().newBuilder()
                    .cacheControl(cacheControl)
                    .removeHeader("Pragma")
                    .build()
                return@Interceptor chain.proceed(offlineRequest)
            }
        }
    }
}

