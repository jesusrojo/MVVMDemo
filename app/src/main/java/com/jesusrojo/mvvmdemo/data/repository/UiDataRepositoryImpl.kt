package com.jesusrojo.mvvmdemo.data.repository

import com.jesusrojo.mvvmdemo.data.api.RawDataApiService.Companion.IN_QUALIFIER
import com.jesusrojo.mvvmdemo.data.model.MapperRawToUiData
import com.jesusrojo.mvvmdemo.data.model.RawDataResponse
import com.jesusrojo.mvvmdemo.data.model.UiData
import com.jesusrojo.mvvmdemo.data.repository.datasource.UiDataCacheDataSource
import com.jesusrojo.mvvmdemo.data.repository.datasource.UiDataLocalDataSource
import com.jesusrojo.mvvmdemo.data.repository.datasource.UiDataRemoteDataSource
import com.jesusrojo.mvvmdemo.domain.repository.UiDataRepository
import com.jesusrojo.mvvmdemo.util.DebugHelp
import com.jesusrojo.mvvmdemo.util.Resource
import retrofit2.Response
import javax.inject.Inject

class UiDataRepositoryImpl @Inject constructor(
    private val cacheDataSource: UiDataCacheDataSource,
    private val localDataSource: UiDataLocalDataSource,
    private val remoteDataSource: UiDataRemoteDataSource,
    private val mapperRawToUiData: MapperRawToUiData
) : UiDataRepository {

    private var pageCount = 1


    override suspend fun fetchDatas(page: Int, query: String): Resource<List<UiData>> {
        DebugHelp.l("fetchDatas $page $query")
        return fetchFromCache(page, query)
    }

    override suspend fun fetchNextDatas(page: Int, query: String): Resource<List<UiData>> {
        DebugHelp.l("fetchNextDatas $page $query")
        return fetchFromAPI(page, query)
    }

    private suspend fun fetchFromCache(page: Int, query: String): Resource<List<UiData>> {
        DebugHelp.l("fetchFromCache")
        var results: List<UiData>? = null
        try {
            results = cacheDataSource.fetchAllFromCache()
        } catch (exception: Exception) {
            DebugHelp.le(exception.message.toString())
        }
        return if (isNotNullIsNotEmpty(results)) {
            Resource.Success(results!!)
        } else {
            fetchFromDB(page, query)
        }
    }

    private suspend fun fetchFromDB(page: Int, query: String): Resource<List<UiData>> {
        DebugHelp.l("fetchFromDB")
        var results: List<UiData>? = null
        try {
            results = localDataSource.fetchAllInDB()
        } catch (exception: Exception) {
            DebugHelp.l(exception.message.toString())
        }

        return if (isNotNullIsNotEmpty(results)) {
            cacheDataSource.saveAllToCache(results!!)
            Resource.Success(results)
        } else {
            fetchFromAPI(page, query)
        }
    }

    private suspend fun fetchFromAPI(page: Int, query: String): Resource<List<UiData>> {
        DebugHelp.l("fetchFromAPI")
        return try {
            pageCount++
            DebugHelp.l("fetchFromAPI $pageCount")

            val apiQuery = "$query$IN_QUALIFIER"
            val response = remoteDataSource.fetchAllApi(pageCount, apiQuery)
            handleResponse(response)
        } catch (exception: Exception) {
            val message = exception.message.toString()
            DebugHelp.l(message)
            Resource.Error(message)
        }
    }

    private suspend fun handleResponse(response: Response<RawDataResponse>): Resource<List<UiData>> {
        DebugHelp.l("handleResponse")
        return if (response.isSuccessful) {
            val body = response.body()
            val rawResults = body?.rawDatas
            if (rawResults != null && rawResults.isNotEmpty()) {
                val uiDatas = mapperRawToUiData(rawResults)
                localDataSource.deleteAllInDB()
                localDataSource.saveAllToDB(uiDatas)
                Resource.Success(uiDatas)
            } else {
                pageCount--
                if(pageCount == 0) pageCount =1
                Resource.Error("Error: rawResults null")
            }
        } else {
            val message = "Response not successful: ${response.errorBody().toString()}" +
                    "\nCODE: ${response.code()}\nMESSAGE: ${response.message()}"
            DebugHelp.l(message)
            Resource.Error(message)
        }
    }

    override suspend fun deleteAll() {
        DebugHelp.l("deleteAll")
        cacheDataSource.deleteAllInCache()
        localDataSource.deleteAllInDB()
    }

    override suspend fun deleteAllCache() {
        DebugHelp.l("deleteAllCache")
        cacheDataSource.deleteAllInCache()
    }

    private fun isNotNullIsNotEmpty(datas: List<UiData>?) =
        datas != null && datas.isNotEmpty()


//    override suspend fun fetchUsersFlow(): Flow<Result<List<UiData>>> =
//        remoteDataSource.fetchUsersFlow().map {
//            if (it.isSuccess) {
//                val datas = this.mapper(it.getOrNull()!!)
//                Result.success(datas)
//            } else
//                Result.failure(it.exceptionOrNull()!!)
//        }
}