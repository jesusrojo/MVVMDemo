package com.jesusrojo.mvvmdemo.data.repository.datasourceimpl


import com.jesusrojo.mvvmdemo.data.api.RawDataApiService
import com.jesusrojo.mvvmdemo.data.model.RawDataResponse
import com.jesusrojo.mvvmdemo.data.repository.datasource.UiDataRemoteDataSource
import com.jesusrojo.mvvmdemo.util.DebugHelp

import retrofit2.Response
import javax.inject.Inject


class UiDataRemoteDataSourceImpl @Inject constructor(
        private val service: RawDataApiService
): UiDataRemoteDataSource {

    override suspend fun fetchAllApi(page: Int, apiQuery: String): Response<RawDataResponse> {
        DebugHelp.l("fetchAllApi PAGE $page APIQUERY: $apiQuery")
        return service.fetchRawDatas(page, apiQuery) //todo change order paramenters
    }
}