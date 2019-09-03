package com.toeii.extensionreadjetpack.ui.home.recommend

import android.annotation.SuppressLint
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout
import com.toeii.extensionreadjetpack.R
import com.toeii.extensionreadjetpack.base.*
import com.toeii.extensionreadjetpack.entity.ViceResult
import com.toeii.extensionreadjetpack.common.SpacesItemDecoration
import com.toeii.extensionreadjetpack.databinding.*
import com.toeii.extensionreadjetpack.entity.RecommendBannerItem

class RecommendFragment: BaseFragment<FragmentRecommendBinding>() {

//    private val mCoordinatorScrollTopLayout: QMUIContinuousNestedTopLinearLayout by lazy { QMUIContinuousNestedTopLinearLayout(context) }
//    private val mRecyclerView: RecyclerView by lazy { QMUIContinuousNestedBottomRecyclerView(context!!) }
    private val mRecommendAdapter: RecommendAdapter by lazy { RecommendAdapter() }
    private val mHandler: Handler by lazy { Handler() }

    private lateinit var bannerData: List<RecommendBannerItem>

    private val mViewModel: RecommendViewModel by lazy(LazyThreadSafetyMode.NONE)  {
        ViewModelProviders.of(this,RecommendModelFactory(RecommendRepository()))[RecommendViewModel::class.java]
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_recommend
    }

    override fun initView(view : View) {
        //TODO NestedScrollLayout会造成JetPack Paging失效
        /*
            val headView = LayoutInflater.from(activity).inflate(R.layout.view_list_header_recommend,null)
            mHeadBinding = ViewListHeaderRecommendBinding.bind(headView)
            mCoordinatorScrollTopLayout.orientation = LinearLayout.VERTICAL
            mCoordinatorScrollTopLayout.addView(headView)
            val matchParent = ViewGroup.LayoutParams.MATCH_PARENT
            val topLp = CoordinatorLayout.LayoutParams(matchParent, ViewGroup.LayoutParams.WRAP_CONTENT)
            topLp.behavior = QMUIContinuousNestedTopAreaBehavior(context)
            mBinding.coordinator.setTopAreaView(mCoordinatorScrollTopLayout, topLp)

            mBinding.rvCoordinator.layoutManager = object : LinearLayoutManager(context) {
                override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
                    return RecyclerView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                }
            }
            val recyclerViewLp = CoordinatorLayout.LayoutParams(matchParent, matchParent)
            recyclerViewLp.behavior = QMUIContinuousNestedBottomAreaBehavior()
            mBinding.rvCoordinator.adapter = mRecommendAdapter
            mBinding.rvCoordinator.isNestedScrollingEnabled = true
            mBinding.rvCoordinator.addItemDecoration(SpacesItemDecoration(10))
            mBinding.coordinator.setBottomAreaView(mRecyclerView, recyclerViewLp)
        */

        mBinding.rvCoordinator.layoutManager = object : LinearLayoutManager(context) {
            override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
                return RecyclerView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }
        mBinding.rvCoordinator.adapter = mRecommendAdapter
        mBinding.rvCoordinator.isNestedScrollingEnabled = true
        mBinding.rvCoordinator.addItemDecoration(SpacesItemDecoration(10))

    }

    override fun initData() {
        fetchRecommendBannerResult()
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
                mHandler.postDelayed({ mBinding.pullToRefresh.finishRefresh() }, 500)
            }
        })

    }

    private fun fetchRecommendBannerResult() {
        mViewModel.fetchBannerResult()
        mViewModel.bannerResult.observe(this, Observer<List<RecommendBannerItem>> {
            val newIt = it.filter { true }
            bannerData = newIt
        })
    }


    private fun fetchRecommendResult() {
        mViewModel.fetchResult()
        mViewModel.result?.observe(this, Observer<PagedList<ViceResult>> {
            mRecommendAdapter.submitList(it)
//            if(null != mRecommendAdapter.currentList && mRecommendAdapter.currentList?.size!! > 0){
//                mRecommendAdapter.currentList?.get(0)?.bannerData = bannerData
//                mRecommendAdapter.currentList?.add(0,mRecommendAdapter.currentList?.get(0))
//            }
        })
    }

}
