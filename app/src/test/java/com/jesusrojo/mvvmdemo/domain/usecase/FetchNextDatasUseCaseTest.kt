package com.jesusrojo.mvvmdemo.domain.usecase

import com.google.common.truth.Truth
import com.jesusrojo.mvvmdemo.data.repository.datasource.FakeRepository
import com.jesusrojo.mvvmdemo.domain.repository.UiDataRepository
import com.jesusrojo.mvvmdemo.util.Resource
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runBlockingTest

import org.junit.Test

class FetchNextDatasUseCaseTest {

    private val repository: UiDataRepository = mock()
    private val sut = FetchNextDatasUseCase(repository)

    private val page = 1
    private val query = "Kotlin"
    @Test
    fun execute_callRepository() = runBlockingTest {

        sut.execute(page, query)

        verify(repository, times(1)).fetchNextDatas(page, query)
    }

    @Test
    fun execute_callRepository_listOK() = runBlockingTest {

        val datas = FakeRepository.getFakeListItemsOneTwo()
        whenever(repository.fetchNextDatas(page, query)).thenReturn(Resource.Success(datas))

        val results = sut.execute(page, query)

        Truth.assertThat(results.data).isEqualTo(datas)
        Truth.assertThat(results.data?.size).isEqualTo(datas.size)
        Truth.assertThat(results.data?.size).isEqualTo(2)
    }
}