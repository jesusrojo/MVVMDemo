package com.jesusrojo.mvvmdemo.presentation.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.jesusrojo.mvvmdemo.data.model.UiData
import com.jesusrojo.mvvmdemo.data.repository.fake.FakeUtil
import com.jesusrojo.mvvmdemo.domain.usecase.*
import com.jesusrojo.mvvmdemo.util.Resource
import com.jesusrojo.mvvmdemo.utilunittests.BaseUnitTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runBlockingTest
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

    @Mock lateinit var observer: Observer<Resource<*>>
    @Mock lateinit var fetchUseCaseMock: FetchDatasUseCase
    @Mock lateinit var fetchNextUseCaseMock: FetchNextDatasUseCase
    @Mock lateinit var refreshDatasUseCase: RefreshDatasUseCase
    @Mock lateinit var deleteAllUseCase: DeleteAllUseCase
    @Mock lateinit var deleteAllCacheUseCaseMock: DeleteAllCacheUseCase
    private val dispatcher = Dispatchers.Unconfined
    private lateinit var sutMock: UiDataViewModel
    private val query = "Kotlin"

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        sutMock = UiDataViewModel(fetchUseCaseMock, fetchNextUseCaseMock,
            refreshDatasUseCase, deleteAllUseCase, deleteAllCacheUseCaseMock, dispatcher)

        sutMock.resourceUiDatas.observeForever(observer)
    }

    @After
    fun tearDown() {
        sutMock.resourceUiDatas.removeObserver(observer)
        testCoroutineDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun fetchDatas_returnSuccess() =
        mainCoroutineRule.runBlockingTest {

        val datas = FakeUtil.getFakeListItemsOneTwo()
        val successResource = Resource.Success(datas)
        given(fetchUseCaseMock.execute(query)).willReturn(successResource)

        sutMock.fetchDatas()

        val actualResource = sutMock.resourceUiDatas.value
        assertThat(actualResource?.data, equalTo(datas))

        verify(observer, times(1)).onChanged(isA(Resource.Loading::class.java))
        verify(observer, times(1)).onChanged(isA(Resource.Success::class.java))
        verify(observer, never()).onChanged(isA(Resource.Error::class.java))

        verifyNoMoreInteractions(observer)
    }


    @Test  fun fetchDatas_returnError() =
        mainCoroutineRule.runBlockingTest {

        val message = "error 676"
        val errorResource = Resource.Error<List<UiData>>(message)
        given(fetchUseCaseMock.execute(query)).willReturn(errorResource)

        sutMock.fetchDatas()

        val actualResource = sutMock.resourceUiDatas.value
        assertThat(actualResource?.message, equalTo(message))
        assertThat(actualResource?.data, equalTo(null))

        verify(observer, times(1))
            .onChanged(isA(Resource.Loading::class.java))
        verify(observer, times(1)).onChanged(isA(Resource.Error::class.java))
        verify(observer, never()).onChanged(isA(Resource.Success::class.java))

        verifyNoMoreInteractions(observer)
    }


    //TEST LIVE DATA
    @Test
    fun testLiveData_success() {
        val mutableLiveData = MutableLiveData<Resource<List<UiData>>>()

        // 1  empty array
        var uiDatas = emptyList<UiData>()
        var testValue = Resource.Success(uiDatas)

        mutableLiveData.value = testValue

        assertThat(mutableLiveData.value, equalTo(testValue))
        assertThat(mutableLiveData.value?.data, equalTo(uiDatas))

        //2  fake array
        uiDatas = FakeUtil.getFakeListItemsOneTwo()
        testValue = Resource.Success(uiDatas)

        mutableLiveData.value = testValue

        assertThat(mutableLiveData.value, equalTo(testValue))
        assertThat(mutableLiveData.value?.data, equalTo(uiDatas))
    }

    @Test
    fun testLiveData_error() {
        val mutableLiveData = MutableLiveData<Resource<List<UiData>>>()
        val ERROR_MSG = "msg error"
        val testValue = Resource.Error<List<UiData>>(ERROR_MSG)

        mutableLiveData.value = testValue

        assertThat(mutableLiveData.value, equalTo(testValue))
        assertThat(mutableLiveData.value?.data, equalTo(null))
        assertThat(mutableLiveData.value?.message, equalTo(ERROR_MSG))
    }

    @Test
    fun testLiveData_loading() {
        val mutableLiveData = MutableLiveData<Resource<List<UiData>>>()
        val testValue = Resource.Loading<List<UiData>>(null)

        mutableLiveData.value = testValue

        assertThat(mutableLiveData.value, equalTo(testValue))
        assertThat(mutableLiveData.value?.data, equalTo(null))
    }
}