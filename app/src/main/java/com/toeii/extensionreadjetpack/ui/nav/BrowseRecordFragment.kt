package com.toeii.extensionreadjetpack.ui.nav

import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qmuiteam.qmui.nestedScroll.QMUIContinuousNestedBottomAreaBehavior
import com.qmuiteam.qmui.nestedScroll.QMUIContinuousNestedBottomRecyclerView
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout
import com.toeii.extensionreadjetpack.R
import com.toeii.extensionreadjetpack.base.BaseFragment
import com.toeii.extensionreadjetpack.databinding.FragmentBrowseRecordBinding
import com.toeii.extensionreadjetpack.entity.BrowseRecordEntity
import org.jetbrains.anko.sdk27.coroutines.onClick

class BrowseRecordFragment : BaseFragment<FragmentBrowseRecordBinding>(){

    private val mBrowseRecordAdapter: BrowseRecordAdapter by lazy { BrowseRecordAdapter() }

    private val mRecyclerView: RecyclerView by lazy { QMUIContinuousNestedBottomRecyclerView(context!!) }

    private val mViewModel: BrowseRecordViewModel by lazy(LazyThreadSafetyMode.NONE)  {
        ViewModelProviders.of(this, BrowseRecordModelFactory(BrowseRecordRepository()))[BrowseRecordViewModel::class.java]
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_browse_record
    }

    override fun initView(view: View) {

        mBinding.topbar.setTitle("浏览记录")

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
        mRecyclerView.adapter = mBrowseRecordAdapter
        mBinding.coordinator.setBottomAreaView(mRecyclerView, recyclerViewLp)

    }

    override fun initData() {
        mViewModel.fetchBrowseRecordResult()
        mViewModel.result?.observe(this, Observer<PagedList<BrowseRecordEntity>>{
            mBrowseRecordAdapter.submitList(it)
        })
    }


    override fun initListener() {

        mBinding.topbar.addLeftBackImageButton().onClick {
            popBackStack()
        }

        mBinding.pullToRefresh.setOnPullListener(object : QMUIPullRefreshLayout.OnPullListener {
            override fun onMoveTarget(offset: Int) {

            }

            override fun onMoveRefreshView(offset: Int) {

            }

            override fun onRefresh() {
                initData()
                mBinding.pullToRefresh.postDelayed( { mBinding.pullToRefresh.finishRefresh() }, 3000)
            }
        })

    }

}
