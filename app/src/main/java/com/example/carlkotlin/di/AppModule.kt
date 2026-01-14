package com.example.carlkotlin.di

import com.example.carlkotlin.data.repository.MockPostRepositoryImpl
import com.example.carlkotlin.domain.repository.PostRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePostRepository(): PostRepository {
        return MockPostRepositoryImpl
    }
}
