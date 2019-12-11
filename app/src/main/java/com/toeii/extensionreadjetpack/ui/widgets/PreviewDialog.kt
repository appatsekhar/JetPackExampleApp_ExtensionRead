package com.toeii.extensionreadjetpack.ui.widgets

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import com.github.chrisbanes.photoview.PhotoView
import com.toeii.extensionreadjetpack.R
import org.jetbrains.anko.find
import android.widget.RelativeLayout


class PreviewDialog(internal var mContext: Context,internal var image: Drawable) : Dialog(mContext, R.style.allDialogStyle) {

    init {
        setContentView(R.layout.dialog_preview)

        val dm = DisplayMetrics()
        window!!.windowManager.defaultDisplay.getMetrics(dm)
        val p = this.window!!.attributes
        p.width = dm.widthPixels
        p.height = dm.heightPixels + getStatusBarHeight(mContext) + getDaoHangHeight(mContext)
        p.gravity = Gravity.CENTER
        this.window!!.attributes = p

    }


    fun initData() {
        val photoView = find<PhotoView>(R.id.photoView)
        val layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT)
        photoView.setImageDrawable(image)
        photoView.layoutParams = layoutParams
        photoView.setOnClickListener {
            dismiss()
        }
    }


    override fun show() {
        this.window!!.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        )
        initData()
        super.show()
        fullScreenImmersive(window!!.decorView)
        this.window!!.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
    }

    private fun fullScreenImmersive(view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val uiOptions = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_FULLSCREEN)
            view.systemUiVisibility = uiOptions
        }
    }

    override fun onStop() {
        super.onStop()
    }

    companion object {

        /**
         * 获取状态栏高度
         * @param context
         * @return
         */
        fun getStatusBarHeight(context: Context): Int {
            var result = 0
            val resourceId = context.resources.getIdentifier(
                "status_bar_height", "dimen",
                "android"
            )
            if (resourceId > 0) {
                result = context.resources.getDimensionPixelSize(resourceId)
            }
            return result
        }

        /**
         * 获取导航栏高度
         * @param context
         * @return
         */
        fun getDaoHangHeight(context: Context): Int {
            val result = 0
            var resourceId = 0
            val rid = context.resources.getIdentifier("config_showNavigationBar", "bool", "android")
            if (rid != 0) {
                resourceId = context.resources.getIdentifier("navigation_bar_height", "dimen", "android")
                return context.resources.getDimensionPixelSize(resourceId)
            } else
                return 0
        }
    }

}
