package com.toeii.extensionreadjetpack.fragment.home

import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.qmuiteam.qmui.nestedScroll.*
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout
import com.toeii.extensionreadjetpack.R
import com.toeii.extensionreadjetpack.base.BaseFragment
import com.toeii.extensionreadjetpack.entity.Status
import org.jetbrains.anko.find
import java.util.ArrayList

class DailyFragment : BaseFragment(){
    private lateinit var mPullToRefresh: QMUIPullRefreshLayout
    private lateinit var mCoordinatorScroll: QMUIContinuousNestedScrollLayout

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mDailyAdapter: DailyAdapter

    private val mHeadDatas = ArrayList<String>()
    private val mDatas = ArrayList<Status>()

    init {

        for(index in 1..20){
            mHeadDatas.add("")
            mDatas.add(Status())
        }

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_daily
    }

    override fun initView(view : View) {
        mPullToRefresh = view.find(R.id.pull_to_refresh)
        mCoordinatorScroll = view.find(R.id.coordinator)

        mDailyAdapter = DailyAdapter()
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
        mRecyclerView.adapter = mDailyAdapter
        mCoordinatorScroll.setBottomAreaView(mRecyclerView, recyclerViewLp)

    }

    override fun initData() {

        mDailyAdapter.setNewData(mDatas)

    }

    override fun initListener() {
        mPullToRefresh.setOnPullListener(object : QMUIPullRefreshLayout.OnPullListener {
            override fun onMoveTarget(offset: Int) {

            }

            override fun onMoveRefreshView(offset: Int) {

            }

            override fun onRefresh() {
                //TODO data load
                mPullToRefresh.postDelayed( { mPullToRefresh.finishRefresh() }, 3000)
            }
        })
    }

}


internal class DailyAdapter : BaseQuickAdapter<Status, BaseViewHolder>(R.layout.view_list_item_daily) {

    override fun convert(helper: BaseViewHolder, item: Status) {
//        Glide.with(mContext) .load(item.userAvatar).into(helper.itemView.theme_icon)
//        helper.setText(R.id.theme_title, "Hoteis in Rio de Janeiro")
//        helper.setText(R.id.theme_subtitle, "Hoteis in Rio de JaneiroJaneiroJaneiroJaneiroJaneiroJaneiroJaneiroJaneiroJaneiroJaneiro")
//        helper.setText(R.id.group_title_text, "O ever youthful,O ever weeping")
    }

}
