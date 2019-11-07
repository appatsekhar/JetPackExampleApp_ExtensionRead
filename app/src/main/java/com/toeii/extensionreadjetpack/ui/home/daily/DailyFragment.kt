package com.toeii.extensionreadjetpack.ui.home.daily

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout
import com.toeii.extensionreadjetpack.R
import com.toeii.extensionreadjetpack.base.BaseFragment
import com.toeii.extensionreadjetpack.common.CoroutineBus
import com.toeii.extensionreadjetpack.common.SpacesItemDecoration
import com.toeii.extensionreadjetpack.common.UI
import com.toeii.extensionreadjetpack.config.ERAppConfig
import com.toeii.extensionreadjetpack.databinding.FragmentDailyBinding
import com.toeii.extensionreadjetpack.entity.EventMessage
import com.toeii.extensionreadjetpack.entity.HomeDailyItemListBean

class DailyFragment : BaseFragment<FragmentDailyBinding>(){

    private val mDailyAdapter: DailyAdapter by lazy { DailyAdapter() }

    private val mViewModel: DailyViewModel by lazy(LazyThreadSafetyMode.NONE)  {
        ViewModelProviders.of(this,DailyModelFactory(DailyRepository()))[DailyViewModel::class.java]
    }

    override fun getLayoutId(): Int = R.layout.fragment_daily

    override fun initView(view : View) {

        mBinding.emptyView.show(true)

        mBinding.rvCoordinator.layoutManager = LinearLayoutManager(context)
        mBinding.rvCoordinator.isNestedScrollingEnabled = true
        (mBinding.rvCoordinator.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        mBinding.rvCoordinator.adapter = mDailyAdapter
        mBinding.rvCoordinator.addItemDecoration(SpacesItemDecoration(10))

    }

    override fun initData() {
        mViewModel.fetchResult()
        mViewModel.result?.observe(this, Observer<PagedList<HomeDailyItemListBean>>{
            mDailyAdapter.submitList(it)
        })
    }

    override fun initListener() {
        mBinding.pullToRefresh.setOnPullListener(object : QMUIPullRefreshLayout.OnPullListener {
            override fun onMoveTarget(offset: Int) {}

            override fun onMoveRefreshView(offset: Int) {}

            override fun onRefresh() {
                initData()
                mBinding.pullToRefresh.postDelayed( { mBinding.pullToRefresh.finishRefresh() }, 500)
            }
        })

        CoroutineBus.register(this.javaClass.simpleName, UI, EventMessage::class.java) {
            when {
                it.tag == ERAppConfig.HOME_DAILY_PAGE_DATA_INIT ->  mBinding.emptyView.show(false)
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        CoroutineBus.unregister(this.javaClass.simpleName)
    }

}

