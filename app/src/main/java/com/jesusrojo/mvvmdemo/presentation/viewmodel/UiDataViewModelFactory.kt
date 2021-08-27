package com.jesusrojo.mvvmdemo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jesusrojo.mvvmdemo.domain.usecase.DeleteAllCacheUseCase
import com.jesusrojo.mvvmdemo.domain.usecase.DeleteAllUseCase
import com.jesusrojo.mvvmdemo.domain.usecase.FetchDatasUseCase
import com.jesusrojo.mvvmdemo.domain.usecase.FetchNextDatasUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject


class UiDataViewModelFactory @Inject constructor(
    private val fetchDatasUseCase: FetchDatasUseCase,
    private val fetchNextDatasUseCase: FetchNextDatasUseCase,
    private val deleteAllUseCase: DeleteAllUseCase,
    private val deleteAllCacheUseCase: DeleteAllCacheUseCase,
    private val ioDispatcher: CoroutineDispatcher
    //@IoDispatcher ioDispatcher: CoroutineDispatcher
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(UiDataViewModel::class.java)) {
            return UiDataViewModel(
                fetchDatasUseCase, fetchNextDatasUseCase,
                deleteAllUseCase, deleteAllCacheUseCase, ioDispatcher
            ) as T
        }

        throw IllegalArgumentException("Unknown class name in UiDataViewModelFactory")
    }
}