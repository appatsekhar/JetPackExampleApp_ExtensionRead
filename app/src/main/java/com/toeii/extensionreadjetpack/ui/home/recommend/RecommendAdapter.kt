package com.toeii.extensionreadjetpack.ui.home.recommend

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.stx.xhb.androidx.XBanner
import com.toeii.extensionreadjetpack.R
import com.toeii.extensionreadjetpack.base.BasePagedListAdapterObserver
import com.toeii.extensionreadjetpack.databinding.ViewListHeaderRecommendBinding
import com.toeii.extensionreadjetpack.databinding.ViewListItemRecommendBinding
import com.toeii.extensionreadjetpack.databinding.ViewVpItemRecommendBinding
import com.toeii.extensionreadjetpack.entity.RecommendBannerItem
import com.toeii.extensionreadjetpack.entity.ViceResult

class RecommendAdapter : PagedListAdapter<ViceResult, RecyclerView.ViewHolder>(diffCallback) {

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> ITEM_TYPE_HEADER
            itemCount - 1 -> ITEM_TYPE_FOOTER
            else -> super.getItemViewType(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_TYPE_HEADER -> HeaderViewHolder(parent)
            ITEM_TYPE_FOOTER -> FooterViewHolder(parent)
            else -> RecommendViewHolder(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> holder.bindsHeader(currentList?.get(0)) //TODO null data
            is FooterViewHolder -> holder.bindsFooter()
            is RecommendViewHolder -> holder.bindTo(getDataItem(position))
        }
    }

    private fun getDataItem(position: Int): ViceResult? {
        return getItem(position-1)
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + 2
    }

    override fun registerAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
        super.registerAdapterDataObserver(BasePagedListAdapterObserver(observer, 1))
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<ViceResult>() {
            override fun areItemsTheSame(oldItem: ViceResult, newItem: ViceResult): Boolean =
                oldItem._id == newItem._id

            override fun areContentsTheSame(oldItem: ViceResult, newItem: ViceResult): Boolean =
                oldItem == newItem
        }

        private const val ITEM_TYPE_HEADER = 99
        private const val ITEM_TYPE_FOOTER = 100
    }
}

class RecommendViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.view_list_item_recommend, parent, false)) {

    private lateinit var mBinding : ViewListItemRecommendBinding
    private var viceResult: ViceResult? = null

    fun bindTo(viceResult: ViceResult?) {
        this.viceResult = viceResult
        mBinding =  if(null == DataBindingUtil.getBinding<ViewListItemRecommendBinding>(itemView)){
            ViewListItemRecommendBinding.bind(itemView)
        }else{
            DataBindingUtil.getBinding<ViewListItemRecommendBinding>(itemView)!!
        }
        mBinding.item = viceResult
    }

}

internal class HeaderViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.view_list_header_recommend, parent, false)) {

    private lateinit var mHeadBinding : ViewListHeaderRecommendBinding
    private lateinit var mVpBinding : ViewVpItemRecommendBinding
    private val mHeadViewAdapter: XBanner.XBannerAdapter by lazy { getHeadPagerAdapter() }

    fun bindsHeader(viceResult: ViceResult?) {
        mHeadBinding = if(null == DataBindingUtil.getBinding<ViewListHeaderRecommendBinding>(itemView)){
            ViewListHeaderRecommendBinding.bind(itemView)
        }else{
            DataBindingUtil.getBinding<ViewListHeaderRecommendBinding>(itemView)!!
        }
        if (viceResult != null) {
            mHeadBinding.itemPager.setBannerData(R.layout.view_vp_item_recommend,viceResult.bannerData)
            mHeadBinding.itemPager.loadImage(mHeadViewAdapter)
        }
    }

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

}

internal class FooterViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.view_list_item_footer, parent, false)) {

    fun bindsFooter() {

    }

}
