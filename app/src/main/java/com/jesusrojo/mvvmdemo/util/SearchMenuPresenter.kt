package com.jesusrojo.mvvmdemo.util

import android.annotation.SuppressLint
import com.jesusrojo.mvvmdemo.util.DebugHelp.Companion.l
import android.app.Activity
import com.jesusrojo.mvvmdemo.R
import android.app.SearchManager
import android.content.Context
import android.os.Build
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import java.lang.Exception

class SearchMenuPresenter(
    private val mActivity: Activity?,
    private val mMenu: Menu?,
    private val listener: SearchMenuPresenterListener?
) : SearchView.OnQueryTextListener,
    MenuItem.OnActionExpandListener,
    SearchView.OnCloseListener {


    interface SearchMenuPresenterListener {
        fun onQueryTextSubmit(query: String?)
        fun getLastQueryTyped(): String?
    }

    private val myTag = javaClass.simpleName
    private var searchView: SearchView? = null
    private var mMenuItemSearch: MenuItem? = null

    private val sizeMenu: Int
        get() = mMenu?.size() ?: 0

    fun initSearchStaff() {
        if (mActivity != null && mMenu != null) {
            mMenuItemSearch = mMenu.findItem(R.id.menu_item_1_search)
            if (mMenuItemSearch != null) {
                try {
                    initSearchView()
                } catch (e: Exception) {
                    l(myTag, "ko $e")
                }
            }
        }
    }

    private fun initSearchView() {
        searchView = mMenuItemSearch?.actionView as SearchView
        if (searchView != null) {
            val context = mActivity?.applicationContext
            val searchManager = context?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
            if (searchManager != null) { //Assumes current activity is the searchable activity
                val searchableInfo = searchManager.getSearchableInfo(mActivity?.componentName)
                if (searchableInfo != null) {
                    searchView?.setSearchableInfo(searchableInfo)
                }
            }
            val hintText = mActivity?.resources?.getString(R.string.search_title)
            searchView?.queryHint = hintText
            searchView?.setIconifiedByDefault(false) //Do not iconify the widget; expand it by default
            searchView?.isSubmitButtonEnabled = true

            // TYPING
            searchView?.setOnQueryTextListener(this)

            // CLICK MENU
            searchView?.setOnSearchClickListener {
                //user click icon or menu (View v)
                saveStateMenuItemsAndHideAllButSearch(mMenuItemSearch)
            }

            // CLOSING
            setOnCloseSearchViewListenerWithApiVersion(mMenuItemSearch, searchView!!)
        }
    }

    //TYPING  LISTENER setOnQueryTextListener
    override fun onQueryTextSubmit(query: String): Boolean {
        listener?.onQueryTextSubmit(query)
        return false //FALSE
    }

    override fun onQueryTextChange(newText: String) = false


    // CLOSING 1 - 2
    @SuppressLint("ObsoleteSdkInt")
    private fun setOnCloseSearchViewListenerWithApiVersion(
        menuItemSearch: MenuItem?, searchView: SearchView
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            menuItemSearch?.setOnActionExpandListener(this)
        } else {
            searchView.setOnCloseListener(this)
        }
    }

    // CLOSING 1 LISTENER  setOnActionExpandListener
    override fun onMenuItemActionExpand(item: MenuItem): Boolean {
        setLastQueryInView()
        return true
    }

    override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
        mActivity?.invalidateOptionsMenu()
        return true
    }

    // CLOSING 2 LISTENER  setOnCloseListener
    override fun onClose(): Boolean {
        mActivity?.invalidateOptionsMenu()
        return true
    }


    // MY
    private fun setLastQueryInView() {
        val lastQuery: CharSequence? = listener?.getLastQueryTyped()
        if (lastQuery != null && lastQuery.isNotEmpty()) {
            if (searchView != null) {
                searchView?.post {
                    if (searchView != null) searchView?.setQuery(lastQuery, false)
                }
            }
        }
    }

    private fun saveStateMenuItemsAndHideAllButSearch(menuItemSearch: MenuItem?) {
        val size = sizeMenu
        for (i in 0 until size) {
            val item = mMenu?.getItem(i)
            if (item != null) {
                if (item != menuItemSearch) item.isVisible = false
            }
        }
    }
}