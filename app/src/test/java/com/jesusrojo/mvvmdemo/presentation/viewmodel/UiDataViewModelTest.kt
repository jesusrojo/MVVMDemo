package com.jesusrojo.mvvmdemo.presentation.viewmodel


import androidx.lifecycle.Observer
import com.google.common.truth.Truth.assertThat
import com.jesusrojo.mvvmdemo.data.model.UiData
import com.jesusrojo.mvvmdemo.data.repository.fake.FakeRepository
import com.jesusrojo.mvvmdemo.domain.usecase.DeleteAllCacheUseCase
import com.jesusrojo.mvvmdemo.domain.usecase.DeleteAllUseCase
import com.jesusrojo.mvvmdemo.domain.usecase.FetchDatasUseCase
import com.jesusrojo.mvvmdemo.domain.usecase.FetchNextDatasUseCase
import com.jesusrojo.mvvmdemo.util.Resource
import com.jesusrojo.mvvmdemo.utilunittests.BaseUnitTest
import com.jesusrojo.mvvmdemo.utilunittests.captureValues
import com.jesusrojo.mvvmdemo.utilunittests.getOrAwaitValue
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test


class UiDataViewModelTest: BaseUnitTest() {

    private val fakeRepository = FakeRepository()

    private val fetchUseCase = FetchDatasUseCase(fakeRepository)
    private val fetchNextDatasUseCase = FetchNextDatasUseCase(fakeRepository)
    private val deleteAllUseCase = DeleteAllUseCase(fakeRepository)
    private val deleteAllCacheUseCased = DeleteAllCacheUseCase(fakeRepository)
    private val ioDispatcher = Dispatchers.IO

    private lateinit var sutFake: UiDataViewModel

    private val observer: Observer<Resource<List<UiData>>> = mock()

    @Before
    fun setUp(){

        sutFake = UiDataViewModel(fetchUseCase, fetchNextDatasUseCase,
            deleteAllUseCase, deleteAllCacheUseCased, ioDispatcher)
    }

    @Test
    fun fail_fetchNextDatas_loadingShow_data() =
        coroutinesTestRule.testDispatcher.runBlockingTest {

            val state = sutFake.resourceUiDatas
            state.observeForever(observer)

            sutFake.fetchNextDatas()

            val resultResource = state.getOrAwaitValue()
            assertThat(resultResource.data).isNotNull()
            assertThat(resultResource.data?.size).isEqualTo(2)
        }


    @Test
    fun fail_fetchDatas_loadingShow_data() =
        coroutinesTestRule.testDispatcher.runBlockingTest {

            val state = sutFake.resourceUiDatas
            state.observeForever(observer)

            sutFake.fetchDatas()

            var resultResource = state.getOrAwaitValue()
          //  assertThat(resultResource.data).isEqualTo(null)// todo FAIL

            resultResource = state.getOrAwaitValue()
            assertThat(resultResource.data).isNotNull()
            assertThat(resultResource.data?.size).isEqualTo(2)
        }

    @Test
    fun fetchDatas_withCAPTURE_VALUES() =
        coroutinesTestRule.testDispatcher.runBlockingTest {

            val state = sutFake.resourceUiDatas
            var list = state.captureValues()
            assertThat(list.size).isEqualTo(0)

            sutFake.fetchDatas()

            list = state.captureValues()

            assertThat(list.size).isEqualTo(1)
            assertThat(list[0]).isInstanceOf(Resource.Success::class.java)
        }

    @Test
    fun fetchDatas_loadExpectedDatas() =
        coroutinesTestRule.testDispatcher.runBlockingTest {

            sutFake.fetchDatas()

            val state = sutFake.resourceUiDatas

            assertThat(state.value?.data).isNull()
        }


    @Test
    fun testDefaultVariables() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val state = sutFake.resourceUiDatas
            state.observeForever(observer)
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