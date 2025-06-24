package com.koushik.data.di

import com.koushik.data.api.ApiService
import com.koushik.data.repository.NewsRepository
import com.koushik.domain.repository.ItemRepository
import com.koushik.domain.usecase.GetItemsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    // Provides NewsRepository
    @Provides
    @Singleton
    fun provideNewsRepository(apiService: ApiService): NewsRepository {
        return NewsRepository(apiService)
    }

    // Provides GetItemsUseCase
    @Provides
    fun provideGetItemsUseCase(
        repository: ItemRepository
    ): GetItemsUseCase = GetItemsUseCase(repository)
}