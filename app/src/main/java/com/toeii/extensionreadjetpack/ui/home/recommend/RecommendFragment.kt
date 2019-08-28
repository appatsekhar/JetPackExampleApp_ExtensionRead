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
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.qmuiteam.qmui.nestedScroll.*
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout
import com.stx.xhb.androidx.XBanner
import com.toeii.extensionreadjetpack.base.BaseFragment
import com.toeii.extensionreadjetpack.databinding.FragmentRecommendBinding
import com.toeii.extensionreadjetpack.entity.RecommendBannerItem
import com.toeii.extensionreadjetpack.R
import com.toeii.extensionreadjetpack.base.BaseViewHolder
import com.toeii.extensionreadjetpack.databinding.ViewListHeaderRecommendBinding
import com.toeii.extensionreadjetpack.databinding.ViewListItemRecommendBinding
import com.toeii.extensionreadjetpack.databinding.ViewVpItemRecommendBinding
import com.toeii.extensionreadjetpack.entity.ViceResult
import com.toeii.extensionreadjetpack.util.SpacesItemDecoration

class RecommendFragment: BaseFragment<FragmentRecommendBinding>() {

    private val mCoordinatorScrollTopLayout: QMUIContinuousNestedTopLinearLayout by lazy { QMUIContinuousNestedTopLinearLayout(context) }
    private val mRecyclerView: RecyclerView by lazy { QMUIContinuousNestedBottomRecyclerView(context!!) }
    private val mRecommendAdapter: RecommendAdapter by lazy { RecommendAdapter(context!!) }
    private val mHeadViewAdapter: XBanner.XBannerAdapter by lazy { getHeadPagerAdapter() }
    private val mHandler: Handler by lazy { Handler() }

    private lateinit var mHeadBinding : ViewListHeaderRecommendBinding
    private lateinit var mVpBinding : ViewVpItemRecommendBinding

    private fun getHeadPagerAdapter(): XBanner.XBannerAdapter {
        return XBanner.XBannerAdapter { _, model, view, _ ->
            mVpBinding = if(null == DataBindingUtil.getBinding<ViewVpItemRecommendBinding>(view)){
                ViewVpItemRecommendBinding.bind(view)
            }else{
                DataBindingUtil.getBinding<ViewVpItemRecommendBinding>(view)!!
            }
            val bannerItem = model as RecommendBannerItem
            mVpBinding.bannerItem = bannerItem
        }
    }

    private val mViewModel: RecommendViewModel by lazy(LazyThreadSafetyMode.NONE)  {
        ViewModelProviders.of(this,RecommendModelFactory(RecommendRepository()))[RecommendViewModel::class.java]
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_recommend
    }

    override fun initView(view : View) {

        val headView = LayoutInflater.from(activity).inflate(R.layout.view_list_header_recommend,null)
        mHeadBinding = ViewListHeaderRecommendBinding.bind(headView)
        mCoordinatorScrollTopLayout.orientation = LinearLayout.VERTICAL
        mCoordinatorScrollTopLayout.addView(headView)
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
            mHeadBinding.itemPager.setBannerData(R.layout.view_vp_item_recommend,newIt)
            mHeadBinding.itemPager.loadImage(mHeadViewAdapter)
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

internal class RecommendAdapter(private var mContext: Context): PagedListAdapter<ViceResult, BaseViewHolder<ViewListItemRecommendBinding>>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<ViceResult>() {
            override fun areItemsTheSame(oldItem: ViceResult, newItem: ViceResult): Boolean =
                oldItem._id == newItem._id

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: ViceResult, newItem: ViceResult): Boolean =
                oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ViewListItemRecommendBinding>, position: Int) {
        val item= currentList?.get(position)
        if(item?.site != null){
            holder.binding.item = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ViewListItemRecommendBinding> {
        val view = LayoutInflater.from(mContext).inflate(R.layout.view_list_item_recommend,parent,false)
        val bindView = ViewListItemRecommendBinding.bind(view)
        return RecommendHolder(bindView)
    }

    class RecommendHolder(itemView: ViewListItemRecommendBinding):BaseViewHolder<ViewListItemRecommendBinding>(itemView)

}

