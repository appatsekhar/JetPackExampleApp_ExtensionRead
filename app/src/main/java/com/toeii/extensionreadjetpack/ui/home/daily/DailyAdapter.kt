package com.toeii.extensionreadjetpack.ui.home.daily

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.toeii.extensionreadjetpack.R
import com.toeii.extensionreadjetpack.base.BasePagedListAdapter
import com.toeii.extensionreadjetpack.base.BaseViewHolder
import com.toeii.extensionreadjetpack.databinding.ViewListItemDailyBinding
import com.toeii.extensionreadjetpack.entity.HomeDailyItemListBean
import java.text.SimpleDateFormat


class DailyAdapter: BasePagedListAdapter<HomeDailyItemListBean, ViewListItemDailyBinding>(DIFF_CALLBACK) {

    override fun getLayoutId(viewType: Int): Int = R.layout.view_list_item_daily

    override fun setVariable(data: HomeDailyItemListBean, position: Int, holder: BaseViewHolder<ViewListItemDailyBinding>) {
        if(data.type == "textCard"){
            holder.binding.rlDailyLayout.layoutParams.height = 0
        }else{
            holder.binding.rlDailyLayout.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            if(data.data.content.data.dataType == "VideoBeanForClient"){
                data.data.header.issuerName = data.data.content.data.author.name
                data.data.header.icon = data.data.content.data.author.icon
                data.data.header.iconType =  data.data.content.data.author.description
            }else if(data.data.content.data.dataType == "UgcPictureBean"){
                var template = SimpleDateFormat("MM-dd HH:mm")
                data.data.header.iconType = "发布于："+template.format(data.data.content.data.owner.releaseDate)
                data.data.header.issuerName = data.data.content.data.owner.nickname
                data.data.header.icon = data.data.content.data.owner.cover.toString()
                data.data.header.iconType = data.data.content.data.owner.description
            }
        }

        holder.binding.item = data
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HomeDailyItemListBean>() {
            override fun areItemsTheSame(oldItem: HomeDailyItemListBean, newItem: HomeDailyItemListBean): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: HomeDailyItemListBean, newItem: HomeDailyItemListBean): Boolean =
                oldItem == newItem
        }
    }
}