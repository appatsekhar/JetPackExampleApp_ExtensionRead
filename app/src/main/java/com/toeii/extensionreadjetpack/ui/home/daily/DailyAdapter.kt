package com.toeii.extensionreadjetpack.ui.home.daily

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.room.ColumnInfo
import com.toeii.extensionreadjetpack.ERApplication
import com.toeii.extensionreadjetpack.R
import com.toeii.extensionreadjetpack.base.BaseFragment
import com.toeii.extensionreadjetpack.base.BasePagedListAdapterObserver
import com.toeii.extensionreadjetpack.common.db.BrowseRecordBean
import com.toeii.extensionreadjetpack.databinding.*
import com.toeii.extensionreadjetpack.entity.HomeDailyItemListBean
import com.toeii.extensionreadjetpack.interfaces.OnItemClickListener
import org.jetbrains.anko.doAsync

class DailyAdapter : PagedListAdapter<HomeDailyItemListBean, RecyclerView.ViewHolder>(diffCallback) {

    internal var isLoadMore = 0
    private lateinit var itemListener: OnItemClickListener

    fun setOnItemListener(listener: OnItemClickListener) {
        this.itemListener = listener
    }

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
            else -> DailyViewHolder(parent)
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> if(!currentList.isNullOrEmpty()) holder.bindsHeader(currentList!![0])
            is FooterViewHolder -> holder.bindsFooter(isLoadMore)
            is DailyViewHolder -> getDataItem(position)?.let { holder.bindTo(it,itemListener) }
        }
    }

    private fun getDataItem(position: Int): HomeDailyItemListBean? =
        getItem(position-1)

    override fun getItemCount(): Int =
        super.getItemCount() + 2

    override fun registerAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
        super.registerAdapterDataObserver(BasePagedListAdapterObserver(observer, 1))
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<HomeDailyItemListBean>() {
            override fun areItemsTheSame(oldItem: HomeDailyItemListBean, newItem: HomeDailyItemListBean): Boolean =
                oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: HomeDailyItemListBean, newItem: HomeDailyItemListBean): Boolean =
                oldItem == newItem
        }

        private const val ITEM_TYPE_HEADER = 99
        private const val ITEM_TYPE_FOOTER = 100

    }
}

class DailyViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.view_list_item_daily, parent, false)) {

    private lateinit var mBinding : ViewListItemDailyBinding

    fun bindTo(data: HomeDailyItemListBean,listener: OnItemClickListener) {

        mBinding = initViewBindingImpl(itemView) as ViewListItemDailyBinding

        if(data.type == "textCard"){
            mBinding.rlDailyLayout.layoutParams.height = 0
        }else{
            mBinding.rlDailyLayout.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            data.data.header.iconType = mBinding.root.resources.getString(R.string.str_release)+"："
            data.data.header.issuerName = data.data.content.data.author.name
            data.data.header.icon = data.data.content.data.author.icon
            data.data.header.iconType = data.data.content.data.author.description
        }
        mBinding.item = data

        mBinding.rlDailyLayout.setOnClickListener {
            listener.onItemClick(layoutPosition,it)
        }

    }

}

internal class HeaderViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.view_list_item_header, parent, false)) {

    private lateinit var mHeaderBinding : ViewListItemHeaderBinding

    fun bindsHeader(item: HomeDailyItemListBean?) {
        mHeaderBinding = initViewBindingImpl(itemView) as ViewListItemHeaderBinding
        mHeaderBinding.headerText.visibility = View.GONE
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
