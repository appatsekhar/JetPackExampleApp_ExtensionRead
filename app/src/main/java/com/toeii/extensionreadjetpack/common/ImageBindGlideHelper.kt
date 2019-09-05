package com.toeii.extensionreadjetpack.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.toeii.extensionreadjetpack.R

object ImageBindGlideHelper {


    @BindingAdapter("imageUrl")
    @JvmStatic
    fun loadImage(imageView: ImageView, url: String?) {
        Glide.with(imageView.context).load(url)
            .placeholder(R.mipmap.icon_error_cover)
            .error(R.mipmap.icon_error_cover)
            .into(imageView)
    }


}
