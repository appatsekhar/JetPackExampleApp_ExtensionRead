package com.toeii.extensionreadjetpack

import android.os.Bundle
import com.qmuiteam.qmui.arch.annotation.DefaultFirstFragment
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import com.toeii.extensionreadjetpack.base.BaseFragmentActivity
import com.toeii.extensionreadjetpack.fragment.MainFragment

@DefaultFirstFragment(MainFragment::class)
class MainActivity : BaseFragmentActivity() {

    override fun getContextViewId(): Int {
        return R.id.extension_read_id
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if(supportActionBar !=null){
            supportActionBar?.hide()
        }
        super.onCreate(savedInstanceState)
        QMUIStatusBarHelper.setStatusBarLightMode(this)
    }

//    fun createWebExplorerIntent(context: Context, url: String, title: String): Intent {
//        val bundle = Bundle()
//        bundle.putString(EXTRA_URL, url)
//        bundle.putString(EXTRA_TITLE, title)
//        return of(context, QDWebExplorerFragment::class.java, bundle)
//    }

}
