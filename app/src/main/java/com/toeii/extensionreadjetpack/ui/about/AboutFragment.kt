package com.toeii.extensionreadjetpack.ui.about

import android.view.View
import android.widget.Toast
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import com.toeii.extensionreadjetpack.R
import com.toeii.extensionreadjetpack.base.BaseFragment
import com.toeii.extensionreadjetpack.databinding.FragmentAboutBinding
import com.toeii.extensionreadjetpack.ui.about.history.BrowseRecordFragment
import org.jetbrains.anko.support.v4.toast
import java.util.ArrayList

class AboutFragment : BaseFragment<FragmentAboutBinding>(){

    private val mAboutAdapter: AboutAdapter by lazy { AboutAdapter() }

    private val mDatas = ArrayList<String>()

    init {
        for(position in 0..2){
            val str = when (position) {
                0 -> "项目主页"
                1 -> "浏览记录"
                2 -> "关于作者"
                else -> null
            }
            if(null != str){
                mDatas.add(str)
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_about
    }

    override fun initView(view : View) {
        mBinding.recyclerViewAbout.adapter =mAboutAdapter
    }

    override fun initData() {
        mAboutAdapter.setNewData(mDatas)
    }


    override fun initListener() {
        mAboutAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { _, _, position ->
            when (position) {
                0 -> {
                    toast("nav_project_main")
                }
                1 -> {
                    toast("nav_browse_record")
                    startFragment(BrowseRecordFragment())
                }
                2 -> {
                    toast("nav_about_author")
                }
            }
        }

    }

    private fun showSingleChoiceDialog() {
        val items = arrayOf("默认主题", "主题1", "主题2", "主题3", "主题4", "主题5", "主题6")
        val checkedIndex = 0
        QMUIDialog.CheckableDialogBuilder(activity)
            .setCheckedIndex(checkedIndex)
            .addItems(items) { dialog, which ->
                Toast.makeText(activity, "你选择了 " + items[which], Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            .create(com.qmuiteam.qmui.R.style.QMUI_Dialog).show()
    }


}


internal class AboutAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.view_list_item_about) {

    override fun convert(helper: BaseViewHolder, item: String) {
        helper.setText(R.id.about_item_text, item)
    }

}