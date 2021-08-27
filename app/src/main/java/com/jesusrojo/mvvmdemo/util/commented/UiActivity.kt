@file:Suppress("PackageDirectoryMismatch")
//package com.jesusrojo.mvvmdemo.util.commented
//import android.os.Bundle
//import android.view.Menu
//import android.view.MenuItem
//import android.view.View
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.databinding.DataBindingUtil
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.jesusrojo.mvvmdemo.databinding.ItemsLayoutBinding
//import com.jesusrojo.mvvmdemo.presentation.adapter.UiDataAdapter
//import com.jesusrojo.mvvmdemo.presentation.di.Injection
//import com.jesusrojo.mvvmdemo.util.InternetHelp
//import com.jesusrojo.retrofitdemo.R
//import com.jesusrojo.retrofitdemo.databinding.ItemsLayoutBinding
//import com.jesusrojo.retrofitdemo.uidata.data.model.UiData
//import com.jesusrojo.retrofitdemo.uidata.presentation.adapter.UiDataAdapter
//import com.jesusrojo.retrofitdemo.uidata.presentation.details.DetailsDialogFragment
//import com.jesusrojo.retrofitdemo.uidata.presentation.di.Injection
//import com.jesusrojo.retrofitdemo.util.DebugHelp
//import com.jesusrojo.retrofitdemo.util.InternetHelp
//import retrofit2.Response
//
//
//abstract class UiActivity : AppCompatActivity() {
//
//    private lateinit var uiDataAdapter: UiDataAdapter
//    private lateinit var binding: ItemsLayoutBinding
//    private lateinit var llm: LinearLayoutManager
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        initMyAdapter()
//        initRecycler()
//        initButtonsUi()
//        InternetHelp.checkInternetAndShowSnackBarIfIsKO(this)
//    }
//
//    private fun initMyAdapter() {
//        uiDataAdapter = Injection.provideUsersAdapter(
//            ArrayList(), this.resources
//        ) { data ->
////            DetailsDialogFragment.showInfoDialogFragment(
////                this, data.avatar_url,
////                data.toString()
////            )
//        }
//    }
//
//    // RECYCLER
//    private fun initRecycler() {
//        llm = LinearLayoutManager(applicationContext)
//
//        binding.swipeLayoutContainerItems.setOnRefreshListener {
//            binding.swipeLayoutContainerItems.isRefreshing = false
//            onRefreshAction()
//        }
//
//        binding.recyclerViewItems.apply {
//            layoutManager = llm
//            adapter = uiDataAdapter
//            addOnScrollListener(object : RecyclerView.OnScrollListener() {
//                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                    super.onScrolled(recyclerView, dx, dy)
//                    if (dy > 0) {
//                        val childCount = uiDataAdapter.itemCount
//                        val lastPosition = llm.findLastCompletelyVisibleItemPosition()
//                        if (childCount - 1 == lastPosition && isGoneProgressBar()) {
//                            onScrollToLastPositionRecyclerAction()
//                        }
//                    }
//                }
//            })
//        }
//    }
//
//    private fun onRefreshAction() {
//        if (isNetworkNotAvailableShowMessage()) return
//        refreshDatas()
//    }
//
//    fun onScrollToLastPositionRecyclerAction() {
//        if (isNetworkNotAvailableShowMessage()) return
//        fetchNextDatas()
//    }
//
//    //UI UPDATES
//    protected fun updateUiSuccessUiDatas(datas: List<UiData>?) {
//        DebugHelp.l("updateUiSuccessUiDatas")
//        hideProgressBar()
//
//        if (datas != null) {
//            if (datas.isNotEmpty()) {
//
//                uiDataAdapter.setNewValues(datas as ArrayList<UiData>)
//                binding.recyclerViewItems.scrollToPosition(0)
//
//                hideTextViewError()
//            } else {
//                showMsgBadList()
//            }
//        } else {
//            showMsgBadList()
//        }
//    }
//
//    protected fun updateUiError(message: String?) {
//        DebugHelp.l("updateUiError $message")
//        hideProgressBar()
//        if (message != null && message.isNotEmpty()) {
//            showTextViewErrorWithMessage(message)
//        } else {
//            hideTextViewError()
//        }
//    }
//
//    protected fun updateUiLoading() {
//        DebugHelp.l("updateUiLoading")
//        showProgressBar()
//        hideTextViewError()
//    }
//
//
//    // PROGRESS BAR
//    private fun showProgressBar() {
//        binding.progressBarItems.visibility = View.VISIBLE
//    }
//
//    private fun hideProgressBar() {
//        binding.progressBarItems.visibility = View.GONE
//    }
//
//    private fun isGoneProgressBar() = binding.progressBarItems.visibility == View.GONE
//
//
//    // TEXT VIEW ERROR
//    private fun hideTextViewError() {
//        binding.textViewErrorItems.visibility = View.GONE
//        binding.textViewErrorItems.text = ""
//    }
//
//    private fun showTextViewErrorWithMessage(message: String?) {
//        binding.textViewErrorItems.text = message
//        binding.textViewErrorItems.visibility = View.VISIBLE
//    }
//
//    private fun showMsgBadList() {
//        val message = getString(R.string.list_is_empty) +
//                "\n\n" + getString(R.string.swipe_down_to_get_fresh_data)
//        binding.textViewErrorItems.text = message
//        binding.textViewErrorItems.visibility = View.VISIBLE
//    }
//
//
//    // ABSTRACTS ACTIONS
//    open fun fetchDatas() {}
//    open fun refreshDatas() {}
//    open fun fetchNextDatas() {}
//    open fun deleteAll() {}
//
//
//    // MENU TOP RIGHT
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        val inflater = menuInflater
//        inflater.inflate(R.menu.menu_top_right, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.menu_item_1 -> {
//                uiDataAdapter.orderByDataId()
//                binding.recyclerViewItems.scrollToPosition(0)
//                toast("By Id")
//                true
//            }
//            R.id.menu_item_2 -> {
//                uiDataAdapter.orderByName()
//                binding.recyclerViewItems.scrollToPosition(0)
//                toast("By Name")
//                true
//            }
//            R.id.menu_item_3 -> {
//                uiDataAdapter.deleteAll() // we delete all recycler from here.
//                //Also we will update with empty list
//                deleteAll()
//                toast("Delete All")
//
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
//
//    // TOAST
//    protected fun toast(message: String) {
//        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
//    }
//
//    // INTERNET ("private", we check everything in this class)
//    private fun isNetworkNotAvailableShowMessage(): Boolean {
//        ////TODO  HERE OR IN VIEW MODEL?
//        //// viewModel.hasInternet = isNetworkNotAvailable()
//        if (!InternetHelp.isNetworkNotAvailableShowMessage(this)) {
//            return true
//        }
//        return false
//    }
//
//
//    // RESPONSE
//    protected fun <T> responseNotIsSuccessfulShowMessage(it: Response<T>): Boolean {
//        return if (it.isSuccessful) {
//            false
//        } else {
//            updateUiError(it.errorBody().toString())
//            true
//        }
//    }
//
//    //BUTTONS
//    protected open fun initButtonsUi() {
//        binding.btn01.setOnClickListener{ onClickBtn01() }
//        binding.btn02.setOnClickListener{ onClickBtn02() }
//        binding.btn03.setOnClickListener{ onClickBtn03() }
//        binding.btn04.setOnClickListener{ onClickBtn04() }
//    }
//
//    protected open fun onClickBtn01(){
//        Toast.makeText(applicationContext, "clicked", Toast.LENGTH_SHORT).show()
//        updateUiLoading()
//        fetchDatas()
//    }
//    protected open fun onClickBtn02(){}
//    protected open fun onClickBtn03(){}
//    protected open fun onClickBtn04(){}
//
//    protected fun setupBtn1(textUi: String){
//        binding.btn01.text = textUi
//        binding.btn01.visibility = View.VISIBLE
//    }
//    protected fun setupBtn2(textUi: String){
//        binding.btn02.text = textUi
//        binding.btn02.visibility = View.VISIBLE
//    }
//    protected fun setupBtn3(textUi: String){
//        binding.btn03.text = textUi
//        binding.btn03.visibility = View.VISIBLE
//
//    }
//    protected fun setupBtn4(textUi: String){
//        binding.btn04.text = textUi
//        binding.btn04.visibility = View.VISIBLE
//    }
//    //END BUTTONS
//}