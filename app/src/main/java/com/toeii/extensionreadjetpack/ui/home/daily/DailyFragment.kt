package com.toeii.extensionreadjetpack.ui.home.daily

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
import com.toeii.extensionreadjetpack.databinding.FragmentDailyBinding
import com.toeii.extensionreadjetpack.entity.HomeDailyEntity
import java.util.ArrayList

class DailyFragment : BaseFragment<FragmentDailyBinding>(){

    private val mRecyclerView: RecyclerView by lazy { QMUIContinuousNestedBottomRecyclerView(context!!) }
    private val mDailyAdapter: DailyAdapter by lazy { DailyAdapter() }

    private val mHeadDatas = ArrayList<String>()
    private val mDatas = ArrayList<HomeDailyEntity>()

    init {

        for(index in 1..20){
            mHeadDatas.add("")
            mDatas.add(HomeDailyEntity(false,0,null,"",0))
        }

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_daily
    }

    override fun initView(view : View) {

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
        mBinding.coordinator.setBottomAreaView(mRecyclerView, recyclerViewLp)

    }

    override fun initData() {

        mDailyAdapter.setNewData(mDatas)

    }

    override fun initListener() {
        mBinding.pullToRefresh.setOnPullListener(object : QMUIPullRefreshLayout.OnPullListener {
            override fun onMoveTarget(offset: Int) {

            }

            override fun onMoveRefreshView(offset: Int) {

            }

            override fun onRefresh() {
                //TODO data load
                mBinding.pullToRefresh.postDelayed( { mBinding.pullToRefresh.finishRefresh() }, 3000)
            }
        })
    }

}


internal class DailyAdapter : BaseQuickAdapter<HomeDailyEntity, BaseViewHolder>(R.layout.view_list_item_daily) {

    override fun convert(helper: BaseViewHolder, item: HomeDailyEntity) {
//        Glide.with(mContext) .load(item.userAvatar).into(helper.itemView.theme_icon)
//        helper.setText(R.id.theme_title, "Hoteis in Rio de Janeiro")
//        helper.setText(R.id.theme_subtitle, "Hoteis in Rio de JaneiroJaneiroJaneiroJaneiroJaneiroJaneiroJaneiroJaneiroJaneiroJaneiro")
//        helper.setText(R.id.group_title_text, "O ever youthful,O ever weeping")
    }

}
