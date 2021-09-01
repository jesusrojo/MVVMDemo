package com.jesusrojo.mvvmdemo.presentation.viewmodel

import androidx.lifecycle.*
import com.jesusrojo.mvvmdemo.domain.usecase.*

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job

class FlowUiDataViewModel(
    private val fetchDatasUseCase: FetchDatasUseCase,
    private val fetchNextDatasUseCase: FetchNextDatasUseCase,
    private val refreshDatasUseCase: RefreshDatasUseCase,
    private val deleteAllUseCase: DeleteAllUseCase,
    private val deleteAllCacheUseCase: DeleteAllCacheUseCase,
    private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private var fetchDatasJob: Job? = null
    private val vmScope = viewModelScope
    private var query = "Kotlin"

    //https://developer.android.com/kotlin/coroutines/coroutines-best-practices
//    private val _uiState = MutableStateFlow(LatestNewsUiState.Loading)
//    val uiState: StateFlow<LatestNewsUiState> = _uiState
//    fun fetchDatas() { fetchDatas(query) }
//
//    fun fetchDatas(userQuery: String) {
//        query = userQuery
//
//        _resourceUiDatas.postValue(Resource.Loading(null))
//        fetchDatasJob?.cancel()
//        fetchDatasJob = vmScope.launch(ioDispatcher) {
//            try {
//                val resourceResult = fetchDatasUseCase.execute(query)
//                _resourceUiDatas.postValue(resourceResult)
//            } catch (e: Exception) {
//                _resourceUiDatas.postValue(Resource.Error("Error $e"))
//            }
//        }
//    }
//    fun fetchNextDatas() {}
//    fun refreshDatas() {}
//    fun deleteAll() {}
//    fun deleteAllCache() {}
//    override fun onCleared() { super.onCleared() }
}