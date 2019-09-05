package com.toeii.extensionreadjetpack.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.toeii.extensionreadjetpack.interfaces.OnItemClickListener
import com.toeii.extensionreadjetpack.interfaces.OnItemLongClickListener


abstract class BasePagedListAdapter<T, VB : ViewDataBinding>(val callback: DiffUtil.ItemCallback<T>) :
    PagedListAdapter<T, BaseViewHolder<VB>>(callback) {

    private var itemListener: OnItemClickListener? = null
    private var itemLongListener: OnItemLongClickListener? = null

    fun setOnItemListener(listener: OnItemClickListener?) {
        this.itemListener = listener
    }

    fun setOnItemLongListener(listener: OnItemLongClickListener?) {
        this.itemLongListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<VB> {
        return BaseViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), getLayoutId(viewType), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder<VB>, position: Int) {
        val data = getItem(position) ?: return
        setVariable(data, position, holder)
        holder.binding.executePendingBindings()
        holder.binding.root.setOnClickListener { v -> itemListener?.onItemClick(position, v) }
        holder.binding.root.setOnLongClickListener { v ->
            return@setOnLongClickListener itemLongListener?.onItemLongClick(
                position, v
            ) ?: false
        }
    }

    fun getItemData(position: Int): T? = getItem(position)

    abstract fun getLayoutId(viewType: Int): Int

    abstract fun setVariable(data: T, position: Int, holder: BaseViewHolder<VB>)
}
