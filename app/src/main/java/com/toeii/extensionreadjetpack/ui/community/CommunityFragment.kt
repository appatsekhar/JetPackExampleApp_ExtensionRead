package com.toeii.extensionreadjetpack.ui.community

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout
import com.toeii.extensionreadjetpack.ERApplication
import com.toeii.extensionreadjetpack.R
import com.toeii.extensionreadjetpack.base.BaseFragment
import com.toeii.extensionreadjetpack.common.CoroutineBus
import com.toeii.extensionreadjetpack.common.SpacesItemDecoration
import com.toeii.extensionreadjetpack.common.UI
import com.toeii.extensionreadjetpack.config.ERAppConfig
import com.toeii.extensionreadjetpack.databinding.FragmentCommunityBinding
import com.toeii.extensionreadjetpack.entity.EventMessage
import com.toeii.extensionreadjetpack.entity.OpenEyeItemResult
import com.toeii.extensionreadjetpack.entity.OpenEyeResult
import com.toeii.extensionreadjetpack.interfaces.OnItemClickListener
import org.jetbrains.anko.debug

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

        mBinding.emptyView.show(true)

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = RecyclerView.HORIZONTAL
        mBinding.recyclerViewHeader.layoutManager = layoutManager
        mBinding.recyclerViewHeader.adapter = mCommunityItemAdapter

        mBinding.rvCoordinator.layoutManager = LinearLayoutManager(context)
        mBinding.rvCoordinator.isNestedScrollingEnabled = true
        (mBinding.rvCoordinator.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        mBinding.rvCoordinator.adapter = mCommunityContentAdapter
        mBinding.rvCoordinator.addItemDecoration(SpacesItemDecoration(10))
    }

    override fun initData() {
        mViewModel.fetchItemResult()
        mViewModel.itemResult.observe(this, Observer<List<OpenEyeItemResult>>{
            mCommunityItemAdapter.setNewData(it)
            if(!it.isNullOrEmpty()){
                mViewModel.fetchResult(it[0].data.tagId.toString())
                mViewModel.result?.observe(this, Observer<PagedList<OpenEyeResult>>{ newIt ->
                    mCommunityContentAdapter.submitList(newIt)
                })
            }
        })
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

        CoroutineBus.register(this.javaClass.simpleName, UI, EventMessage::class.java) {
            when {
                it.tag == CommunityFragment::class.java.name + ERAppConfig.PAGE_DATA_INIT ->
                    mBinding.emptyView.show(false)
                it.tag == CommunityFragment::class.java.name + ERAppConfig.PAGE_DATA_LOAD_START ->
                    mCommunityContentAdapter.isLoadMore = 1
                it.tag == CommunityFragment::class.java.name + ERAppConfig.PAGE_DATA_LOAD_END ->
                    mCommunityContentAdapter.isLoadMore = -1
            }
            mCommunityContentAdapter.notifyDataSetChanged()
        }

        mCommunityItemAdapter.setOnItemClickListener { adapter, view, position ->
            if(null != adapter.data[position]){
                mBinding.emptyView.show(true)
                val itemBean = adapter.data[position] as OpenEyeItemResult
                mViewModel.fetchResult(itemBean.data.tagId.toString())
                mViewModel.result?.observe(this, Observer<PagedList<OpenEyeResult>>{
                    mCommunityContentAdapter.submitList(it)
                })
            }
        }


    }



}


