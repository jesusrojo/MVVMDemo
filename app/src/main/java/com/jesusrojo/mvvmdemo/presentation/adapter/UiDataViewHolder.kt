package com.jesusrojo.mvvmdemo.presentation.adapter

import android.content.res.Resources
import androidx.recyclerview.widget.RecyclerView

import com.jesusrojo.mvvmdemo.R

import com.jesusrojo.mvvmdemo.data.model.UiData
import com.jesusrojo.mvvmdemo.databinding.ItemLayoutBinding
import com.jesusrojo.mvvmdemo.util.formatNumber
import com.jesusrojo.mvvmdemo.util.loadImageSquareGlide


class UiDataViewHolder(
    private val binding: ItemLayoutBinding,
    resources: Resources?,
    private val listener: (UiData) -> Unit,
    private val listenerMenu: (UiData) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private var name = resources?.getString(R.string.name)
    private var author = resources?.getString(R.string.author_name)

    fun bindMyHolder(uiData: UiData, position: Int) {

        binding.cardViewContainerItem.setOnClickListener { listener(uiData) }
        binding.menuIndividualTvItem.setOnClickListener { listenerMenu(uiData) }

        var textUi = position.toString()
        binding.positionTvItem.text = textUi //todo DEBUG POSITION

        textUi = "$name:\t\t ${uiData.name}" //uiData.name
        binding.nameTvItem.text = textUi

        textUi =  "$author:\t\t ${uiData.title}"
        binding.authorNameTvItem.text = textUi

        textUi =  formatNumber(uiData.forksCount)
        binding.forksCountTvItem.text = textUi

        textUi =  formatNumber(uiData.startsCount)
        binding.startsCountTvItem.text = textUi

        textUi = uiData.description
        binding.descriptionTvItem.text = textUi

        // AVATAR
        val avatarUrl = uiData.avatarUrl
        loadImageSquareGlide(binding.avatarIvItem, avatarUrl)
    }
}