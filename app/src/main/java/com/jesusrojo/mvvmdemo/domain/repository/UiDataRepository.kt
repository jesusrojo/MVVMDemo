package com.jesusrojo.mvvmdemo.domain.repository

import com.jesusrojo.mvvmdemo.data.model.UiData
import com.jesusrojo.mvvmdemo.util.Resource

interface UiDataRepository {
    suspend fun fetchDatas(page: Int, query: String): Resource<List<UiData>>
    suspend fun fetchNextDatas(page: Int, query: String): Resource<List<UiData>>
    suspend fun deleteAll()
    suspend fun deleteAllCache()
}