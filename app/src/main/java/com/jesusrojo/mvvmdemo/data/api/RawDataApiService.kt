package com.jesusrojo.mvvmdemo.data.api

import com.jesusrojo.mvvmdemo.data.model.RawDataResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RawDataApiService {

    companion object {
        const val BASE_URL = "https://api.github.com/"
        const val IN_QUALIFIER = "in:name,description"
    }

    //https://api.github.com/search/repositories?sort=stars&q=Androidin:name,description&page=0&per_page=10

    @GET("search/repositories?sort=stars")
    suspend fun fetchRawDatas(
        @Query("page") page: Int,
        @Query("q") apiQuery: String,
      //  @Query("per_page") itemsPerPage: Int
    ): Response<RawDataResponse>

//////https://api.github.com/search/repositories?q=language:kotlin&sort=stars&page=1
//
//    @GET("search/repositories?sort=stars&q=language:kotlin")
//    suspend fun fetchDatas(
//        @Query("page") page: Int,
//     //   @Query("language") language: String
//    ): Response<RawDataResponse>
//
///////
}