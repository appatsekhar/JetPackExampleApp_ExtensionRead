package com.toeii.extensionreadjetpack.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.toeii.extensionreadjetpack.R

object ImageHelper {


    @BindingAdapter("imageUrl")
    @JvmStatic
    fun loadImage(imageView: ImageView, url: String?) {
        Glide.with(imageView.context).load(url)
//            .placeholder(R.mipmap.icon_error_cover)
            .error(R.mipmap.icon_error_cover)
            .into(imageView)
    }


}
