package com.toeii.extensionreadjetpack.fragment.about

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.toeii.extensionreadjetpack.R
import com.toeii.extensionreadjetpack.base.BaseFragment
import org.jetbrains.anko.find
import java.util.ArrayList

class AboutFragment : BaseFragment(){

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAboutAdapter: AboutAdapter

    private val mDatas = ArrayList<String>()

    init {
        for(position in 1..5){
            val str = when (position) {
                1 -> "项目主页"
                2 -> "浏览记录"
                3 -> "切换主题"
                4 -> "关于作者"
                5 -> "清除缓存"
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

    }

}


internal class AboutAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.view_list_item_about) {

    override fun convert(helper: BaseViewHolder, item: String) {
        helper.setText(R.id.about_item_text, item)
    }

}