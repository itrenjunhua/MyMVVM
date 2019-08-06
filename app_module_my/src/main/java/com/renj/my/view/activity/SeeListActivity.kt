package com.renj.my.view.activity

import android.arch.lifecycle.Observer
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.renj.common.utils.aroute.ARouterPath
import com.renj.mvvmbase.view.BaseLoadActivity
import com.renj.mvvmbase.view.LoadingStyle
import com.renj.my.R
import com.renj.my.databinding.SeeAndCollectionListActivityBinding
import com.renj.my.viewmodel.SeeAndCollectionListVM
import com.renj.pagestatuscontroller.IRPageStatusController
import com.renj.pagestatuscontroller.annotation.RPageStatus
import com.renj.utils.net.NetWorkUtils
import com.renj.view.recyclerview.draw.LinearItemDecoration
import kotlinx.android.synthetic.main.see_and_collection_list_activity.*

/**
 * ======================================================================
 *
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 *
 * 创建时间：2019-07-11   14:01
 *
 * 描述：
 *
 * 修订历史：
 *
 * ======================================================================
 */
@Route(path = ARouterPath.PATH_MY_ACTIVITY_SEE_LIST)
class SeeListActivity : BaseLoadActivity<SeeAndCollectionListActivityBinding, SeeAndCollectionListVM>() {
    override fun createAndBindViewModel(viewDataBinding: SeeAndCollectionListActivityBinding?): SeeAndCollectionListVM {
        var seeAndCollectionListVM = SeeAndCollectionListVM()
        viewDataBinding?.seeAndCollectionVM = seeAndCollectionListVM
        return seeAndCollectionListVM
    }

    override fun getLayoutId(): Int {
        return R.layout.see_and_collection_list_activity
    }

    override fun initData() {
        setPageBack(true, false, null)
        setPageTitle(R.string.my_see)
        swipe_toLoad_layout.setOnRefreshListener {
            viewModel.pageNo = 1
            viewModel.listSeeResponse(LoadingStyle.LOADING_REFRESH, viewModel.pageNo, viewModel.pageSize)
        }
        swipe_toLoad_layout.setOnLoadMoreListener {
            viewModel.listSeeResponse(LoadingStyle.LOADING_LOAD_MORE, viewModel.pageNo, viewModel.pageSize)
        }

        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        swipe_target.layoutManager = linearLayoutManager
        swipe_target.addItemDecoration(LinearItemDecoration(LinearLayoutManager.VERTICAL))

        viewModel.pageNo = 1
        viewModel.listSeeResponse(LoadingStyle.LOADING_PAGE, viewModel.pageNo, viewModel.pageSize)

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
            viewModel.pageNo = 1
            viewModel.listSeeResponse(LoadingStyle.LOADING_PAGE, viewModel.pageNo, viewModel.pageSize)
        } else if (pageStatus == RPageStatus.NET_WORK && viewId == R.id.tv_reload) {
            viewModel.pageNo = 1
            // 此处修改页面状态是因为在 BaseApplication 中指定了当网络异常时点击不自动修改为 loading 状态
            rPageStatusController.changePageStatus(RPageStatus.LOADING)
            viewModel.listSeeResponse(LoadingStyle.LOADING_PAGE, viewModel.pageNo, viewModel.pageSize)
        } else if (pageStatus == RPageStatus.NET_WORK && viewId == R.id.tv_net_work) {
            NetWorkUtils.openNetWorkActivity()
        }

    }

    override fun showCustomResultPage(status: Int, loadingStyle: Int, `object`: Any?) {
        if (loadingStyle == LoadingStyle.LOADING_REFRESH)
            swipe_toLoad_layout.isRefreshing = false
        if (loadingStyle == LoadingStyle.LOADING_LOAD_MORE)
            swipe_toLoad_layout.isLoadingMore = false
    }
}