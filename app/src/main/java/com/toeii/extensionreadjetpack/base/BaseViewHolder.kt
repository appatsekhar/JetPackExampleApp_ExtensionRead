package com.toeii.extensionreadjetpack.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

open class BaseViewHolder<VB : ViewDataBinding>(open val binding: VB) : RecyclerView.ViewHolder(binding.root)