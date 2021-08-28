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

    private fun increasePageCount() { pageCount++ }

    private fun decreasePageCount() {
        pageCount--
        if (pageCount <= 0) pageCount = 1
    }
    private fun resetPageCount() { pageCount = 1  }


    override suspend fun fetchDatas(query: String): Resource<List<UiData>> {
        DebugHelp.l("fetchDatas, query $query, pageCount $pageCount")
        return fetchFromCache(query)
    }

    override suspend fun fetchNextDatas(query: String): Resource<List<UiData>> {
        DebugHelp.l("fetchNextDatas, query $query")
        increasePageCount()
        return fetchFromAPI(query)
    }

    override suspend fun refreshDatas(query: String): Resource<List<UiData>> {
        DebugHelp.l("refreshDatas, query $query")
        deleteAll()
        resetPageCount()
        return fetchFromAPI(query)
    }

    private suspend fun fetchFromCache(query: String): Resource<List<UiData>> {
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
            fetchFromDB(query)
        }
    }

    private suspend fun fetchFromDB(query: String): Resource<List<UiData>> {
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
            fetchFromAPI(query)
        }
    }

    private suspend fun fetchFromAPI(query: String): Resource<List<UiData>> {
        return try {
            val apiQuery = "$query$IN_QUALIFIER" // _UP
            DebugHelp.l("fetchFromAPI $pageCount, apiQuery $apiQuery")
            val response = remoteDataSource.fetchAllApi(pageCount, apiQuery)
            handleResponse(response)
        } catch (exception: Exception) {
            val message = exception.message.toString()
            decreasePageCount()
            DebugHelp.l(message)
            Resource.Error(message)
        }
    }

    private suspend fun handleResponse(
        response: Response<RawDataResponse>): Resource<List<UiData>> {
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
                decreasePageCount()
                Resource.Error("Error response: rawResults null or empty")
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
        resetPageCount()
        cacheDataSource.deleteAllInCache()
        localDataSource.deleteAllInDB()
    }

    override suspend fun deleteAllCache() {
        DebugHelp.l("deleteAllCache")
        resetPageCount()
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