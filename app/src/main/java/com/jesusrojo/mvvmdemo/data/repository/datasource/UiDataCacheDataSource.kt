package com.jesusrojo.mvvmdemo.data.repository.datasource

import com.jesusrojo.mvvmdemo.data.model.UiData


interface UiDataCacheDataSource {
    suspend fun fetchAllFromCache():List<UiData>
    suspend fun saveAllToCache(datas: List<UiData>)
    suspend fun deleteAllInCache()
}