package com.toeii.extensionreadjetpack.fragment.nav

import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.qmuiteam.qmui.nestedScroll.QMUIContinuousNestedBottomAreaBehavior
import com.qmuiteam.qmui.nestedScroll.QMUIContinuousNestedBottomRecyclerView
import com.qmuiteam.qmui.nestedScroll.QMUIContinuousNestedScrollLayout
import com.qmuiteam.qmui.widget.QMUITopBar
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout
import com.toeii.extensionreadjetpack.R
import com.toeii.extensionreadjetpack.base.BaseFragment
import com.toeii.extensionreadjetpack.entity.BrowseRecordEntity
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.util.ArrayList

class BrowseRecordFragment : BaseFragment(){

    private lateinit var mTopBar: QMUITopBar
    private lateinit var mPullToRefresh: QMUIPullRefreshLayout
    private lateinit var mCoordinatorScroll: QMUIContinuousNestedScrollLayout

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mBrowseRecordAdapter: BrowseRecordAdapter

    private val mDatas = ArrayList<BrowseRecordEntity>()

    init {

        for(index in 1..20){
            mDatas.add(BrowseRecordEntity())
        }

    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_browse_record
    }

    override fun initView(view: View) {

        mTopBar = view.find(R.id.topbar)
        mPullToRefresh = view.find(R.id.pull_to_refresh)
        mCoordinatorScroll = view.find(R.id.coordinator)

        mTopBar.setTitle("浏览记录")

        mBrowseRecordAdapter = BrowseRecordAdapter()
        mRecyclerView = QMUIContinuousNestedBottomRecyclerView(context!!)
        mRecyclerView.layoutManager = object : LinearLayoutManager(context) {
            override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
                return RecyclerView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
        }
        val recyclerViewLp = CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        recyclerViewLp.behavior = QMUIContinuousNestedBottomAreaBehavior()
        mRecyclerView.adapter = mBrowseRecordAdapter
        mCoordinatorScroll.setBottomAreaView(mRecyclerView, recyclerViewLp)

    }

    override fun initData() {

        mBrowseRecordAdapter.setNewData(mDatas)

    }


    override fun initListener() {

        mTopBar.addLeftBackImageButton().onClick {
            popBackStack()
        }

    }

}

internal class BrowseRecordAdapter : BaseQuickAdapter<BrowseRecordEntity, BaseViewHolder>(R.layout.view_list_item_browse_record) {

    override fun convert(helper: BaseViewHolder, item: BrowseRecordEntity) {
//        Glide.with(mContext) .load(item.userAvatar).into(helper.itemView.theme_icon)
        helper.setText(R.id.title, "Hoteis in Rio de Janeiro")
        helper.setText(R.id.subtitle, "Hoteis in Rio de JaneiroJaneiroJaneiroJaneiroJaneiroJaneiroJaneiroJaneiroJaneiroJaneiro")
    }

}