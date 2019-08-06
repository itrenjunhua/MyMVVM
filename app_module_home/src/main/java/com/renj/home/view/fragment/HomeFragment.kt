package com.renj.home.view.fragment

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.renj.common.utils.aroute.ARouterPath
import com.renj.common.utils.aroute.ARouterUtils
import com.renj.home.R
import com.renj.home.databinding.HomeFragmentBinding
import com.renj.mvvmbase.view.BaseFragment
import com.renj.mvvmbase.viewmodel.BaseViewModel
import com.renj.utils.res.ResUtils
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView


/**
 * ======================================================================
 *
 * 作者：Renj
 *
 * 创建时间：2019-07-07   16:59
 *
 * 描述：首页 Fragment
 *
 * 修订历史：
 *
 * ======================================================================
 */
@Route(path = ARouterPath.PATH_HOME_FRAGMENT_HOME)
class HomeFragment : BaseFragment<HomeFragmentBinding,BaseViewModel>() {
    override fun createAndBindViewModel(viewDataBinding: HomeFragmentBinding?): BaseViewModel {
        return BaseViewModel()
    }

    val titles: Array<String> by lazy {
        arrayOf(
                ResUtils.getString(R.string.home_top_my_csdn),
                ResUtils.getString(R.string.home_top_my_github)
        )
    }
    var fragments = ArrayList<Fragment>()

    override fun getLayoutId(): Int {
        return R.layout.home_fragment
    }

    override fun initData() {
        fragments.add(ARouterUtils.getFragment(ARouterPath.PATH_HOME_FRAGMENT_MY_CSDN))
        fragments.add(ARouterUtils.getFragment(ARouterPath.PATH_HOME_FRAGMENT_MY_GITHUB))
        initViewPager()
        initIndicator()
    }

    private fun initViewPager() {
        viewDataBinding.homeViewPager.adapter = object : FragmentPagerAdapter(childFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return fragments[position]
            }

            override fun getCount(): Int {
                return fragments.size
            }
        }
    }

    private fun initIndicator() {
        val commonNavigator = CommonNavigator(activity)
        commonNavigator.isAdjustMode = true
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return titles.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val colorTransitionPagerTitleView = ColorTransitionPagerTitleView(context)
                colorTransitionPagerTitleView.normalColor = ResUtils.getColor(R.color.main_text)
                colorTransitionPagerTitleView.selectedColor = ResUtils.getColor(R.color.app_main)
                colorTransitionPagerTitleView.text = titles[index]
                colorTransitionPagerTitleView.setOnClickListener {
                    viewDataBinding.homeViewPager.currentItem = index
                }
                return colorTransitionPagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                indicator.mode = LinePagerIndicator.MODE_WRAP_CONTENT
                indicator.setColors(ResUtils.getColor(R.color.app_main))
                return indicator
            }
        }
        viewDataBinding.homeMagicIndicator.navigator = commonNavigator
        ViewPagerHelper.bind(viewDataBinding.homeMagicIndicator, viewDataBinding.homeViewPager)
    }
}