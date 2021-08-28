package com.jesusrojo.mvvmdemo.presentation.viewmodel

import androidx.lifecycle.*
import com.jesusrojo.mvvmdemo.data.model.UiData
import com.jesusrojo.mvvmdemo.domain.usecase.*
import com.jesusrojo.mvvmdemo.util.DebugHelp
import com.jesusrojo.mvvmdemo.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class UiDataViewModel(
    private val fetchDatasUseCase: FetchDatasUseCase,
    private val fetchNextDatasUseCase: FetchNextDatasUseCase,
    private val refreshDatasUseCase: RefreshDatasUseCase,
    private val deleteAllUseCase: DeleteAllUseCase,
    private val deleteAllCacheUseCase: DeleteAllCacheUseCase,
    private val ioDispatcher: CoroutineDispatcher,
   //TODO private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val vmScope = viewModelScope
    private var fetchDatasJob: Job? = null
    private var fetchNextDatasJob: Job? = null
    private var refreshDatasJob: Job? = null
    private var deleteAllJob: Job? = null
    private var deleteAllCacheJob: Job? = null
    private val defaultQuery = "Kotlin"
    var query = defaultQuery // public, we use it to setDefaultQuery in searchView

    private val _resourceUiDatas = MutableLiveData<Resource<List<UiData>>>()
    val resourceUiDatas: LiveData<Resource<List<UiData>>> = _resourceUiDatas

    init{
        DebugHelp.l("init viewModel")
       // query = savedStateHandle["QUERY"] ?: defaultQuery
    }
    override fun onCleared() {
        fetchDatasJob?.cancel()
        fetchNextDatasJob?.cancel()
        refreshDatasJob?.cancel()
        deleteAllJob?.cancel()
        deleteAllCacheJob?.cancel()
        super.onCleared()
    }

    fun fetchDatas() { fetchDatas(query) }

    fun fetchDatas(userQuery: String) {
        query = userQuery
        _resourceUiDatas.postValue(Resource.Loading(null))
        fetchDatasJob?.cancel()
        fetchDatasJob = vmScope.launch(ioDispatcher) {
            try {
                val resourceResult = fetchDatasUseCase.execute(query)
                _resourceUiDatas.postValue(resourceResult)
            } catch (e: Exception) {
                _resourceUiDatas.postValue(Resource.Error("Error $e"))
            }
        }
    }

    fun fetchNextDatas() {
        _resourceUiDatas.postValue(Resource.Loading(null))
        fetchNextDatasJob?.cancel()
        fetchNextDatasJob = vmScope.launch(ioDispatcher) {
            try {
                val resourceResult = fetchNextDatasUseCase.execute(query)
                _resourceUiDatas.postValue(resourceResult)
            } catch (e: Exception) {
                _resourceUiDatas.postValue(Resource.Error("Error $e"))
            }
        }
    }

    fun refreshDatas() {
        refreshDatasJob?.cancel()
        refreshDatasJob = vmScope.launch(ioDispatcher) {
            try {
                val resourceResult = refreshDatasUseCase.execute(query)
                _resourceUiDatas.postValue(resourceResult)
            } catch (e: Exception) {
                _resourceUiDatas.postValue(Resource.Error("Error $e"))
            }
        }
    }


    fun deleteAll() {
        deleteAllJob?.cancel()
        deleteAllJob = vmScope.launch(ioDispatcher) {
            try {
                deleteAllUseCase.execute()
                _resourceUiDatas.postValue(Resource.Success(emptyList()))
            } catch (e: Exception) {
                _resourceUiDatas.postValue(Resource.Error("Error $e"))
            }
        }
    }

    fun deleteAllCache() {
        deleteAllCacheJob?.cancel()
        deleteAllCacheJob = vmScope.launch(ioDispatcher) {
            try {
                 deleteAllCacheUseCase.execute()
                _resourceUiDatas.postValue(Resource.Success(emptyList()))
            } catch (e: Exception) {
                _resourceUiDatas.postValue(Resource.Error("Error $e"))
            }
        }
    }
}