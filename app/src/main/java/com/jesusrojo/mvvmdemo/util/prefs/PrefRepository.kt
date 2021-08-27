@file:Suppress("unused")

package com.jesusrojo.mvvmdemo.util.prefs

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager

class PrefRepository(val context: Context) {

    private val pref: SharedPreferences =  PreferenceManager.getDefaultSharedPreferences(context)
//    private val pref: SharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    private val editor = pref.edit()

//    private val gson = Gson()

    private fun String.put(long: Long) {
        editor.putLong(this, long)
        editor.commit()
    }

    private fun String.put(int: Int) {
        editor.putInt(this, int)
        editor.commit()
    }

    private fun String.put(string: String) {
        editor.putString(this, string)
        editor.commit()
    }

    private fun String.put(boolean: Boolean) {
        editor.putBoolean(this, boolean)
        editor.commit()
    }

    private fun String.getLong() = pref.getLong(this, 0)

    private fun String.getInt() = pref.getInt(this, 0)

    private fun String.getString() = pref.getString(this, "")!!

    private fun String.getBoolean() = pref.getBoolean(this, false)



    fun setLoggedIn(isLoggedIn: Boolean) {
        PREF_LOGGED_IN.put(isLoggedIn)
    }

    fun setLoggedIn() = PREF_LOGGED_IN.getBoolean()

    fun setShareMsg(msg: String) {
        PREF_SHARE_MESSAGE.put(msg)
    }

    fun getShareMsg() = PREF_SHARE_MESSAGE.getString()

    fun setMinimumAppVersion(version: Long) {
        PREF_MINIMUM_APP_VERSION.put(version)
    }

    fun getMinimumAppVersion() = PREF_MINIMUM_APP_VERSION.getLong()

//    fun setLastRefreshTime(date: Date) {
//        PREF_LAST_REFRESH_TIME.put(gson.toJson(date))
//    }
//
//    fun getLastRefreshTime(): Date? {
//        PREF_LAST_REFRESH_TIME.getString().also {
//            return if (it.isNotEmpty())
//                gson.fromJson(PREF_LAST_REFRESH_TIME.getString(), Date::class.java)
//            else
//                null
//        }
//    }

    fun clearData() {
        editor.clear()
        editor.commit()
    }
}

// HOW TO USE
class HomePageFragment: Fragment() {

    private val prefRepository by lazy { PrefRepository(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //To set the value
        prefRepository.setLoggedIn(true)

        //To retrieve the value from shared preferences
        val msg = prefRepository.getShareMsg()
    }
}