package com.jesusrojo.mvvmdemo.presentation.di.hilt

import com.jesusrojo.mvvmdemo.domain.usecase.*
import com.jesusrojo.mvvmdemo.presentation.viewmodel.UiDataViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ViewModelFactoryModule {

    @Singleton
    @Provides
    fun provideMyViewModelFactory(
        fetchDatasUseCase: FetchDatasUseCase,
        fetchNextDatasUseCase: FetchNextDatasUseCase,
        refreshDatasUseCase: RefreshDatasUseCase,
        deleteAllUseCase: DeleteAllUseCase,
        deleteAllCacheUseCase: DeleteAllCacheUseCase,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): UiDataViewModelFactory {
        return UiDataViewModelFactory(
            fetchDatasUseCase,
            fetchNextDatasUseCase,
            refreshDatasUseCase,
            deleteAllUseCase,
            deleteAllCacheUseCase,
            ioDispatcher
        )
    }
}