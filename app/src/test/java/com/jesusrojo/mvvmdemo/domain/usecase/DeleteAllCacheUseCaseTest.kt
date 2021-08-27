package com.jesusrojo.mvvmdemo.domain.usecase

import com.jesusrojo.mvvmdemo.domain.repository.UiDataRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class DeleteAllCacheUseCaseTest{

    private val repository: UiDataRepository = mock()
    private val sut = DeleteAllCacheUseCase(repository)

    @Test
    fun execute_callRepository() = runBlockingTest {

        sut.execute()

        verify(repository, times(1)).deleteAllCache()
    }
}