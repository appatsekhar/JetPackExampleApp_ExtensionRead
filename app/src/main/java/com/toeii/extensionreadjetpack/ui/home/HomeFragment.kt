package com.toeii.extensionreadjetpack.ui.home

import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.viewpager.widget.PagerAdapter
import com.google.android.material.navigation.NavigationView
import com.qmuiteam.qmui.arch.QMUIFragment
import com.qmuiteam.qmui.arch.QMUIFragmentPagerAdapter
import com.qmuiteam.qmui.util.QMUIDisplayHelper
import com.qmuiteam.qmui.util.QMUIResHelper
import com.qmuiteam.qmui.widget.QMUITabSegment
import com.toeii.extensionreadjetpack.R
import com.toeii.extensionreadjetpack.base.BaseFragment
import com.toeii.extensionreadjetpack.databinding.FragmentHomeBinding
import com.toeii.extensionreadjetpack.ui.home.daily.DailyFragment
import com.toeii.extensionreadjetpack.ui.home.recommend.RecommendFragment
import com.toeii.extensionreadjetpack.ui.about.history.BrowseRecordFragment
import org.jetbrains.anko.backgroundColorResource
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.toast
import java.util.HashMap


class HomeFragment : BaseFragment<FragmentHomeBinding>(), NavigationView.OnNavigationItemSelectedListener {

    private val mPagerAdapter: PagerAdapter by lazy { getPagerAdapter() }
    private val mPages = HashMap<Pager, QMUIFragment>()

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initView(view: View) {


        mBinding.homeTopbar.setBackgroundColor(ContextCompat.getColor(context!!, R.color.app_color))

        // initTab
        val space = QMUIDisplayHelper.dp2px(context, 10)
        mBinding.homeTabs.backgroundColorResource = R.color.app_color
        mBinding.homeTabs.setTabTextSize(resources.getDimensionPixelSize(R.dimen.text_size_16))
        mBinding.homeTabs.setDefaultNormalColor(QMUIResHelper.getAttrColor(activity, R.attr.qmui_config_color_gray_9))
        mBinding.homeTabs.setDefaultSelectedColor(resources.getColor(R.color.qmui_config_color_white))
        mBinding.homeTabs.setHasIndicator(true)
        mBinding.homeTabs.setItemSpaceInScrollMode(space)
        mBinding.homeTabs.setPadding(space,0,space,0)
        for(position in 1..2){
            val tab = when (position) {
                1 -> QMUITabSegment.Tab(resources.getString(R.string.str_home_tab_recommend))
                2 -> QMUITabSegment.Tab(resources.getString(R.string.str_home_tab_daily))
                else -> null
            }
            if(null != tab){
                mBinding.homeTabs.addTab(tab)
            }
        }

        // initPager
        mPages[Pager.Recommend] = RecommendFragment()
        mPages[Pager.Daily] = DailyFragment()
        mBinding.homePager.adapter = mPagerAdapter
        mBinding.homeTabs.setupWithViewPager(mBinding.homePager, false)

    }

    override fun initData() {
    }

    override fun initListener() {

        mBinding.homeNavView.setNavigationItemSelectedListener(this)

        mBinding.homeMenu.onClick {
            if (mBinding.homeDrawerlayout.isDrawerOpen(GravityCompat.START)) {
                mBinding.homeDrawerlayout.closeDrawer(GravityCompat.START)
            }else{
                mBinding.homeDrawerlayout.openDrawer(GravityCompat.START)
            }
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_project_main -> {
                openWebView(item.title.toString(),"https://github.com/toeii/JetPackExampleApp_ExtensionRead/")
            }
            R.id.nav_browse_record -> {
                startFragment(BrowseRecordFragment())
            }
            R.id.nav_about_author -> {
                openWebView(item.title.toString(),"https://toeii.github.io/about/")
            }
        }
        return true
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