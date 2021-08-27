@file:Suppress("RedundantNullableReturnType")

package com.jesusrojo.mvvmdemo.presentation.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.jesusrojo.mvvmdemo.R
import com.jesusrojo.mvvmdemo.data.model.UiData
import com.jesusrojo.mvvmdemo.databinding.DetailsLayoutBinding
import com.jesusrojo.mvvmdemo.util.loadImageCircleGlide


class SecondFragment : Fragment() {

    companion object { const val UIDATA_PARAM_KEY = "UIDATA_PARAM_KEY" }

    private var _binding: DetailsLayoutBinding? = null
    private val binding get() = _binding!!//property only valid between onCreateView & onDestroyView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        _binding = DetailsLayoutBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data =  arguments?.getParcelable<UiData>(UIDATA_PARAM_KEY)
        if (data != null) bindMyData(binding, data)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bindMyData(binding: DetailsLayoutBinding, data: UiData) {
        binding.imageButtonCancelDetails.visibility = View.GONE //this is for the DialogFragment

        // TEXT EXPLANATION
        val textUi = data.fullDescription
        binding.textViewExplanationDetails.text = textUi

        // AVATAR
        val avatarUrl = data.avatarUrl
        loadImageCircleGlide(binding.textViewExplanationDetails, avatarUrl)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_top_right_second, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.menu_item_save -> {
                Toast.makeText(requireContext(), "Todo save", Toast.LENGTH_SHORT).show()
                //todo save to another database
                true
            }
            else -> false
        }
}