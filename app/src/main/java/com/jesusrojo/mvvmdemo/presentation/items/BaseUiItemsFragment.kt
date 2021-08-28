package com.jesusrojo.mvvmdemo.presentation.items

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jesusrojo.mvvmdemo.R
import com.jesusrojo.mvvmdemo.data.model.UiData
import com.jesusrojo.mvvmdemo.databinding.ItemsLayoutBinding
import com.jesusrojo.mvvmdemo.presentation.adapter.UiDataAdapter
import com.jesusrojo.mvvmdemo.presentation.di.Injection
import com.jesusrojo.mvvmdemo.presentation.dialogfragments.DetailsDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import com.jesusrojo.mvvmdemo.presentation.activities.MainActivity
import com.jesusrojo.mvvmdemo.presentation.activities.MainDrawerActivity
import com.jesusrojo.mvvmdemo.presentation.fragments.SecondFragment
import com.jesusrojo.mvvmdemo.util.*

@AndroidEntryPoint
abstract class BaseUiItemsFragment : Fragment() {

    private lateinit var uiDataAdapter: UiDataAdapter

    private var _binding: ItemsLayoutBinding? = null
    private val binding get() = _binding!! // property only valid between onCreateView and onDestroyView

    private lateinit var llm: LinearLayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        _binding = ItemsLayoutBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        DebugHelp.l("onCreateView")
        initMyAdapter()
        initMyRecycler()
        checkInternetAndShowSnackBarIfIsKO()
    }

    override fun onDestroyView() {
        super.onDestroyView()
          _binding = null
    }


    // ADAPTER
    private fun initMyAdapter() {
        uiDataAdapter = Injection.provideUsersAdapter(
            ArrayList(),
            this.resources,
            { uiData -> onClickAdapterItem(uiData) },
            { uiData -> onClickAdapterItemMenu(uiData) }
        )
    }

    private fun onClickAdapterItem(uidata: UiData) {
        val bundle = bundleOf(SecondFragment.UIDATA_PARAM_KEY to uidata)
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
    }

    private fun onClickAdapterItemMenu(data: UiData) {
      DetailsDialogFragment.showInfoDialogFragment(
             requireActivity() as AppCompatActivity, data.avatarUrl, data.toString()) // _UP
    }


    //OPEN TO VIEW MODEL
    protected open fun fetchDatas(query: String) { toast("fetchDatas") }

    protected open fun refreshDatas() { toast("refreshDatas") }

    protected open fun fetchNextDatas() { toast("fetchNextDatas") }

    @CallSuper
    protected open fun deleteAll() { uiDataAdapter.deleteAll() }

    @CallSuper
    protected open fun deleteAllCache() { uiDataAdapter.deleteAll() }

    protected open fun getLastQueryTypedVM(): String? = "" //viewModel?.query



    // RECYCLER
    private fun initMyRecycler() {
        llm = LinearLayoutManager(requireActivity().applicationContext)

        binding.swipeLayoutContainerItems.setOnRefreshListener {
            binding.swipeLayoutContainerItems.isRefreshing = false
            onRefreshAction()
        }

        binding.recyclerViewItems.apply {
            layoutManager = llm
            adapter = uiDataAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    //debugScrollRecycler(dy)
                    if (dy > 0) {
                        val childCount = uiDataAdapter.itemCount
                        //FAIL WITH NEWS
                        // val lastPosition = llm.findLastCompletelyVisibleItemPosition()
                        val lastPosition = llm.findLastVisibleItemPosition()

                        if (childCount - 1 == lastPosition && isGoneProgressBar()) {
                            onScrollToLastPositionRecyclerAction()
                        }
                    }
                }
            })
        }
    }

