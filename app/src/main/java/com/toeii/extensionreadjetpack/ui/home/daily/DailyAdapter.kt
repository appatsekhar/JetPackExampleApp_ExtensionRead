package com.toeii.extensionreadjetpack.ui.home.daily

import androidx.recyclerview.widget.DiffUtil
import com.toeii.extensionreadjetpack.R
import com.toeii.extensionreadjetpack.base.BasePagedListAdapter
import com.toeii.extensionreadjetpack.base.BaseViewHolder
import com.toeii.extensionreadjetpack.databinding.ViewListItemDailyBinding
import com.toeii.extensionreadjetpack.entity.QdailyResult

class DailyAdapter: BasePagedListAdapter<QdailyResult, ViewListItemDailyBinding>(DIFF_CALLBACK) {

    override fun getLayoutId(viewType: Int): Int = R.layout.view_list_item_daily

    override fun setVariable(data: QdailyResult, position: Int, holder: BaseViewHolder<ViewListItemDailyBinding>) {
        holder.binding.item = data
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<QdailyResult>() {
            override fun areItemsTheSame(oldItem: QdailyResult, newItem: QdailyResult): Boolean =
                oldItem._id == newItem._id

            override fun areContentsTheSame(oldItem: QdailyResult, newItem: QdailyResult): Boolean =
                oldItem == newItem
        }
    }
}