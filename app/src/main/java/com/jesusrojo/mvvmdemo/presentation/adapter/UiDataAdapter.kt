package com.jesusrojo.mvvmdemo.presentation.adapter

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jesusrojo.mvvmdemo.data.model.UiData
import com.jesusrojo.mvvmdemo.databinding.ItemLayoutBinding
import com.jesusrojo.mvvmdemo.util.DebugHelp


class UiDataAdapter(
    private var values: ArrayList<UiData>,
    private val resources: Resources?,
    private val listener: (UiData) -> Unit,
    private val listenerMenu: (UiData) -> Unit
) : RecyclerView.Adapter<UiDataViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UiDataViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemLayoutBinding = ItemLayoutBinding.inflate(
            layoutInflater, parent, false)
        return UiDataViewHolder(binding, resources, listener, listenerMenu)
    }

    override fun onBindViewHolder(holder: UiDataViewHolder, position: Int) {
        val item = values[position]
        holder.bindMyHolder(item, position)
    }

    override fun getItemCount(): Int = values.size

    fun setNewValues(newValues: ArrayList<UiData>) {
        DebugHelp.l("setNewValues")
        values.clear()
        values.addAll(newValues)
        notifyDataSetChanged()
    }

    fun deleteAll() {
        values.clear()
        notifyDataSetChanged()
    }


    // SORT
    fun orderByName() {
        values.sortWith(
            compareBy(String.CASE_INSENSITIVE_ORDER, { it.name })
        )
        notifyDataSetChanged()
    }

    fun orderByAuthorName() {
        values.sortWith(
            compareBy(String.CASE_INSENSITIVE_ORDER, { it.title })
        )
        notifyDataSetChanged()
    }

    fun orderByForks() {
        values.sortBy { it.forksCount }
        values.reverse()
        notifyDataSetChanged()
    }

    fun orderByStars() {
        values.sortBy { it.startsCount }
        values.reverse()
        notifyDataSetChanged()
    }
}