package com.jesusrojo.mvvmdemo.presentation.di.hilt

import com.jesusrojo.mvvmdemo.domain.repository.UiDataRepository
import com.jesusrojo.mvvmdemo.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun provideFetchDatasUseCase(
        repository: UiDataRepository
    ): FetchDatasUseCase {
        return FetchDatasUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideFetchNextDatasUseCase(
        repository: UiDataRepository
    ): FetchNextDatasUseCase {
        return FetchNextDatasUseCase(repository)
    }


    @Singleton
    @Provides
    fun provideRefreshDatasUseCase(
        repository: UiDataRepository
    ): RefreshDatasUseCase {
        return RefreshDatasUseCase(repository)
    }


    @Singleton
    @Provides
    fun provideDeleteAllUseCase(
        repository: UiDataRepository
    ): DeleteAllUseCase {
        return DeleteAllUseCase(repository)
    }


    @Singleton
    @Provides
    fun provideDeleteAllCacheUseCase(
        repository: UiDataRepository
    ): DeleteAllCacheUseCase {
        return DeleteAllCacheUseCase(repository)
    }
}