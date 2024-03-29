package com.renj.home.view.fragment

import android.arch.lifecycle.Observer
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.renj.common.utils.aroute.ARouterPath
import com.renj.home.R
import com.renj.home.databinding.MyCsdnFragmentBinding
import com.renj.home.viewmodel.MyCSDNVM
import com.renj.mvvmbase.view.BaseLoadFragment
import com.renj.mvvmbase.view.LoadingStyle
import com.renj.pagestatuscontroller.IRPageStatusController
import com.renj.pagestatuscontroller.annotation.RPageStatus
import com.renj.utils.net.NetWorkUtils
import com.renj.view.recyclerview.draw.LinearItemDecoration

/**
 * ======================================================================
 *
 *
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 *
 *
 * 创建时间：2019-04-15   16:45
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
@Route(path = ARouterPath.PATH_HOME_FRAGMENT_MY_CSDN)
class MyCSDNFragment : BaseLoadFragment<MyCsdnFragmentBinding, MyCSDNVM>() {
    override fun createAndBindViewModel(viewDataBinding: MyCsdnFragmentBinding?): MyCSDNVM {
        var myCSDNVM = MyCSDNVM()
        viewDataBinding?.csdnViewModel = myCSDNVM
        return myCSDNVM
    }

    override fun getLayoutId(): Int {
        return R.layout.my_csdn_fragment
    }

    override fun initData() {
        // 刷新和加载监听
        viewDataBinding.swipeToLoadLayout.setOnRefreshListener {
            viewModel.refreshPageData()
        }
        viewDataBinding.swipeToLoadLayout.setOnLoadMoreListener {
            viewModel.loadMoreData()
        }

        // RecyclerView 分割线和管理器
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        viewDataBinding.swipeTarget.layoutManager = linearLayoutManager
        viewDataBinding.swipeTarget.addItemDecoration(LinearItemDecoration(LinearLayoutManager.VERTICAL))

        // 加載页面数据
        viewModel.loadPageData()

        // 是否能加载更多监听
        viewModel.loadMore.observe(this, Observer {
            if (it!!) {
                viewDataBinding.swipeToLoadLayout.isLoadingMore = false
                viewDataBinding.swipeToLoadLayout.isLoadMoreEnabled = false
            } else {
                viewDataBinding.swipeToLoadLayout.isLoadMoreEnabled = true
            }
        })
    }

    /**
     * 处理状态页面的事件
     *
     * @param iRPageStatusController 控制器
     * @param pageStatus             点击事件产生的页面状态
     * @param object                 绑定对象
     * @param view                   点击事件产生的 View
     * @param viewId                 点击事件产生的 View 的 id
     */
    override fun handlerPageLoadException(
        iRPageStatusController: IRPageStatusController<*>,
        pageStatus: Int,
        `object`: Any,
        view: View,
        viewId: Int
    ) {
        if (pageStatus == RPageStatus.ERROR && viewId == R.id.tv_error) {
            viewModel.loadPageData()
        } else if (pageStatus == RPageStatus.NET_WORK && viewId == R.id.tv_reload) {
            // 此处修改页面状态是因为在 BaseApplication 中指定了当网络异常时点击不自动修改为 loading 状态
            rPageStatusController.changePageStatus(RPageStatus.LOADING)
            viewModel.loadPageData()
        } else if (pageStatus == RPageStatus.NET_WORK && viewId == R.id.tv_net_work) {
            NetWorkUtils.openNetWorkActivity()
        }

    }

    override fun showCustomResultPage(status: Int, loadingStyle: Int, `object`: Any?) {
        if (loadingStyle == LoadingStyle.LOADING_REFRESH)
            viewDataBinding.swipeToLoadLayout.isRefreshing = false
        if (loadingStyle == LoadingStyle.LOADING_LOAD_MORE)
            viewDataBinding.swipeToLoadLayout.isLoadingMore = false
    }
}
