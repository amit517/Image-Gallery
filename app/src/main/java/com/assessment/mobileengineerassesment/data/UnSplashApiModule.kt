package com.assessment.mobileengineerassesment.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UnSplashApiModule{
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(UnsplashApiService::class.java)
}
