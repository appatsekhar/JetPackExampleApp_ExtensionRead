package com.toeii.extensionreadjetpack.fragment.about

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import com.toeii.extensionreadjetpack.R
import com.toeii.extensionreadjetpack.base.BaseFragment
import com.toeii.extensionreadjetpack.fragment.nav.BrowseRecordFragment
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.toast
import java.util.ArrayList

class AboutFragment : BaseFragment(){

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAboutAdapter: AboutAdapter

    private val mDatas = ArrayList<String>()

    init {
        for(position in 0..4){
            val str = when (position) {
                0 -> "项目主页"
                1 -> "浏览记录"
                2 -> "切换主题"
                3 -> "关于作者"
                4 -> "清除缓存"
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
        mRecyclerView = view.find(R.id.recyclerView_about)
        mAboutAdapter = AboutAdapter()
        mRecyclerView.adapter =mAboutAdapter
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
                    toast("nav_check_theme")
                    showSingleChoiceDialog()
                }
                3 -> {
                    toast("nav_about_author")
                }
                4 -> {
                    toast("nav_clean_rom")
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