package com.jesusrojo.mvvmdemo.working

import androidx.lifecycle.*
import com.jesusrojo.mvvmdemo.data.model.UiData
import com.jesusrojo.mvvmdemo.data.repository.fake.FakeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


//https://www.droidcon.com/news-detail?content-id=/repository/collaboration/Groups/spaces/droidcon_hq/Documents/public/news/android-news/Kotlin%20Flow%20on%20Android%20-%20Quick%20guide
class FlowApiService(){
    suspend fun fetchDatas(): List<UiData> = FakeRepository.getFakeListItemsOneTwo()
}

class FlowRepository(private val api: FlowApiService) {

    fun fetchFlowDatas(): Flow<List<UiData>> {
        return flow {
            val fooList = api.fetchDatas()
//                .map { fooFromApi ->    FooUI.fromResponse(fooFromApi)  }
            emit(fooList)
        }.flowOn(Dispatchers.IO) // Use the IO thread for this Flow
    }

//    fun getFooAlternative(): Flow<List<UiData>> {
//        return api.fetchDatas()
//            .map { /*fooFromApi -> FooUI.fromResponse(fooFromApi)*/}
//            .asFlow()
//            .flowOn(Dispatchers.IO)
//    }
}

class FlowUiDataViewModel(val repository: FlowRepository) : ViewModel() {

    //
    private val _foo = MutableLiveData<List<UiData>>()
    val foo: LiveData<List<UiData>> get() = _foo

    fun loadFoo() {
        viewModelScope.launch {
            repository.fetchFlowDatas()
                .onStart { /* _foo.value = loading state */ }
                .catch { exception -> /* _foo.value = error state */ }
                .collect { fooItems ->
                    _foo.value = fooItems
                }
        }
    }
}
