package com.renj.found.view.fragment

import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.renj.common.utils.aroute.ARouterPath
import com.renj.found.R
import com.renj.found.databinding.FoundFragmentBinding
import com.renj.found.viewmodel.FoundVM
import com.renj.mvvmbase.view.BaseLoadFragment
import com.renj.mvvmbase.view.LoadingStyle
import com.renj.pagestatuscontroller.IRPageStatusController
import com.renj.pagestatuscontroller.annotation.RPageStatus
import com.renj.utils.net.NetWorkUtils

/**
 * ======================================================================
 *
 *
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 *
 *
 * 创建时间：2019-04-15   16:57
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
@Route(path = ARouterPath.PATH_FOUND_FRAGMENT_FOUND)
class FoundFragment : BaseLoadFragment<FoundFragmentBinding, FoundVM>() {
    override fun createAndBindViewModel(viewDataBinding: FoundFragmentBinding?): FoundVM {
        var foundViewModel = FoundVM()
        viewDataBinding?.foundViewModel = foundViewModel
        return foundViewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.found_fragment
    }

    override fun initData() {
        initSwipeToLoadLayout()
        initRecyclerView()

        viewModel.foundRequest(LoadingStyle.LOADING_PAGE)
    }

    private fun initSwipeToLoadLayout() {
        viewDataBinding.swipeToLoadLayout.setOnRefreshListener { viewModel.foundRequest(LoadingStyle.LOADING_REFRESH) }
    }

    private fun initRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        viewDataBinding.swipeTarget.layoutManager = linearLayoutManager
        viewDataBinding.swipeTarget.addItemDecoration(DividerItemDecoration(context!!, LinearLayoutManager.VERTICAL))
    }

    override fun handlerPageLoadException(iRPageStatusController: IRPageStatusController<*>, pageStatus: Int, `object`: Any, view: View, viewId: Int) {
        if (pageStatus == RPageStatus.ERROR && viewId == R.id.tv_error) {
            viewModel.foundRequest(LoadingStyle.LOADING_PAGE)
        } else if (pageStatus == RPageStatus.NET_WORK && viewId == R.id.tv_reload) {
            // 此处修改页面状态是因为在 BaseApplication 中指定了当网络异常时点击不自动修改为 loading 状态
            rPageStatusController.changePageStatus(RPageStatus.LOADING)
            viewModel.foundRequest(LoadingStyle.LOADING_PAGE)
        } else if (pageStatus == RPageStatus.NET_WORK && viewId == R.id.tv_net_work) {
            NetWorkUtils.openNetWorkActivity()
        }
    }

    override fun showCustomResultPage(status: Int, loadingStyle: Int, `object`: Any?) {
        viewDataBinding.swipeToLoadLayout.isRefreshing = false
    }

}
