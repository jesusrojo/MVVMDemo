package com.jesusrojo.mvvmdemo.data.repository.datasource

import com.jesusrojo.mvvmdemo.data.model.RawDataResponse
import retrofit2.Response

interface UiDataRemoteDataSource {
    suspend fun fetchAllApi(page: Int, apiQuery: String): Response<RawDataResponse>
}