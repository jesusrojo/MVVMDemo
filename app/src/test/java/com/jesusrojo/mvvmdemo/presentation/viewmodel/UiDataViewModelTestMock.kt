package com.jesusrojo.mvvmdemo.presentation.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.jesusrojo.mvvmdemo.data.model.UiData
import com.jesusrojo.mvvmdemo.data.repository.datasource.FakeRepository
import com.jesusrojo.mvvmdemo.domain.usecase.DeleteAllCacheUseCase
import com.jesusrojo.mvvmdemo.domain.usecase.DeleteAllUseCase
import com.jesusrojo.mvvmdemo.domain.usecase.FetchDatasUseCase
import com.jesusrojo.mvvmdemo.domain.usecase.FetchNextDatasUseCase
import com.jesusrojo.mvvmdemo.util.Resource
import com.jesusrojo.mvvmdemo.utilunittests.BaseUnitTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.*
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class UiDataViewModelTestMock: BaseUnitTest() {

    private val query = "Kotlin"
    private val page = 1

    // This rule allows us to run LiveData synchronously
//    @get:Rule
//    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @Mock
    lateinit var observer: Observer<Resource<*>>

    @Mock lateinit var fetchUseCaseMock: FetchDatasUseCase
    @Mock lateinit var fetchNextUseCaseMock: FetchNextDatasUseCase
    @Mock lateinit var deleteAllUseCase: DeleteAllUseCase
    @Mock lateinit var deleteAllCacheUseCaseMock: DeleteAllCacheUseCase
    private val ioDispatcher = Dispatchers.IO

    private lateinit var sutMock: UiDataViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        sutMock = UiDataViewModel(fetchUseCaseMock, fetchNextUseCaseMock, deleteAllUseCase, deleteAllCacheUseCaseMock, ioDispatcher)

        // Observe the LiveData forever
        sutMock.resourceUiDatas.observeForever(observer)

        // Sets the given [dispatcher] as an underlying dispatcher of [Dispatchers.Main]
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    @After
    fun tearDown() {
        // Whatever happens, remove the observer!
        sutMock.resourceUiDatas.removeObserver(observer)

        // Resets state of the [Dispatchers.Main] to the original main dispatcher
        Dispatchers.resetMain()

        // Clean up the TestCoroutineDispatcher to make sure no other work is running
        testCoroutineDispatcher.cleanupTestCoroutines()
    }


    @Test
    fun `test live data_success`() {
        // Given
        val mutableLiveData = MutableLiveData<Resource<List<UiData>>>()

        // When
        mutableLiveData.value = Resource.Success(emptyList())

        // Then
        assertThat(mutableLiveData.value?.data, equalTo(emptyList()))
    }

    @Test
    fun `test live data_loading`() {
        // Given
        val mutableLiveData = MutableLiveData<Resource<List<UiData>>>()

        // When
        mutableLiveData.value = Resource.Loading(null)

        // Then
        assertThat(mutableLiveData.value?.data, equalTo(null))
    }

    @Test
    fun `test live data_error`() {
        // Given
        val errorMessage = "Error testing"
        val mutableLiveData = MutableLiveData<Resource<List<UiData>>>()

        // When
        mutableLiveData.value = Resource.Error(errorMessage)

        // Then
        assertThat(mutableLiveData.value?.data, equalTo(null))
        assertThat(mutableLiveData.value?.message, equalTo(errorMessage))
    }

    //todo fail Example of correct verification:
    //    verify(mock).doSomething()
    @Test
    fun `fetch datas return success with expected data`() = runBlockingTest {
        // Given
        val expectedDatas = Resource.Success(FakeRepository.getFakeListItemsOneTwo())
        given(fetchUseCaseMock.execute(page,query)).willReturn(expectedDatas)

        // When
        sutMock.fetchDatas()
        val actualResource = sutMock.resourceUiDatas.value

        // Then
        verify(observer, times(1))
            .onChanged(isA(Resource.Loading::class.java)) // FAIL SOMETIMES But was 0 times.
        verify(observer, times(1)).onChanged(isA(Resource.Success::class.java))
        verify(observer, never()).onChanged(isA(Resource.Error::class.java))

        verifyNoMoreInteractions(observer)
    }


    @Test  fun `fetch datas return success with expected data FAIL`() = runBlockingTest {

        val expectedDatas = Resource.Success(FakeRepository.getFakeListItemsOneTwo())
        given(fetchUseCaseMock.execute(page, query)).willReturn(expectedDatas)

        val actualResource = sutMock.resourceUiDatas
        sutMock.fetchDatas()

        assertThat(actualResource.value?.data, equalTo(null))
    }


    @Test
    fun `fetch datas should return exception`() = runBlockingTest {
        // Given
        given(fetchUseCaseMock.execute(page, query)).willThrow(RuntimeException("Exception"))
        ////  `when`(fetchUseCaseMock.execute(page)).thenThrow(RuntimeException("Exception"))

        // When
        sutMock.fetchDatas()
        val actualError = sutMock.resourceUiDatas.value

        // Then
        verify(observer, times(1)).onChanged(isA(Resource.Loading::class.java))
        verify(observer, never()).onChanged(isA(Resource.Success::class.java))
        verifyNoMoreInteractions(observer)
    }
}