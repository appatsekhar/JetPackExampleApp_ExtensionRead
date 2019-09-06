package com.toeii.extensionreadjetpack.ui.community

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout
import com.toeii.extensionreadjetpack.R
import com.toeii.extensionreadjetpack.base.BaseFragment
import com.toeii.extensionreadjetpack.databinding.FragmentCommunityBinding
import com.toeii.extensionreadjetpack.entity.OpenEyeResult

class CommunityFragment : BaseFragment<FragmentCommunityBinding>(){

    private val mCommunityItemAdapter: CommunityItemAdapter by lazy { CommunityItemAdapter() }
    private val mCommunityContentAdapter: CommunityContentAdapter by lazy { CommunityContentAdapter() }

    private val mViewModel: CommunityViewModel by lazy(LazyThreadSafetyMode.NONE)  {
        ViewModelProviders.of(this,CommunityModelFactory(CommunityRepository()))[CommunityViewModel::class.java]
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_community
    }

    override fun initView(view : View) {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = RecyclerView.HORIZONTAL
        mBinding.recyclerViewHeader.layoutManager = layoutManager
        mBinding.recyclerViewHeader.adapter = mCommunityItemAdapter
    }

    override fun initData() {
        mViewModel.fetchItemResult()
        mViewModel.itemResult.value.let {
            mCommunityItemAdapter.setNewData(it)
            if(!it.isNullOrEmpty()){
                mViewModel.fetchResult(it[0].data.tagId.toString())
                mViewModel.result?.observe(this, Observer<PagedList<OpenEyeResult>>{ newIt ->
                    mCommunityContentAdapter.submitList(newIt)
                })
            }
        }
    }

    override fun initListener() {
        mBinding.pullToRefresh.setOnPullListener(object : QMUIPullRefreshLayout.OnPullListener {
            override fun onMoveTarget(offset: Int) {

            }

            override fun onMoveRefreshView(offset: Int) {

            }

            override fun onRefresh() {
                initData()
                mBinding.pullToRefresh.postDelayed( { mBinding.pullToRefresh.finishRefresh() }, 500)
            }
        })
    }



}


