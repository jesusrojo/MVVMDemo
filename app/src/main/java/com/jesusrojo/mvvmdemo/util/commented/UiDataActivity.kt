@file:Suppress("PackageDirectoryMismatch")
//package com.jesusrojo.mvvmdemo.util.commented
//import android.os.Bundle
//import android.widget.Toast
//import com.jesusrojo.mvvmdemo.presentation.di.Injection
//import com.jesusrojo.mvvmdemo.presentation.viewmodel.UiDataViewModel
//import com.jesusrojo.mvvmdemo.util.Resource
//import com.jesusrojo.mvvmdemo.util.exhaustive
//
//
//open class UiDataActivity: UiActivity() {
//
//    private lateinit var viewModel: UiDataViewModel
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
////        viewModel = Injection.getInstanceUiDataViewModel(this)
//
//        observeViewModel()
//
//        if(savedInstanceState == null) viewModel.fetchDatas()
//    }
//
//    private fun observeViewModel() {
//        viewModel.resourceUiDatas.observe(this, { state ->
//            when (state) {
//                is Resource.Success -> updateUiSuccessUiDatas(state.data)
//                is Resource.Error -> updateUiError(state.message!!)
//                is Resource.Loading -> updateUiLoading()
//            }.exhaustive
//        })
//    }
//
//    override fun refreshDatas() {
//        viewModel.fetchDatas()
//      //  viewModel.refreshDatas()
//    }
//
//    override fun fetchNextDatas() {
//        Toast.makeText(applicationContext, "fetchNextDatas", Toast.LENGTH_SHORT).show()
//        viewModel.fetchNextDatas()
//    }
//
//    override fun deleteAll() {
//        viewModel.deleteAll()
//    }
//}