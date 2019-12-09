/*
 * Tencent is pleased to support the open source community by making QMUI_Android available.
 *
 * Copyright (C) 2017-2018 THL A29 Limited, a Tencent company. All rights reserved.
 *
 * Licensed under the MIT License (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.toeii.extensionreadjetpack.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.qmuiteam.qmui.arch.QMUIFragment
import com.toeii.extensionreadjetpack.config.ERAppConfig
import com.toeii.extensionreadjetpack.ui.about.web.WebFragment
import org.jetbrains.anko.contentView

abstract class BaseFragment<VB : ViewDataBinding>  : QMUIFragment(){

    protected lateinit var mBinding: VB

    override fun onCreateView(): View {
        val viewGroup = activity?.contentView as ViewGroup
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), getLayoutId(),viewGroup , false)
        val view = mBinding.root
        initView(view)
        initData()
        initListener()
        return view
    }

    abstract fun getLayoutId():Int
    abstract fun initView(view : View)
    abstract fun initData()
    abstract fun initListener()

    internal fun openWebView(title: String, url: String) {
        val webFragment = WebFragment()
        val bundle = Bundle()
        bundle.putString(ERAppConfig.WEB_EXTRA_TITLE, title)
        bundle.putString(ERAppConfig.WEB_EXTRA_URL, url)
        webFragment.arguments = bundle
        startFragment(webFragment)
    }

}