//    private fun debugScrollRecycler(dy: Int) {
//        DebugHelp.l("onScrolled $dy uiDataAdapter.itemCount ${uiDataAdapter.itemCount}")
//        DebugHelp.l(
//            "isGoneProgressBar() ${isGoneProgressBar()} " +
//                    "lastPosition ${llm.findLastCompletelyVisibleItemPosition()} " +
//                    "lastPosition2 ${llm.findLastVisibleItemPosition()}")
//    }

    private fun scrollRecyclerToPositionZero() {
        binding.recyclerViewItems.scrollToPosition(0)
    }

    //ACTIONS FROM RECYCLER
    private fun onRefreshAction() {
        if(isNetworkNotAvailableShowMessage()) return
        refreshDatas()
    }

    fun onScrollToLastPositionRecyclerAction() {
        if(isNetworkNotAvailableShowMessage()) return
        fetchNextDatas()
    }

    //UI UPDATES
    protected fun updateUiSuccessUiDatas(datas: List<UiData>?) {
        DebugHelp.l("updateUiSuccessUiDatas")
        hideProgressBar()

        if (datas != null) {
            if (datas.isNotEmpty()) {

                uiDataAdapter.setNewValues(datas as ArrayList<UiData>)
                scrollRecyclerToPositionZero()

                hideTextViewError()
            } else {
                showMsgBadList()
            }
        } else {
            showMsgBadList()
        }
    }

    protected fun updateUiError(message: String?) {
        DebugHelp.l("updateUiError $message")
        hideProgressBar()
        if (message != null && message.isNotEmpty()) {
            showTextViewErrorWithMessage(message)
        } else {
            hideTextViewError()
        }
    }

    protected fun updateUiLoading() {
        DebugHelp.l("updateUiLoading")
        showProgressBar()
        hideTextViewError()
    }


    // PROGRESS BAR
    private fun showProgressBar() {
        binding.progressBarItems.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBarItems.visibility = View.GONE
    }

    private fun isGoneProgressBar() = binding.progressBarItems.visibility == View.GONE

    // TEXT VIEW ERROR
    private fun hideTextViewError() {
        binding.textViewErrorItems.visibility = View.GONE
        binding.textViewErrorItems.text = ""
    }

    private fun showTextViewErrorWithMessage(message: String?) {
        binding.textViewErrorItems.text = message
        binding.textViewErrorItems.visibility = View.VISIBLE
    }

    private fun showMsgBadList() {
        val message = getString(R.string.error_empty_list_explanation)
        binding.textViewErrorItems.text = message
        binding.textViewErrorItems.visibility = View.VISIBLE
    }

    //MENU TOP RIGHT
    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
          //// R.id.menu_item_search ->   true
            R.id.menu_item_10 -> {
                uiDataAdapter.orderByName()
                scrollRecyclerToPositionZero()
                true
            }
            R.id.menu_item_11 -> {
                uiDataAdapter.orderByAuthorName()
                scrollRecyclerToPositionZero()
                true
            }
            R.id.menu_item_12 -> {
                uiDataAdapter.orderByForks()
                scrollRecyclerToPositionZero()
                true
            }
            R.id.menu_item_13 -> {
                uiDataAdapter.orderByStars()
                scrollRecyclerToPositionZero()
                true
            }

            R.id.menu_item_20 -> {
                deleteAll()
                true
            }
            R.id.menu_item_21 -> {
                deleteAllCache()
                true
            }
            R.id.menu_item_30 -> {
                requireActivity().showAlertDialog(DebugHelp.fullLog)
                true
            }
            R.id.menu_item_31 -> {
                DebugHelp.fullLog = ""
                true
            }

            R.id.menu_item_40 -> {
                requireActivity().startMyActivity<MainActivity>()
                true
            }
            R.id.menu_item_41 -> {
                requireActivity().startMyActivity<MainDrawerActivity>()
                true
            }
            else -> false
        }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_top_right_main, menu)
        initSearchMenuPresenter(menu)
    }


    //SEARCH
    private fun initSearchMenuPresenter(menu: Menu) {
        val mSearchMenuPresenter = SearchMenuPresenter(requireActivity(), menu,
        object : SearchMenuPresenter.SearchMenuPresenterListener {

            override fun onQueryTextSubmit(query: String?) {
                query?.let {
                    if(isNetworkNotAvailableShowMessage()) return
                    fetchDatas(query)
                }
            }

            override fun getLastQueryTyped(): String? = getLastQueryTypedVM()
        })
        mSearchMenuPresenter.initSearchStaff()
    }


    // INTERNET
    private fun checkInternetAndShowSnackBarIfIsKO() {
        InternetHelp.checkInternetAndShowSnackBarIfIsKO(requireActivity())
    }
    private fun isNetworkNotAvailableShowMessage(): Boolean {
        ////TODO  HERE OR IN VIEW MODEL?
        //// viewModel.hasInternet = isNetworkNotAvailable()
        if (!InternetHelp.isNetworkNotAvailableShowMessage(requireActivity())) {
            return true
        }
        return false
    }


    //TOAST
    private fun toast(s: String) {
        Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show()
    }

//    private fun navigateToSecondFragment() {
//        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//    }
}