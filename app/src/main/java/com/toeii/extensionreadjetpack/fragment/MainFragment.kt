package com.toeii.extensionreadjetpack.fragment

import android.content.Intent
import android.view.KeyEvent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.PagerAdapter
import com.qmuiteam.qmui.arch.QMUIFragment
import com.qmuiteam.qmui.arch.QMUIFragmentPagerAdapter
import com.qmuiteam.qmui.util.QMUIResHelper
import com.qmuiteam.qmui.widget.QMUITabSegment
import com.qmuiteam.qmui.widget.QMUIViewPager
import com.toeii.extensionreadjetpack.R
import com.toeii.extensionreadjetpack.base.BaseFragment
import com.toeii.extensionreadjetpack.fragment.about.AboutFragment
import com.toeii.extensionreadjetpack.fragment.community.CommunityFragment
import com.toeii.extensionreadjetpack.fragment.home.HomeFragment
import org.jetbrains.anko.find
import java.util.HashMap

class MainFragment : BaseFragment(){

    private lateinit var mPagerAdapter: PagerAdapter
    private lateinit var mPages: HashMap<Pager, QMUIFragment>

    private lateinit var mViewPager: QMUIViewPager
    private lateinit var mTabSegment: QMUITabSegment

    override fun getLayoutId(): Int {
        return R.layout.fragment_main
    }

    override fun initView(view: View) {
        mViewPager = view.find(R.id.main_pager)
        mTabSegment = view.find(R.id.main_tabs)

        //initTab
        mTabSegment.setDefaultNormalColor(QMUIResHelper.getAttrColor(activity, R.attr.qmui_config_color_gray_6))
        mTabSegment.setDefaultSelectedColor(resources.getColor(R.color.app_color))
//        homeTabs.setDefaultTabIconPosition(QMUITabSegment.ICON_POSITION_BOTTOM);

        for(position in 1..3){
            val tab = when (position) {
                1 ->QMUITabSegment.Tab(
                    ContextCompat.getDrawable(baseFragmentActivity, R.mipmap.icon_tabbar_home),
                    ContextCompat.getDrawable(baseFragmentActivity, R.mipmap.icon_tabbar_home_selected),
                    "首页", true)
                2 ->QMUITabSegment.Tab(
                    ContextCompat.getDrawable(baseFragmentActivity, R.mipmap.icon_tabbar_community),
                    ContextCompat.getDrawable(baseFragmentActivity, R.mipmap.icon_tabbar_community_selected),
                    "社区", true)
                3 ->QMUITabSegment.Tab(
                    ContextCompat.getDrawable(baseFragmentActivity, R.mipmap.icon_tabbar_about),
                    ContextCompat.getDrawable(baseFragmentActivity, R.mipmap.icon_tabbar_about_selected),
                    "关于", true)
                else -> null
            }
            if(null != tab){
                mTabSegment.addTab(tab)
            }
        }

        // initPager
        mPages = HashMap<Pager, QMUIFragment>()
        mPages[Pager.HOME] = HomeFragment()
        mPages[Pager.COMMUNITY] = CommunityFragment()
        mPages[Pager.ABOUT] = AboutFragment()
        mPagerAdapter = getPagerAdapter()
        mViewPager.adapter = mPagerAdapter
        mViewPager.setSwipeable(false)
        mTabSegment.setupWithViewPager(mViewPager, false)
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