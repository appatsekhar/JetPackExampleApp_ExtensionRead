package com.toeii.extensionreadjetpack.fragment.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.qmuiteam.qmui.nestedScroll.*
import com.qmuiteam.qmui.widget.QMUIPagerAdapter
import com.qmuiteam.qmui.widget.QMUIViewPager
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout
import com.toeii.extensionreadjetpack.R
import com.toeii.extensionreadjetpack.base.BaseFragment
import com.toeii.extensionreadjetpack.entity.Status
import com.toeii.extensionreadjetpack.view.AlphaAndScalePageTransformer
import org.jetbrains.anko.find
import java.util.ArrayList


class RecommendFragment : BaseFragment(){

    private lateinit var mPullToRefresh: QMUIPullRefreshLayout
    private lateinit var mCoordinatorScroll: QMUIContinuousNestedScrollLayout
    private lateinit var mCoordinatorScrollTopLayout: QMUIContinuousNestedTopLinearLayout

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mRecommendAdapter: RecommendAdapter
    private lateinit var mCoordinatorScrollHeaderView: View
    private lateinit var mHeadItemTitleText: TextView
    private lateinit var mHeadViewPager: QMUIViewPager
    private lateinit var mHeadViewAdapter: QMUIPagerAdapter

    private val mHeadDatas = ArrayList<String>()
    private val mDatas = ArrayList<Status>()

    init {

        for(index in 1..20){
            mHeadDatas.add("")
            mDatas.add(Status())
        }

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_recommend
    }

    override fun initView(view : View) {
        mPullToRefresh = view.find(R.id.pull_to_refresh)
        mCoordinatorScroll = view.find(R.id.coordinator)

        mCoordinatorScrollHeaderView = LayoutInflater.from(context).inflate(R.layout.view_list_header_recommend,null)
        mHeadItemTitleText = mCoordinatorScrollHeaderView.find(R.id.item_title_text)
        mHeadViewPager = mCoordinatorScrollHeaderView.find(R.id.item_pager)

        mHeadViewPager.setPageTransformer(false, AlphaAndScalePageTransformer())
        mHeadViewPager.infiniteRatio = 3000
        mHeadViewPager.isEnableLoop = true
        mHeadViewAdapter = getHeadPagerAdapter()
        mHeadViewPager.adapter = mHeadViewAdapter
        mHeadViewAdapter.notifyDataSetChanged()

        mCoordinatorScrollTopLayout = QMUIContinuousNestedTopLinearLayout(context)
        mCoordinatorScrollTopLayout.orientation = LinearLayout.VERTICAL
        mCoordinatorScrollTopLayout.addView(mCoordinatorScrollHeaderView)

        val matchParent = ViewGroup.LayoutParams.MATCH_PARENT
        val topLp = CoordinatorLayout.LayoutParams(matchParent, ViewGroup.LayoutParams.WRAP_CONTENT)
        topLp.behavior = QMUIContinuousNestedTopAreaBehavior(context)
        mCoordinatorScroll.setTopAreaView(mCoordinatorScrollTopLayout, topLp)

        mRecommendAdapter = RecommendAdapter()
        mRecyclerView = QMUIContinuousNestedBottomRecyclerView(context!!)
        mRecyclerView.layoutManager = object : LinearLayoutManager(context) {
            override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
                return RecyclerView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
        }
        val recyclerViewLp = CoordinatorLayout.LayoutParams(matchParent, matchParent)
        recyclerViewLp.behavior = QMUIContinuousNestedBottomAreaBehavior()
        mRecyclerView.adapter = mRecommendAdapter
        mCoordinatorScroll.setBottomAreaView(mRecyclerView, recyclerViewLp)

    }

    override fun initData() {

        mRecommendAdapter.setNewData(mDatas)

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


    private fun getHeadPagerAdapter(): QMUIPagerAdapter {

        return object : QMUIPagerAdapter() {

            override fun isViewFromObject(@NonNull view: View, @NonNull `object`: Any): Boolean {
                return view === `object`
            }

            override fun getCount(): Int {
                return if(mHeadDatas.size < 1)  0 else mHeadDatas.size
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return mHeadDatas[position]
            }

            @NonNull
            override fun hydrate(@NonNull container: ViewGroup, position: Int): Any {
                val view = LayoutInflater.from(context).inflate(R.layout.view_vp_item_recommend,null)

                return view
            }

            override fun populate(@NonNull container: ViewGroup, @NonNull item: Any, position: Int) {
                val itemView = item as View
//                view.find<QMUIRadiusImageView>(R.id.theme_cover)
//                view.find<QMUIRadiusImageView>(R.id.theme_icon)
//                view.find<TextView>(R.id.theme_title)
//                view.find<TextView>(R.id.theme_subtitle)
                container.addView(itemView)
            }

            override fun destroy(@NonNull container: ViewGroup, position: Int, @NonNull `object`: Any) {
                container.removeView(`object` as View)
            }
        }
    }

}


internal class RecommendAdapter : BaseQuickAdapter<Status, BaseViewHolder>(R.layout.view_list_item_recommend) {

    override fun convert(helper: BaseViewHolder, item: Status) {
//        Glide.with(mContext) .load(item.userAvatar).into(helper.itemView.theme_icon)
        helper.setText(R.id.theme_title, "Hoteis in Rio de Janeiro")
        helper.setText(R.id.theme_subtitle, "Hoteis in Rio de JaneiroJaneiroJaneiroJaneiroJaneiroJaneiroJaneiroJaneiroJaneiroJaneiro")
        helper.setText(R.id.group_title_text, "O ever youthful,O ever weeping")
    }

}


