package com.toeii.extensionreadjetpack.ui.community

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.toeii.extensionreadjetpack.R
import com.toeii.extensionreadjetpack.base.BasePagedListAdapterObserver
import com.toeii.extensionreadjetpack.databinding.ViewListItemCommunityBinding
import com.toeii.extensionreadjetpack.databinding.ViewListItemCommunitySortBinding
import com.toeii.extensionreadjetpack.databinding.ViewListItemFooterBinding
import com.toeii.extensionreadjetpack.databinding.ViewListItemHeaderBinding
import com.toeii.extensionreadjetpack.entity.OpenEyeItemResult
import com.toeii.extensionreadjetpack.entity.OpenEyeResult

class CommunityItemAdapter: BaseQuickAdapter<OpenEyeItemResult, BaseViewHolder>(R.layout.view_list_item_community_sort) {

    private lateinit var mBinding: ViewListItemCommunitySortBinding

    override fun convert(helper: BaseViewHolder, item: OpenEyeItemResult) {
        mBinding = initViewBindingImpl(helper.itemView) as ViewListItemCommunitySortBinding
        mBinding.item = item
    }

}

class CommunityContentAdapter : PagedListAdapter<OpenEyeResult, RecyclerView.ViewHolder>(CommunityContentAdapter.diffCallback) {

    internal var isLoadMore = 0

    override fun getItemViewType(position: Int): Int =
        when (position) {
            0 -> ITEM_TYPE_HEADER
            itemCount - 1 -> ITEM_TYPE_FOOTER
            else -> super.getItemViewType(position)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            ITEM_TYPE_HEADER -> HeaderViewHolder(parent)
            ITEM_TYPE_FOOTER -> FooterViewHolder(parent)
            else -> CommunityViewHolder(parent)
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> if(!currentList.isNullOrEmpty()) holder.bindsHeader(currentList!![0])
            is FooterViewHolder -> holder.bindsFooter(isLoadMore)
            is CommunityViewHolder -> holder.bindTo(getDataItem(position))
        }
    }

    private fun getDataItem(position: Int): OpenEyeResult? =
        getItem(position-1)

    override fun getItemCount(): Int =
        super.getItemCount() + 2

    override fun registerAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
        super.registerAdapterDataObserver(BasePagedListAdapterObserver(observer, 1))
    }


    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<OpenEyeResult>() {
            override fun areItemsTheSame(oldItem: OpenEyeResult, newItem: OpenEyeResult): Boolean =
                oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: OpenEyeResult, newItem: OpenEyeResult): Boolean =
                oldItem == newItem
        }

        private const val ITEM_TYPE_HEADER = 99
        private const val ITEM_TYPE_FOOTER = 100
    }
}


class CommunityViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.view_list_item_community, parent, false)) {

    private lateinit var mBinding : ViewListItemCommunityBinding

    fun bindTo(item: OpenEyeResult?) {
        mBinding = initViewBindingImpl(itemView) as ViewListItemCommunityBinding
        if (item != null) {
            mBinding.item = item
        }
    }

}

internal class HeaderViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.view_list_item_header, parent, false)) {

    private lateinit var mHeaderBinding : ViewListItemHeaderBinding

    fun bindsHeader(item: OpenEyeResult?) {
        mHeaderBinding = initViewBindingImpl(itemView) as ViewListItemHeaderBinding
        mHeaderBinding.headerText.visibility = View.GONE
        if (item != null && item.data.content.data.tags.isNotEmpty()) {
            mHeaderBinding.headerText.visibility = View.VISIBLE
            mHeaderBinding.headerText.text = item.data.content.data.tags[0].name
        }
    }


}

internal class FooterViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.view_list_item_footer, parent, false)) {

    private lateinit var mFooterBinding : ViewListItemFooterBinding

    fun bindsFooter(isLoadMore: Int) {
        mFooterBinding = initViewBindingImpl(itemView) as ViewListItemFooterBinding
        when(isLoadMore){
            0 ->  mFooterBinding.tvBanner.text = ""
            1 ->  mFooterBinding.tvBanner.text = mFooterBinding.root.resources.getString(R.string.str_loading)
            -1 ->  mFooterBinding.tvBanner.text = mFooterBinding.root.resources.getString(R.string.str_not_more)
        }
    }

}


private fun initViewBindingImpl(itemView: View): ViewDataBinding? =
    if (null == DataBindingUtil.getBinding(itemView)) {
        DataBindingUtil.bind(itemView)
    } else {
        DataBindingUtil.getBinding(itemView)
    }
