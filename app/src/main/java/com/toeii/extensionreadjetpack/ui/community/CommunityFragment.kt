package com.toeii.extensionreadjetpack.ui.community

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.qmuiteam.qmui.nestedScroll.*
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout
import com.toeii.extensionreadjetpack.R
import com.toeii.extensionreadjetpack.base.BaseFragment
import com.toeii.extensionreadjetpack.databinding.FragmentCommunityBinding
import com.toeii.extensionreadjetpack.entity.CommunityContentEntity
import java.util.ArrayList

class CommunityFragment : BaseFragment<FragmentCommunityBinding>(){


    private val mTopLinearLayout: QMUIContinuousNestedTopLinearLayout by lazy { QMUIContinuousNestedTopLinearLayout(context) }
    private val mRecyclerView: RecyclerView by lazy { QMUIContinuousNestedBottomRecyclerView(context!!) }

    private val mCommunityAdapter: CommunityAdapter by lazy { CommunityAdapter() }
    private val mSortAdapter: SortAdapter by lazy { SortAdapter() }

    private val mHeadDatas = ArrayList<String>()
    private val mDatas = ArrayList<CommunityContentEntity>()

    init {

        for(index in 1..20){
            mHeadDatas.add("")
            mDatas.add(CommunityContentEntity(false,0,null,"",0))
        }

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_community
    }

    override fun initView(view : View) {

        val matchParent = ViewGroup.LayoutParams.MATCH_PARENT

        mTopLinearLayout.setBackgroundColor(Color.LTGRAY)
        mTopLinearLayout.orientation = LinearLayout.VERTICAL

        val layoutManager = object : LinearLayoutManager(context) {
            override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
                return RecyclerView.LayoutParams(matchParent,matchParent)
            }
        }
        layoutManager.orientation = RecyclerView.HORIZONTAL
        mBinding.recyclerViewHeader.layoutManager = layoutManager
        val topLp = CoordinatorLayout.LayoutParams(matchParent, ViewGroup.LayoutParams.WRAP_CONTENT)
        topLp.behavior = QMUIContinuousNestedTopAreaBehavior(context)
        mBinding.recyclerViewHeader.adapter = mSortAdapter

        val textView = TextView(activity)
        textView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        textView.textSize = 24f
        textView.setBackgroundColor(Color.WHITE)
        textView.setTextColor(Color.BLACK)
        textView.text = "text view"
        textView.gravity = Gravity.START
        val padValue = resources.getDimension(R.dimen.space_10).toInt()
        textView.setPadding(padValue,(padValue/2),padValue,(padValue/2))
        mTopLinearLayout.addView(textView)
        mBinding.coordinator.setTopAreaView(mTopLinearLayout, topLp)

        mRecyclerView.layoutManager = object : LinearLayoutManager(context) {
            override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
                return RecyclerView.LayoutParams(matchParent,matchParent)
            }
        }
        val recyclerViewLp = CoordinatorLayout.LayoutParams(matchParent, matchParent)
        recyclerViewLp.behavior = QMUIContinuousNestedBottomAreaBehavior()
        mRecyclerView.adapter = mCommunityAdapter
        mBinding.coordinator.setBottomAreaView(mRecyclerView, recyclerViewLp)

    }

    override fun initData() {

        mSortAdapter.setNewData(mDatas)
        mCommunityAdapter.setNewData(mDatas)

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



internal class SortAdapter : BaseQuickAdapter<CommunityContentEntity, BaseViewHolder>(R.layout.view_list_item_community_sort) {

    override fun convert(helper: BaseViewHolder, item: CommunityContentEntity) {
//        Glide.with(mContext) .load(item.userAvatar).into(helper.itemView.theme_icon)
        helper.setText(R.id.community_title, "Hoteis"+helper.layoutPosition)
    }

}


internal class CommunityAdapter : BaseQuickAdapter<CommunityContentEntity, BaseViewHolder>(R.layout.view_list_item_community) {

    override fun convert(helper: BaseViewHolder, item: CommunityContentEntity) {
//        Glide.with(mContext) .load(item.userAvatar).into(helper.itemView.theme_icon)
//        helper.setText(R.id.theme_title, "Hoteis in Rio de Janeiro")
//        helper.setText(R.id.theme_subtitle, "Hoteis in Rio de JaneiroJaneiroJaneiroJaneiroJaneiroJaneiroJaneiroJaneiroJaneiroJaneiro")
//        helper.setText(R.id.group_title_text, "O ever youthful,O ever weeping")
    }

}
