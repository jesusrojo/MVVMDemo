package com.jesusrojo.mvvmdemo.util

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.jesusrojo.mvvmdemo.R


// needs  MANIFEST
// <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
class InternetHelp {

    companion object {

        fun isNetworkNotAvailableShowMessage(activity: Activity?): Boolean {
            if(!isNetworkAvailable(activity?.applicationContext)){
                showSnackBarNoInternet(activity)
                return false
            }
            return true
        }

        fun checkInternetAndShowSnackBarIfIsKO(activity: Activity?) {
            activity?.let {
                if(!isNetworkAvailable(activity.applicationContext)){
                    showSnackBarNoInternet(activity)
                }
            }
        }

        private fun isNetworkAvailable(context: Context?): Boolean {
            if (context == null) return false
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                if (capabilities != null) {
                    when {
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                            return true
                        }
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                            return true
                        }
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                            return true
                        }
                    }
                }
            } else {
                val activeNetworkInfo = connectivityManager.activeNetworkInfo
                if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                    return true
                }
            }
            return false
        }

///////////WITH ANDROID  android.R.id.content
    private fun showSnackBarNoInternet(activity: Activity?) {
             activity?.let {
                 val rootView: View = activity.findViewById(android.R.id.content)
                 val snack = Snackbar.make(rootView, R.string.error_no_internet,
                     Snackbar.LENGTH_SHORT)
                 snack.show()
             }
         }
//////////
    }
}