package com.jesusrojo.mvvmdemo.domain.repository

import com.jesusrojo.mvvmdemo.data.model.UiData
import com.jesusrojo.mvvmdemo.util.Resource

interface UiDataRepository {
    suspend fun fetchDatas(query: String): Resource<List<UiData>>
    suspend fun fetchNextDatas(query: String): Resource<List<UiData>>
    suspend fun refreshDatas(query: String): Resource<List<UiData>>
    suspend fun deleteAll()
    suspend fun deleteAllCache()
}