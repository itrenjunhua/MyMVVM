package com.renj.mvvm.activity

import com.renj.common.utils.aroute.ARouterPath
import com.renj.common.utils.aroute.ARouterUtils
import com.renj.mvvm.R
import com.renj.mvvm.databinding.SplashActivityBinding
import com.renj.mvvmbase.view.BaseActivity
import com.renj.mvvmbase.viewmodel.BaseViewModel
import com.renj.utils.common.UIUtils

/**
 * ======================================================================
 *
 * 作者：Renj
 *
 * 创建时间：2019-07-08   1:03
 *
 * 描述：
 *
 * 修订历史：
 *
 * ======================================================================
 */
class SplashActivity : BaseActivity<SplashActivityBinding, BaseViewModel>() {
    override fun getLayoutId(): Int {
        return R.layout.splash_activity
    }

    override fun createAndBindViewModel(viewDataBinding: SplashActivityBinding?): BaseViewModel? {
        return null
    }

    override fun initData() {
        UIUtils.postDelayed({
            ARouterUtils.openActivity(ARouterPath.PATH_MAIN_ACTIVITY_MAIN)
            finish()
        }, 3000)
    }
}