package com.jesusrojo.mvvmdemo.presentation.viewmodel


import androidx.lifecycle.Observer
import com.google.common.truth.Truth.assertThat
import com.jesusrojo.mvvmdemo.data.model.UiData
import com.jesusrojo.mvvmdemo.data.repository.fake.FakeRepository
import com.jesusrojo.mvvmdemo.data.repository.fake.FakeUtil
import com.jesusrojo.mvvmdemo.domain.usecase.*
import com.jesusrojo.mvvmdemo.util.Resource
import com.jesusrojo.mvvmdemo.utilunittests.BaseUnitTest
import com.jesusrojo.mvvmdemo.utilunittests.captureValues
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runBlockingTest

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.*
import org.mockito.Mock


class UiDataViewModelTest : BaseUnitTest() {

    private val fakeRepository = FakeRepository()

    private val fetchUseCase = FetchDatasUseCase(fakeRepository)
    private val fetchNextDatasUseCase = FetchNextDatasUseCase(fakeRepository)
    private var refreshDatasUseCase = RefreshDatasUseCase(fakeRepository)
    private val deleteAllUseCase = DeleteAllUseCase(fakeRepository)
    private val deleteAllCacheUseCased = DeleteAllCacheUseCase(fakeRepository)
    private val dispatcher = Dispatchers.Unconfined

    private lateinit var sutFake: UiDataViewModel

//    @Mock lateinit var observer: Observer<Resource<*>>
    private val observer: Observer<Resource<*>> = mock()

    @Before
    fun setUp() {
        sutFake = UiDataViewModel(
            fetchUseCase, fetchNextDatasUseCase, refreshDatasUseCase,
            deleteAllUseCase, deleteAllCacheUseCased, dispatcher
        )

        sutFake.resourceUiDatas.observeForever(observer)
    }

    @After
    fun tearDown() {
        sutFake.resourceUiDatas.removeObserver(observer)
        testCoroutineDispatcher.cleanupTestCoroutines()
    }


    @Test
    fun fetchNextDatas_success() =
        coroutinesTestRule.testDispatcher.runBlockingTest {

            sutFake.fetchNextDatas()

            val resultResource = sutFake.resourceUiDatas

            assertThat(resultResource?.value?.data).isNotNull()
            assertThat(resultResource?.value?.data?.size).isEqualTo(2)

            verify(observer, times(1))
                .onChanged(isA(Resource.Loading::class.java))
            verify(observer, times(1))
                .onChanged(isA(Resource.Success::class.java))
            verify(observer, never()).onChanged(isA(Resource.Error::class.java))

            verifyNoMoreInteractions(observer)
        }


    @Test
    fun fetchDatas_success() =
        coroutinesTestRule.testDispatcher.runBlockingTest {

            sutFake.fetchDatas()

            val state = sutFake.resourceUiDatas

            assertThat(state.value?.data).isEqualTo(FakeUtil.getFakeListItemsOneTwo())

            verify(observer, times(1))
                .onChanged(isA(Resource.Loading::class.java))
            verify(observer, times(1))
                .onChanged(isA(Resource.Success::class.java))
            verify(observer, never()).onChanged(isA(Resource.Error::class.java))

            verifyNoMoreInteractions(observer)
        }


    @Test
    fun fetchDatas_TESTE_WITH_CAPTURE_VALUES() =
        coroutinesTestRule.testDispatcher.runBlockingTest {

            val state = sutFake.resourceUiDatas
            var list = state.captureValues()
            assertThat(list.size).isEqualTo(0)

            sutFake.fetchDatas()

            list = state.captureValues()

            assertThat(list.size).isEqualTo(1)
            assertThat(list[0]).isInstanceOf(Resource.Success::class.java)
        }


    // VARIABLES
    @Test
    fun testDefaultVariables() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            sutFake.fetchDatas()
            assertThat(sutFake.query).isEqualTo("Kotlin")
        }

    @Test
    fun getQuery() {
        assertThat(sutFake.query).isEqualTo("Kotlin")
    }

    @Test
    fun setQuery() {
        sutFake.query = "Java"
        assertThat(sutFake.query).isEqualTo("Java")
    }

    @Test
    fun getResourceUiDatas() {
        val resource = sutFake.resourceUiDatas
        assertThat(resource).isNotNull()
    }
}