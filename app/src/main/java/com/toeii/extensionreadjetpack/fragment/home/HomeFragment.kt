package com.toeii.extensionreadjetpack.fragment.home

import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager.widget.PagerAdapter
import com.google.android.material.navigation.NavigationView
import com.qmuiteam.qmui.arch.QMUIFragment
import com.qmuiteam.qmui.arch.QMUIFragmentPagerAdapter
import com.qmuiteam.qmui.util.QMUIDisplayHelper
import com.qmuiteam.qmui.util.QMUIResHelper
import com.qmuiteam.qmui.widget.QMUITabSegment
import com.qmuiteam.qmui.widget.QMUITopBar
import com.qmuiteam.qmui.widget.QMUIViewPager
import com.toeii.extensionreadjetpack.R
import com.toeii.extensionreadjetpack.base.BaseFragment
import org.jetbrains.anko.backgroundColorResource
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.toast
import java.util.HashMap


class HomeFragment : BaseFragment(), NavigationView.OnNavigationItemSelectedListener {


    private lateinit var mTopBar: QMUITopBar
    private lateinit var mTabSegment: QMUITabSegment
    private lateinit var mViewPager: QMUIViewPager
    private lateinit var mBtnMenu: ImageView
    private lateinit var mOpenDrawerLayout: ImageView
    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mNavigationView: NavigationView

    private lateinit var mPagerAdapter: PagerAdapter
    private lateinit var mPages: HashMap<Pager, QMUIFragment>

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initView(view: View) {
        mTopBar = view.find(R.id.home_topbar)
        mTabSegment = view.find(R.id.home_tabs)
        mViewPager = view.find(R.id.home_pager)
        mBtnMenu = view.find(R.id.home_menu)
        mOpenDrawerLayout = view.find(R.id.home_menu)
        mDrawerLayout = view.find(R.id.home_drawerlayout)
        mNavigationView = view.find(R.id.home_navView)

        mTopBar.setBackgroundColor(ContextCompat.getColor(context!!, R.color.app_color))

        // initTab
        val space = QMUIDisplayHelper.dp2px(context, 10)
        mTabSegment.backgroundColorResource = R.color.app_color
        mTabSegment.setTabTextSize(resources.getDimensionPixelSize(R.dimen.text_size_16))
        mTabSegment.setDefaultNormalColor(QMUIResHelper.getAttrColor(activity, R.attr.qmui_config_color_gray_9))
        mTabSegment.setDefaultSelectedColor(resources.getColor(R.color.qmui_config_color_white))
        mTabSegment.setHasIndicator(true)
        mTabSegment.setItemSpaceInScrollMode(space)
        mTabSegment.setPadding(space,0,space,0)
        for(position in 1..2){
            val tab = when (position) {
                1 -> QMUITabSegment.Tab("      推荐      ")
                2 -> QMUITabSegment.Tab("      日报      ")
                else -> null
            }
            if(null != tab){
                mTabSegment.addTab(tab)
            }
        }

        // initPager
        mPages = HashMap<Pager, QMUIFragment>()
        mPages[Pager.Recommend] = RecommendFragment()
        mPages[Pager.Daily] = DailyFragment()
        mPagerAdapter = getPagerAdapter()
        mViewPager.adapter = mPagerAdapter
        mTabSegment.setupWithViewPager(mViewPager, false)

    }

    override fun initData() {
    }

    override fun initListener() {

        mNavigationView.setNavigationItemSelectedListener(this)

        mBtnMenu.onClick {
            if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                mDrawerLayout.closeDrawer(GravityCompat.START)
            }else{
                mDrawerLayout.openDrawer(GravityCompat.START)
            }
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_project_main -> {
                toast("nav_project_main")
            }
            R.id.nav_browse_record -> {
                toast("nav_browse_record")
            }
            R.id.nav_check_theme -> {
                toast("nav_check_theme")
            }
            R.id.nav_about_author -> {
                toast("nav_about_author")
            }
            R.id.nav_clean_rom -> {
                toast("nav_clean_rom")
            }
        }
        return true
    }

    //TODO DrawerLayout被QMUIFragmentActivity重建
    override fun onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
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

    internal enum class Pager {
        Daily, Recommend;

        companion object {

            fun getPagerFromPositon(position: Int): Pager {
                return when (position) {
                    0 -> Recommend
                    1 -> Daily
                    else -> Recommend
                }
            }
        }
    }

}