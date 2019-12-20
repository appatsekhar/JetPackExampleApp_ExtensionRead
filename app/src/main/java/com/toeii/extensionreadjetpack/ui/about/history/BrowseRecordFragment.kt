package com.toeii.extensionreadjetpack.ui.about.history

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qmuiteam.qmui.nestedScroll.QMUIContinuousNestedBottomAreaBehavior
import com.qmuiteam.qmui.nestedScroll.QMUIContinuousNestedBottomRecyclerView
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout
import com.toeii.extensionreadjetpack.ERApplication
import com.toeii.extensionreadjetpack.R
import com.toeii.extensionreadjetpack.base.BaseFragment
import com.toeii.extensionreadjetpack.config.ERAppConfig
import com.toeii.extensionreadjetpack.databinding.FragmentBrowseRecordBinding
import com.toeii.extensionreadjetpack.entity.BrowseRecordEntity
import com.toeii.extensionreadjetpack.interfaces.OnItemClickListener
import com.toeii.extensionreadjetpack.ui.about.web.WebFragment
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.uiThread

class BrowseRecordFragment : BaseFragment<FragmentBrowseRecordBinding>(){

    private val mBrowseRecordAdapter: BrowseRecordAdapter by lazy { BrowseRecordAdapter() }

    private val mRecyclerView: RecyclerView by lazy { QMUIContinuousNestedBottomRecyclerView(context!!) }

    private val mViewModel: BrowseRecordViewModel by lazy(LazyThreadSafetyMode.NONE)  {
        ViewModelProviders.of(this,
            BrowseRecordModelFactory(BrowseRecordRepository())
        )[BrowseRecordViewModel::class.java]
    }

    private lateinit var rightButton: Button

    override fun getLayoutId(): Int {
        return R.layout.fragment_browse_record
    }

    override fun initView(view: View) {

        mBinding.topbar.setTitle(getString(R.string.str_browse_record))
        rightButton = mBinding.topbar.addRightTextButton(getString(R.string.str_delete),R.id.topbar_right_delete)
        rightButton.setTextColor(Color.WHITE)

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

        rightButton.setOnClickListener {
                QMUIDialog.MessageDialogBuilder(activity)
                    .setTitle(getString(R.string.str_hint))
                    .setMessage(getString(R.string.str_re_delete))
                    .addAction(getString(R.string.str_cancel)) { dialog, index -> dialog.dismiss() }
                    .addAction(
                        0, getString(R.string.str_delete), QMUIDialogAction.ACTION_PROP_NEGATIVE
                    ) { dialog, index ->
                        doAsync {
                            ERApplication.db.browseRecordDao().deleteAll()
                            uiThread {
                                dialog.dismiss()
                                Toast.makeText(activity, getString(R.string.str_delete_success), Toast.LENGTH_SHORT).show()
                                initData()
                            }
                        }
                    }
                    .create(com.qmuiteam.qmui.R.style.QMUI_Dialog).show()

            }

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

        mBrowseRecordAdapter.setOnItemListener(OnItemClickListener { position, view ->
            openWebView(mBrowseRecordAdapter.getItemData(position)?.title.toString(),
                mBrowseRecordAdapter.getItemData(position)?.url.toString())
        })

    }


}
