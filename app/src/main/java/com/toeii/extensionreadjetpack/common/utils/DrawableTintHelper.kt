package com.toeii.extensionreadjetpack.common.utils

import androidx.core.graphics.drawable.DrawableCompat
import android.graphics.drawable.Drawable
import android.content.res.ColorStateList

object DrawableTintHelper {

    /**
     * Drawable 颜色转化类
     *
     * @param drawable
     * @param color资源
     * @return 改变颜色后的Drawable
     */
    fun tintDrawable(drawable: Drawable, color: Int): Drawable {
        val wrappedDrawable = getCanTintDrawable(drawable)
        DrawableCompat.setTint(wrappedDrawable, color)
        return wrappedDrawable
    }

    /**
     * Drawable 颜色转化类
     *
     * @param drawable 源Drawable
     * @param ColorStateList
     * @return 改变颜色后的Drawable
     */
    fun tintListDrawable(drawable: Drawable, colors: ColorStateList): Drawable {
        val wrappedDrawable = getCanTintDrawable(drawable)
        DrawableCompat.setTintList(wrappedDrawable, colors)
        return wrappedDrawable
    }

    private fun getCanTintDrawable(drawable: Drawable): Drawable {
        val state = drawable.constantState
        return DrawableCompat.wrap(state?.newDrawable() ?: drawable).mutate()
    }

}