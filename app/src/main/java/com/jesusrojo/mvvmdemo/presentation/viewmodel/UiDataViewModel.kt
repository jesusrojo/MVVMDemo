package com.jesusrojo.mvvmdemo.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesusrojo.mvvmdemo.data.model.UiData
import com.jesusrojo.mvvmdemo.domain.usecase.DeleteAllCacheUseCase
import com.jesusrojo.mvvmdemo.domain.usecase.DeleteAllUseCase
import com.jesusrojo.mvvmdemo.domain.usecase.FetchDatasUseCase
import com.jesusrojo.mvvmdemo.domain.usecase.FetchNextDatasUseCase
import com.jesusrojo.mvvmdemo.util.DebugHelp
import com.jesusrojo.mvvmdemo.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class UiDataViewModel(
    private val fetchDatasUseCase: FetchDatasUseCase,
    private val fetchNextDatasUseCase: FetchNextDatasUseCase,
    private val deleteAllUseCase: DeleteAllUseCase,
    private val deleteAllCacheUseCase: DeleteAllCacheUseCase,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val vmScope = viewModelScope
    private var fetchDatasJob: Job? = null
    private var fetchNextDatasJob: Job? = null
    private var refreshDatasJob: Job? = null
    private var deleteAllJob: Job? = null
    private var deleteAllCacheJob: Job? = null
    private var page = 1
    private val defaultQuery = "Kotlin"
    var query = defaultQuery // public, we use it to setDefaultQuery in searchView

    private val _resourceUiDatas = MutableLiveData<Resource<List<UiData>>>()
    val resourceUiDatas: LiveData<Resource<List<UiData>>> = _resourceUiDatas

    init{
        DebugHelp.l("init viewModel")
        // query = saveStateHandler todo implement or move
        // page = saveStateHandler
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
                val resourceResult = fetchDatasUseCase.execute(page, query)
                _resourceUiDatas.postValue(resourceResult)
            } catch (e: Exception) {
                _resourceUiDatas.postValue(Resource.Error("Error $e"))
            }
        }
    }

    fun refreshDatas() {
        page = 1
        refreshDatasJob?.cancel()
        refreshDatasJob = vmScope.launch(ioDispatcher) {
            try {
                deleteAllUseCase.execute()

                val resourceResult = fetchNextDatasUseCase.execute(page, query)
                _resourceUiDatas.postValue(resourceResult)
            } catch (e: Exception) {
                _resourceUiDatas.postValue(Resource.Error("Error $e"))
            }
        }
    }
    fun fetchNextDatas() {
        page++
        _resourceUiDatas.postValue(Resource.Loading(null))
        fetchNextDatasJob?.cancel()
        fetchNextDatasJob = vmScope.launch(ioDispatcher) {
            try {
                val resourceResult = fetchNextDatasUseCase.execute(page, query)
                _resourceUiDatas.postValue(resourceResult)
            } catch (e: Exception) {
                _resourceUiDatas.postValue(Resource.Error("Error $e"))
            }
        }
    }

    fun deleteAll() {
        page = 1
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
        page = 1
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