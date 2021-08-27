package com.jesusrojo.mvvmdemo.presentation.di.hilt


import com.jesusrojo.mvvmdemo.data.api.RawDataApiService
import com.jesusrojo.mvvmdemo.data.db.UiDataDAO
import com.jesusrojo.mvvmdemo.data.model.MapperRawToUiData
import com.jesusrojo.mvvmdemo.data.repository.UiDataRepositoryImpl

import com.jesusrojo.mvvmdemo.data.repository.datasource.UiDataCacheDataSource
import com.jesusrojo.mvvmdemo.data.repository.datasource.UiDataLocalDataSource
import com.jesusrojo.mvvmdemo.data.repository.datasource.UiDataRemoteDataSource
import com.jesusrojo.mvvmdemo.data.repository.datasourceimpl.UiDataCacheDataSourceImpl
import com.jesusrojo.mvvmdemo.data.repository.datasourceimpl.UiDataRemoteDataSourceImpl
import com.jesusrojo.mvvmdemo.data.repository.datasourceimpl.UiDataLocalDataSourceImpl
import com.jesusrojo.mvvmdemo.domain.repository.UiDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideUsersRepository(
        cacheDataSource: UiDataCacheDataSource,
        localDataSource: UiDataLocalDataSource,
        remoteDataSource: UiDataRemoteDataSource,
        mapper: MapperRawToUiData
    ): UiDataRepository {
        return UiDataRepositoryImpl(
           cacheDataSource,localDataSource, remoteDataSource, mapper
        )
    }


    @Singleton
    @Provides
    fun provideRemoteDataSource(
        newsAPIService: RawDataApiService
    ): UiDataRemoteDataSource {
        return UiDataRemoteDataSourceImpl(newsAPIService)
    }

    @Singleton
    @Provides
    fun provideLocalDataSource(myDAO: UiDataDAO): UiDataLocalDataSource {
        return UiDataLocalDataSourceImpl(myDAO)
    }

    @Singleton
    @Provides
    fun provideCacheDataSource(): UiDataCacheDataSource {
        return UiDataCacheDataSourceImpl()
    }

    @Singleton
    @Provides
    fun provideMapperRawToEnty(): MapperRawToUiData {
        return MapperRawToUiData()
    }
}