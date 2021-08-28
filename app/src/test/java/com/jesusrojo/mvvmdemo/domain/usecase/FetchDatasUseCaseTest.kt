package com.jesusrojo.mvvmdemo.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.jesusrojo.mvvmdemo.data.repository.fake.FakeRepository
import com.jesusrojo.mvvmdemo.domain.repository.UiDataRepository
import com.jesusrojo.mvvmdemo.util.Resource
import com.nhaarman.mockitokotlin2.mock

import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test


class FetchDatasUseCaseTest {

    private val repository: UiDataRepository = mock()
    private val sut = FetchDatasUseCase(repository)

    private val page = 1
    private val query = "Kotlin"
    @Test
    fun execute_callRepository() = runBlockingTest { // coroutinesTestRule.testDispatcher.runBlockingTest {

        sut.execute(query)

        verify(repository, times(1)).fetchDatas(query)
    }

    @Test
    fun execute_callRepository_listOK() = runBlockingTest {

            val datas = FakeRepository.getFakeListItemsOneTwo()
            whenever(repository.fetchDatas(query)).thenReturn(Resource.Success(datas))

            val results = sut.execute(query)

            assertThat(results.data).isEqualTo(datas)
            assertThat(results.data?.size).isEqualTo(datas.size)
            assertThat(results.data?.size).isEqualTo(2)
        }
}