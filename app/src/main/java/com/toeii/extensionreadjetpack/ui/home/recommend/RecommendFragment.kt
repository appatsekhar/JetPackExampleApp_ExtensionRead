package com.toeii.extensionreadjetpack.ui.home.recommend

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.qmuiteam.qmui.nestedScroll.*
import com.qmuiteam.qmui.widget.QMUIRadiusImageView
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout
import com.stx.xhb.androidx.XBanner
import com.toeii.extensionreadjetpack.ERApplication
import com.toeii.extensionreadjetpack.base.BaseFragment
import com.toeii.extensionreadjetpack.databinding.FragmentRecommendBinding
import com.toeii.extensionreadjetpack.entity.RecommendBannerItem
import org.jetbrains.anko.find
import com.toeii.extensionreadjetpack.R
import com.toeii.extensionreadjetpack.config.ERAppConfig
import com.toeii.extensionreadjetpack.entity.ViceResult
import com.toeii.extensionreadjetpack.util.SpacesItemDecoration
import kotlinx.android.synthetic.main.view_list_item_recommend.view.*

class RecommendFragment: BaseFragment<FragmentRecommendBinding>() {

    private lateinit var mCoordinatorScrollHeaderView: View
    private lateinit var mHeadItemTitleText: TextView
    private lateinit var mHeadViewPager: XBanner

    private val mCoordinatorScrollTopLayout: QMUIContinuousNestedTopLinearLayout by lazy { QMUIContinuousNestedTopLinearLayout(context) }
    private val mRecyclerView: RecyclerView by lazy { QMUIContinuousNestedBottomRecyclerView(context!!) }
    private val mRecommendAdapter: RecommendAdapter by lazy { RecommendAdapter(context!!) }
    private val mHeadViewAdapter: XBanner.XBannerAdapter by lazy { getHeadPagerAdapter() }
    private val mHandler: Handler by lazy { Handler() }

    private fun getHeadPagerAdapter(): XBanner.XBannerAdapter {
        return XBanner.XBannerAdapter { _, model, view, _ ->

            val themeCover = view.find<QMUIRadiusImageView>(R.id.theme_cover)
            val themeHeadIcon = view.find<QMUIRadiusImageView>(R.id.theme_icon)
            val themeTitle = view.find<TextView>(R.id.theme_title)
            val themeSubTitle = view.find<TextView>(R.id.theme_subtitle)

            val bannerItem = model as RecommendBannerItem
            Glide.with(context!!) .load(bannerItem.data.cover.detail).into(themeCover)
            Glide.with(context!!) .load(bannerItem.data.author.icon).into(themeHeadIcon)
            themeTitle.text = bannerItem.data.author.name
            themeSubTitle.text = bannerItem.data.author.description
        }
    }

    private val mViewModel: RecommendViewModel by lazy {
        ViewModelProviders.of(this,RecommendModelFactory(RecommendRepository()))[RecommendViewModel::class.java]
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_recommend
    }

    override fun initView(view : View) {

        mCoordinatorScrollHeaderView = LayoutInflater.from(context).inflate(R.layout.view_list_header_recommend,null)
        mHeadItemTitleText = mCoordinatorScrollHeaderView.find(R.id.item_title_text)
        mHeadViewPager = mCoordinatorScrollHeaderView.find(R.id.item_pager)

        mCoordinatorScrollTopLayout.orientation = LinearLayout.VERTICAL
        mCoordinatorScrollTopLayout.addView(mCoordinatorScrollHeaderView)

        val matchParent = ViewGroup.LayoutParams.MATCH_PARENT
        val topLp = CoordinatorLayout.LayoutParams(matchParent, ViewGroup.LayoutParams.WRAP_CONTENT)
        topLp.behavior = QMUIContinuousNestedTopAreaBehavior(context)
        mBinding.coordinator.setTopAreaView(mCoordinatorScrollTopLayout, topLp)

        mRecyclerView.layoutManager = object : LinearLayoutManager(context) {
            override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
                return RecyclerView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }
        val recyclerViewLp = CoordinatorLayout.LayoutParams(matchParent, matchParent)
        recyclerViewLp.behavior = QMUIContinuousNestedBottomAreaBehavior()
        mRecyclerView.adapter = mRecommendAdapter
        mRecyclerView.addItemDecoration(SpacesItemDecoration(10))
        mBinding.coordinator.setBottomAreaView(mRecyclerView, recyclerViewLp)

    }

    override fun initData() {
        mViewModel.fetchBannerResult()
        mViewModel.bannerResult.observe(this, Observer<List<RecommendBannerItem>> {
            val newIt = it.filter { item ->
                item.data.cover != null
            }
            mHeadViewPager.setBannerData(com.toeii.extensionreadjetpack.R.layout.view_vp_item_recommend,newIt)
            mHeadViewPager.loadImage(mHeadViewAdapter)
        })

        fetchRecommendResult()
    }

    override fun initListener() {
        mBinding.pullToRefresh.setOnPullListener(object : QMUIPullRefreshLayout.OnPullListener {
            override fun onMoveTarget(offset: Int) {

            }

            override fun onMoveRefreshView(offset: Int) {

            }

            override fun onRefresh() {
                initData()
            }
        })

    }

    private fun fetchRecommendResult() {
        mViewModel.fetchResult()
        mViewModel.result?.observe(this, Observer<PagedList<ViceResult>> {
            mRecommendAdapter.submitList(it)
            mHandler.postDelayed({ mBinding.pullToRefresh.finishRefresh() }, 500)
        })
    }

}

internal class RecommendAdapter(private var mContext: Context): PagedListAdapter<ViceResult, RecyclerView.ViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<ViceResult>() {
            override fun areItemsTheSame(oldItem: ViceResult, newItem: ViceResult): Boolean =
                oldItem._id == newItem._id

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: ViceResult, newItem: ViceResult): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.view_list_item_recommend,null)
        return RecommendHolder(view)
    }

    override fun onBindViewHolder(helper: RecyclerView.ViewHolder, position: Int) {
        val item= currentList?.get(position)
        if(item?.site != null){
            helper.itemView.group_title_text.text = item.title
            Glide.with(mContext).load(item.cover).error(R.mipmap.icon_error_cover).into(helper.itemView.group_cover)
            Glide.with(mContext).load(item.site.icon).error(R.mipmap.icon_error_cover).into(helper.itemView.theme_icon)
            helper.itemView.theme_title.text = item.site.title
            helper.itemView.theme_subtitle.text = item.site.desc
        }
    }

    class RecommendHolder(itemView: View): RecyclerView.ViewHolder(itemView)

}



