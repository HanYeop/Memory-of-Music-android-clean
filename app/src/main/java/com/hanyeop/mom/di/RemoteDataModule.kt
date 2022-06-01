package com.hanyeop.mom.di

import com.hanyeop.data.api.MusicApi
import com.hanyeop.mom.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataModule {

    // Retrofit DI
    @Provides
    @Singleton
    fun provideRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()
    }

    // MusicApi DI
    @Provides
    @Singleton
    fun provideMusicApiService(retrofit: Retrofit): MusicApi {
        return retrofit.create(MusicApi::class.java)
    }
}