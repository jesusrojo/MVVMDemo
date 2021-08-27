package com.jesusrojo.mvvmdemo.util

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.jesusrojo.mvvmdemo.presentation.di.hilt.GlideApp

fun loadImageSquareGlide(imageView: ImageView, avatarUrl: String?) {
    if (avatarUrl != null && avatarUrl.isNotEmpty()) {
        //Glide.with(binding.ivAvatar.context) // without HILT
        GlideApp.with(imageView.context) // with HILT
            .load(avatarUrl)
            .into(imageView)
    }
}


fun loadImageCircleGlide(textView: TextView, avatarUrl: String?) {
    if (avatarUrl != null && avatarUrl.isNotEmpty()) {
        ///// Glide.with(textView.context)// without HILT
        GlideApp.with(textView.context) // with HILT
            .load(avatarUrl)
            .apply(RequestOptions.circleCropTransform())

            // IMAGE VIEW
            //.into(binding.imageViewAvatarDetails)

            // TEXT VIEW
            .into(object : CustomTarget<Drawable>(300, 300) {

                override fun onLoadCleared(drawable: Drawable?) = setDrawableInTextView(drawable)

                override fun onResourceReady(
                    drawable: Drawable,
                    transition: Transition<in Drawable>?
                ) =
                    setDrawableInTextView(drawable)

                private fun setDrawableInTextView(drawable: Drawable?) {
                    textView.setCompoundDrawablesWithIntrinsicBounds(
                        null, drawable, null, null
                    )
                }
            })

    }
}