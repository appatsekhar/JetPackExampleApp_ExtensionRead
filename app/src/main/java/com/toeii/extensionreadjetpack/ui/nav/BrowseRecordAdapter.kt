package com.toeii.extensionreadjetpack.ui.nav

import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import com.toeii.extensionreadjetpack.R
import com.toeii.extensionreadjetpack.base.BasePagedListAdapter
import com.toeii.extensionreadjetpack.base.BaseViewHolder
import com.toeii.extensionreadjetpack.entity.BrowseRecordEntity
import com.toeii.extensionreadjetpack.databinding.ViewListItemBrowseRecordBinding

/**
 * @author Toeii
 * @create 2019/12/5
 * @Describe
 */
class BrowseRecordAdapter : BasePagedListAdapter<BrowseRecordEntity, ViewListItemBrowseRecordBinding>(DIFF_CALLBACK) {

    override fun getLayoutId(viewType: Int): Int = R.layout.view_list_item_browse_record

    override fun setVariable(data: BrowseRecordEntity, position: Int, holder: BaseViewHolder<ViewListItemBrowseRecordBinding>) {
        holder.binding.item = data
        holder.binding.root.setOnClickListener {
            //TODO skip
            Toast.makeText(holder.itemView.context, "index--->$position \n 访问地址--->${data.url}", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<BrowseRecordEntity>() {
            override fun areItemsTheSame(oldItem: BrowseRecordEntity, newItem: BrowseRecordEntity): Boolean =
                oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: BrowseRecordEntity, newItem: BrowseRecordEntity): Boolean =
                oldItem == newItem
        }
    }

}
