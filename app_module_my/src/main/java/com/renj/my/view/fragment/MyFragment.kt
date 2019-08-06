package com.renj.my.view.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.renj.common.utils.aroute.ARouterPath
import com.renj.common.utils.aroute.ARouterUtils
import com.renj.mvvmbase.view.BaseFragment
import com.renj.mvvmbase.viewmodel.BaseViewModel
import com.renj.my.R
import com.renj.my.databinding.MyFragmentBinding
import com.renj.utils.common.UIUtils

/**
 * ======================================================================
 *
 *
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 *
 *
 * 创建时间：2019-04-15   16:59
 *
 *
 * 描述：
 *
 *
 * 修订历史：
 *
 *
 * ======================================================================
 */
@Route(path = ARouterPath.PATH_MY_FRAGMENT_MY)
class MyFragment : BaseFragment<MyFragmentBinding, BaseViewModel>() {
    override fun createAndBindViewModel(viewDataBinding: MyFragmentBinding?): BaseViewModel {
        return BaseViewModel()
    }

    override fun getLayoutId(): Int {
        return R.layout.my_fragment
    }

    override fun initData() {
        viewDataBinding.llMeSee.setOnClickListener {
            ARouterUtils.openActivity(ARouterPath.PATH_MY_ACTIVITY_SEE_LIST)
        }
        viewDataBinding.llMeCollection.setOnClickListener {
            ARouterUtils.openActivity(ARouterPath.PATH_MY_ACTIVITY_COLLECTION_LIST)
        }
        viewDataBinding.llMeAbout.setOnClickListener { UIUtils.showToastSafe(R.string.my_about) }
    }
}
