package com.jesusrojo.mvvmdemo.data.api


import com.google.common.truth.Truth.assertThat
import com.jesusrojo.mvvmdemo.data.model.RawDataResponse
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UsersAPIServiceTest {

    private lateinit var service: RawDataApiService
    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RawDataApiService::class.java)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    private fun enqueueMockResponse(fileName:String){
      val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
      val source = inputStream.source().buffer()
      val mockResponse = MockResponse()
      mockResponse.setBody(source.readString(Charsets.UTF_8))
      server.enqueue(mockResponse)
    }

    private suspend fun prepareAPIResponse(): RawDataResponse {
        enqueueMockResponse("api_response.json")
        val responseBody = service.fetchRawDatas(1, "kotlin").body()
        return responseBody!!
    }

    @Test
    fun fetchDatas_sentRequest_receivedExpected(){
        runBlocking {
            val responseBody = prepareAPIResponse()
            val request = server.takeRequest()
            assertThat(responseBody).isNotNull()
            assertThat(request.path)
                .isEqualTo("/search/repositories?sort=stars&page=1&q=kotlin")
        }
    }

    @Test
    fun fetchDatas_receivedResponse_correctPageSize(){
      runBlocking {
          val responseBody = prepareAPIResponse()

          assertThat(responseBody.rawDatas?.size).isEqualTo(30)
      }
    }

    @Test
    fun fetchDatas_receivedResponse_correctContent(){
        runBlocking {
            val responseBody = prepareAPIResponse()
          
            val data = responseBody.rawDatas?.get(0)

            assertThat(data?.id).isEqualTo(51148780)
            assertThat(data?.name).isEqualTo("architecture-samples")
            assertThat(data?.description).isEqualTo("A collection of samples to discuss and showcase different architectural tools and patterns for Android apps.")
            assertThat(data?.stargazersCount).isEqualTo(39081)
            assertThat(data?.forksCount).isEqualTo(10747)
        }
    }
}