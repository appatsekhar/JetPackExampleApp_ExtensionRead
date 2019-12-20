package com.toeii.extensionreadjetpack.ui

import android.content.Intent
import android.view.KeyEvent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.PagerAdapter
import com.qmuiteam.qmui.arch.QMUIFragment
import com.qmuiteam.qmui.arch.QMUIFragmentPagerAdapter
import com.qmuiteam.qmui.util.QMUIResHelper
import com.qmuiteam.qmui.widget.QMUITabSegment
import com.toeii.extensionreadjetpack.R
import com.toeii.extensionreadjetpack.base.BaseFragment
import com.toeii.extensionreadjetpack.common.utils.DrawableTintHelper
import com.toeii.extensionreadjetpack.databinding.FragmentMainBinding
import com.toeii.extensionreadjetpack.ui.about.AboutFragment
import com.toeii.extensionreadjetpack.ui.community.CommunityFragment
import com.toeii.extensionreadjetpack.ui.home.HomeFragment
import java.util.HashMap

class MainFragment : BaseFragment<FragmentMainBinding>(){

    private val mPagerAdapter: PagerAdapter by lazy { getPagerAdapter() }
    private val mPages: HashMap<Pager, QMUIFragment> by lazy { HashMap<Pager, QMUIFragment>() }

    override fun getLayoutId(): Int {
        return R.layout.fragment_main
    }

    override fun initView(view: View) {

        //initTab
        mBinding.mainTabs.setDefaultNormalColor(QMUIResHelper.getAttrColor(activity, R.attr.qmui_config_color_gray_6))
        mBinding.mainTabs.setDefaultSelectedColor(resources.getColor(R.color.app_color))

        for(position in 1..3){
            val tab = when (position) {
                1 ->QMUITabSegment.Tab(
                    ContextCompat.getDrawable(baseFragmentActivity, R.mipmap.icon_tabbar_home),
                    ContextCompat.getDrawable(baseFragmentActivity, R.mipmap.icon_tabbar_home_selected),//TODO icon无法动态着色，QMUITabSegment控件使用的是TextView。可以自定义QMUITabSegment，改为ImageView + TextView。
                    resources.getString(R.string.str_home), true)
                2 ->QMUITabSegment.Tab(
                    ContextCompat.getDrawable(baseFragmentActivity, R.mipmap.icon_tabbar_community),
                    ContextCompat.getDrawable(baseFragmentActivity, R.mipmap.icon_tabbar_community_selected),
                    resources.getString(R.string.str_community), true)
                3 ->QMUITabSegment.Tab(
                    ContextCompat.getDrawable(baseFragmentActivity, R.mipmap.icon_tabbar_about),
                    ContextCompat.getDrawable(baseFragmentActivity, R.mipmap.icon_tabbar_about_selected),
                    resources.getString(R.string.str_about), true)
                else -> null
            }
            if(null != tab){
                mBinding.mainTabs.addTab(tab)
            }
        }

        // initPager
        mPages[Pager.HOME] = HomeFragment()
        mPages[Pager.COMMUNITY] = CommunityFragment()
        mPages[Pager.ABOUT] = AboutFragment()
        mBinding.mainPager.adapter = mPagerAdapter
        mBinding.mainPager.setSwipeable(false)
        mBinding.mainTabs.setupWithViewPager(mBinding.mainPager, false)
    }

    override fun initData() {

    }

    override fun initListener() {

    }


    private fun getPagerAdapter(): QMUIFragmentPagerAdapter {
        return object : QMUIFragmentPagerAdapter(childFragmentManager) {
            override fun createFragment(position: Int): QMUIFragment {
                return mPages[Pager.getPagerFromPositon(position)]!!
            }

            override fun getCount(): Int {
                return mPages.size
            }
        }
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val home  = Intent(Intent.ACTION_MAIN)
            home.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            home.addCategory(Intent.CATEGORY_HOME)
            startActivity(home)
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    internal enum class Pager {
        HOME, COMMUNITY, ABOUT;

        companion object {

            fun getPagerFromPositon(position: Int): Pager {
                return when (position) {
                    0 -> HOME
                    1 -> COMMUNITY
                    2 -> ABOUT
                    else -> HOME
                }
            }
        }
    }

}