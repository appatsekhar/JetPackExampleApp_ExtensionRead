package com.toeii.extensionreadjetpack.ui.home.recommend

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout
import com.toeii.extensionreadjetpack.base.*
import com.toeii.extensionreadjetpack.common.CoroutineBus
import com.toeii.extensionreadjetpack.ui.widgets.SpacesItemDecoration
import com.toeii.extensionreadjetpack.common.UI
import com.toeii.extensionreadjetpack.config.ERAppConfig
import com.toeii.extensionreadjetpack.databinding.*
import com.toeii.extensionreadjetpack.entity.EventMessage
import androidx.recyclerview.widget.SimpleItemAnimator
import com.toeii.extensionreadjetpack.ERApplication
import com.toeii.extensionreadjetpack.entity.HomeRecommendItemListBean
import com.toeii.extensionreadjetpack.R
import com.toeii.extensionreadjetpack.common.db.BrowseRecordBean
import com.toeii.extensionreadjetpack.interfaces.OnItemClickListener
import org.jetbrains.anko.doAsync


class RecommendFragment: BaseFragment<FragmentRecommendBinding>() {

    private val mRecommendAdapter: RecommendAdapter by lazy { RecommendAdapter() }

    private val mViewModel: RecommendViewModel by lazy(LazyThreadSafetyMode.NONE)  {
        ViewModelProviders.of(this,RecommendModelFactory(RecommendRepository()))[RecommendViewModel::class.java]
    }

    override fun getLayoutId(): Int = R.layout.fragment_recommend

    override fun initView(view : View) {
        //TODO NestedScrollLayout会造成Paging失效
        /*
            val headView = LayoutInflater.from(activity).inflate(R.layout.view_list_header_recommend,null)
            mHeadBinding = ViewListHeaderRecommendBinding.bind(headView)
            mCoordinatorScrollTopLayout.orientation = LinearLayout.VERTICAL
            mCoordinatorScrollTopLayout.addView(headView)
            val matchParent = ViewGroup.LayoutParams.MATCH_PARENT
            val topLp = CoordinatorLayout.LayoutParams(matchParent, ViewGroup.LayoutParams.WRAP_CONTENT)
            topLp.behavior = QMUIContinuousNestedTopAreaBehavior(context)
            mBinding.coordinator.setTopAreaView(mCoordinatorScrollTopLayout, topLp)
            mBinding.rvCoordinator.layoutManager = LinearLayoutManager(context)
            val recyclerViewLp = CoordinatorLayout.LayoutParams(matchParent, matchParent)
            recyclerViewLp.behavior = QMUIContinuousNestedBottomAreaBehavior()
            mBinding.rvCoordinator.adapter = mRecommendAdapter
            mBinding.rvCoordinator.isNestedScrollingEnabled = true
            mBinding.rvCoordinator.addItemDecoration(SpacesItemDecoration(10))
            mBinding.coordinator.setBottomAreaView(mRecyclerView, recyclerViewLp)
        */

        mBinding.emptyView.show(true)

        mBinding.rvCoordinator.layoutManager = LinearLayoutManager(context)
        mBinding.rvCoordinator.isNestedScrollingEnabled = true
        (mBinding.rvCoordinator.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        mBinding.rvCoordinator.adapter = mRecommendAdapter
        mBinding.rvCoordinator.addItemDecoration(SpacesItemDecoration(10))

    }

    override fun initData() {
        fetchRecommendResult()
    }

    override fun initListener() {

        mRecommendAdapter.setOnItemListener(OnItemClickListener { position, view ->
            val webTitle :String
            val webUrl :String
            val data = mRecommendAdapter.currentList!![(position-1)]
            if (data != null) {
                val browseRecordBean: BrowseRecordBean = when {
                    data.type == "videoSmallCard" -> {
                        webTitle = data.data.title
                        webUrl = data.data.webUrl.raw
                        BrowseRecordBean(
                            data.id.toString(),
                            data.data.title, data.data.description,
                            data.data.webUrl.raw, data.data.cover.feed
                        )
                    }else -> {
                        webTitle = data.data.content.data.title
                        webUrl = data.data.content.data.webUrl.raw
                        BrowseRecordBean(
                            data.id.toString(),
                            data.data.content.data.title, data.data.content.data.description,
                            data.data.content.data.webUrl.raw, data.data.content.data.cover.feed
                        )
                    }
                }

                openWebView(webTitle,webUrl)

                doAsync {
                    ERApplication.db.browseRecordDao().insert(browseRecordBean)
                }
            }

        })

        mRecommendAdapter.setOnBannerItemListener(OnItemClickListener { position, view ->
            val data = mRecommendAdapter.currentList!![0]!!.bannerData[position]
            openWebView(data.data.title,data.data.webUrl.raw)

            val browseRecordBean = BrowseRecordBean(
                data.id.toString(),
                data.data.title, data.data.description,
                data.data.webUrl.raw, data.data.cover.feed
            )
            doAsync {
                ERApplication.db.browseRecordDao().insert(browseRecordBean)
            }
        })

        mBinding.pullToRefresh.setOnPullListener(object : QMUIPullRefreshLayout.OnPullListener {
            override fun onMoveTarget(offset: Int) {}

            override fun onMoveRefreshView(offset: Int) {}

            override fun onRefresh() {
                initData()
                mBinding.pullToRefresh.postDelayed({ mBinding.pullToRefresh.finishRefresh() }, 500)
            }
        })

        CoroutineBus.register(this.javaClass.simpleName, UI, EventMessage::class.java) {
            when {
                it.tag == RecommendFragment::class.java.name + ERAppConfig.PAGE_DATA_INIT ->
                    mBinding.emptyView.show(false)
                it.tag == RecommendFragment::class.java.name + ERAppConfig.PAGE_DATA_LOAD_START ->
                    mRecommendAdapter.isLoadMore = 1
                it.tag == RecommendFragment::class.java.name + ERAppConfig.PAGE_DATA_LOAD_END ->
                    mRecommendAdapter.isLoadMore = -1
            }
            mRecommendAdapter.notifyDataSetChanged()
        }

    }

    private fun fetchRecommendResult() {
        mViewModel.fetchResult()
        mViewModel.result?.observe(this, Observer<PagedList<HomeRecommendItemListBean>>{
            mRecommendAdapter.submitList(it)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        CoroutineBus.unregister(this.javaClass.simpleName)
    }

}
