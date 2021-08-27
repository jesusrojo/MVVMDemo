package com.jesusrojo.mvvmdemo.presentation.di

import android.content.res.Resources
import com.jesusrojo.mvvmdemo.data.model.UiData
import com.jesusrojo.mvvmdemo.presentation.adapter.UiDataAdapter


class Injection {

    companion object {

        fun provideUsersAdapter(values: ArrayList<UiData>,
                                resources: Resources?,
                                listener: (UiData) -> Unit,
                                listenerMenu: (UiData) -> Unit
        ): UiDataAdapter = UiDataAdapter(values, resources, listener, listenerMenu)


//        fun provideSharedPreferences(context: Context): SharedPreferences =
//            PreferenceManager.getDefaultSharedPreferences(context)
    }
}