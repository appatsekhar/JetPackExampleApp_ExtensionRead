package com.toeii.extensionreadjetpack.fragment.community

import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.appcompat.widget.AppCompatTextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.qmuiteam.qmui.nestedScroll.*
import com.qmuiteam.qmui.util.QMUIDisplayHelper
import com.qmuiteam.qmui.util.QMUIResHelper
import com.qmuiteam.qmui.widget.QMUIPagerAdapter
import com.qmuiteam.qmui.widget.QMUITabSegment
import com.qmuiteam.qmui.widget.QMUIViewPager
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout
import com.toeii.extensionreadjetpack.R
import com.toeii.extensionreadjetpack.base.BaseFragment
import com.toeii.extensionreadjetpack.entity.Status
import com.toeii.extensionreadjetpack.fragment.home.RecommendAdapter
import com.toeii.extensionreadjetpack.view.AlphaAndScalePageTransformer
import org.jetbrains.anko.find
import java.util.ArrayList

class CommunityFragment : BaseFragment(){


    private lateinit var mPullToRefresh: QMUIPullRefreshLayout
    private lateinit var mCoordinatorScroll: QMUIContinuousNestedScrollLayout
    private lateinit var mTopLinearLayout: QMUIContinuousNestedTopLinearLayout

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mCommunityAdapter: CommunityAdapter

    private lateinit var mHeadRecyclerView: RecyclerView
    private lateinit var mSortAdapter: SortAdapter

    private val mHeadDatas = ArrayList<String>()
    private val mDatas = ArrayList<Status>()

    init {

        for(index in 1..20){
            mHeadDatas.add("")
            mDatas.add(Status())
        }

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_community
    }

    override fun initView(view : View) {
        mHeadRecyclerView = view.find(R.id.recyclerView_header)
        mPullToRefresh = view.find(R.id.pull_to_refresh)
        mCoordinatorScroll = view.find(R.id.coordinator)

        val matchParent = ViewGroup.LayoutParams.MATCH_PARENT

        mTopLinearLayout = QMUIContinuousNestedTopLinearLayout(context)
        mTopLinearLayout.setBackgroundColor(Color.LTGRAY)
        mTopLinearLayout.orientation = LinearLayout.VERTICAL

        mSortAdapter = SortAdapter()
//        mHeadRecyclerView = QMUIContinuousNestedTopRecyclerView(context!!)
        val layoutManager = object : LinearLayoutManager(context) {
            override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
                return RecyclerView.LayoutParams(matchParent,matchParent)
            }
        }
        layoutManager.orientation = RecyclerView.HORIZONTAL
        mHeadRecyclerView.layoutManager = layoutManager
        val topLp = CoordinatorLayout.LayoutParams(matchParent, ViewGroup.LayoutParams.WRAP_CONTENT)
        topLp.behavior = QMUIContinuousNestedTopAreaBehavior(context)
        mHeadRecyclerView.adapter = mSortAdapter

        val textView = TextView(activity)
        textView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        textView.textSize = 24f
        textView.setBackgroundColor(Color.WHITE)
        textView.setTextColor(Color.BLACK)
        textView.text = "text view"
        textView.gravity = Gravity.START
        val padValue = resources.getDimension(R.dimen.space_10).toInt()
        textView.setPadding(padValue,(padValue/2),padValue,(padValue/2))

//        mTopLinearLayout.addView(mHeadRecyclerView)
        mTopLinearLayout.addView(textView)
        mCoordinatorScroll.setTopAreaView(mTopLinearLayout, topLp)

        mCommunityAdapter = CommunityAdapter()
        mRecyclerView = QMUIContinuousNestedBottomRecyclerView(context!!)
        mRecyclerView.layoutManager = object : LinearLayoutManager(context) {
            override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
                return RecyclerView.LayoutParams(matchParent,matchParent)
            }
        }
        val recyclerViewLp = CoordinatorLayout.LayoutParams(matchParent, matchParent)
        recyclerViewLp.behavior = QMUIContinuousNestedBottomAreaBehavior()
        mRecyclerView.adapter = mCommunityAdapter
        mCoordinatorScroll.setBottomAreaView(mRecyclerView, recyclerViewLp)

    }

    override fun initData() {

        mSortAdapter.setNewData(mDatas)
        mCommunityAdapter.setNewData(mDatas)

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



internal class SortAdapter : BaseQuickAdapter<Status, BaseViewHolder>(R.layout.view_list_item_community_sort) {

    override fun convert(helper: BaseViewHolder, item: Status) {
//        Glide.with(mContext) .load(item.userAvatar).into(helper.itemView.theme_icon)
        helper.setText(R.id.community_title, "Hoteis"+helper.layoutPosition)
    }

}


internal class CommunityAdapter : BaseQuickAdapter<Status, BaseViewHolder>(R.layout.view_list_item_community) {

    override fun convert(helper: BaseViewHolder, item: Status) {
//        Glide.with(mContext) .load(item.userAvatar).into(helper.itemView.theme_icon)
//        helper.setText(R.id.theme_title, "Hoteis in Rio de Janeiro")
//        helper.setText(R.id.theme_subtitle, "Hoteis in Rio de JaneiroJaneiroJaneiroJaneiroJaneiroJaneiroJaneiroJaneiroJaneiroJaneiro")
//        helper.setText(R.id.group_title_text, "O ever youthful,O ever weeping")
    }

}
