package com.jesusrojo.mvvmdemo.presentation.items

import android.os.Bundle
import android.view.*
import androidx.lifecycle.ViewModelProvider
import com.jesusrojo.mvvmdemo.presentation.viewmodel.UiDataViewModel
import com.jesusrojo.mvvmdemo.presentation.viewmodel.UiDataViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.jesusrojo.mvvmdemo.util.*


@AndroidEntryPoint
class ItemsFragment : BaseUiItemsFragment() {

    @Inject lateinit var viewModelFactory: UiDataViewModelFactory
    private lateinit var viewModel: UiDataViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory).get(UiDataViewModel::class.java)
        observeViewModel()
        if (savedInstanceState == null) viewModel.fetchDatas()
    }

    //VIEW MODEL
    private fun observeViewModel() {
        viewModel.resourceUiDatas.observe(viewLifecycleOwner, { state ->
            when (state) {
                is Resource.Success -> updateUiSuccessUiDatas(state.data)
                is Resource.Error -> updateUiError(state.message!!)
                is Resource.Loading -> updateUiLoading()
            }.exhaustive
        })
    }

    override fun fetchDatas(query: String) {
        super.fetchDatas(query)
        viewModel.fetchDatas(query)
    }

    override fun fetchNextDatas() {
        viewModel.fetchNextDatas()
    }

    override fun refreshDatas() {
        super.refreshDatas()
        viewModel.refreshDatas()
    }

    override fun deleteAll() {
        super.deleteAll()
        viewModel.deleteAll()
    }

    override fun deleteAllCache() {
        super.deleteAllCache()
        viewModel.deleteAllCache()
    }

    override fun getLastQueryTypedVM(): String? = viewModel?.query
}