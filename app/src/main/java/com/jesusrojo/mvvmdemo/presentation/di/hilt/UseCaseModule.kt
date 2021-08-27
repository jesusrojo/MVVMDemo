package com.jesusrojo.mvvmdemo.presentation.di.hilt

import com.jesusrojo.mvvmdemo.domain.repository.UiDataRepository
import com.jesusrojo.mvvmdemo.domain.usecase.DeleteAllCacheUseCase
import com.jesusrojo.mvvmdemo.domain.usecase.DeleteAllUseCase
import com.jesusrojo.mvvmdemo.domain.usecase.FetchDatasUseCase
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
    fun provideFetchAllUseCase(
        repository: UiDataRepository
    ): FetchDatasUseCase {
        return FetchDatasUseCase(repository)
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