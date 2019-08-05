package com.renj.found.view.activity

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.renj.common.utils.aroute.ARouterPath
import com.renj.found.R
import com.renj.found.databinding.FoundClassificationListActivityBinding
import com.renj.found.viewmodel.ClassificationListVM
import com.renj.mvvmbase.view.BaseLoadActivity
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
 * 创建时间：2019-07-09   11:03
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
@Route(path = ARouterPath.PATH_FOUND_ACTIVITY_CLASSIFICATION_LIST)
class ClassificationListActivity : BaseLoadActivity<FoundClassificationListActivityBinding, ClassificationListVM>() {
    override fun createAndBindViewModel(viewDataBinding: FoundClassificationListActivityBinding?): ClassificationListVM {
        var classificationListVM = ClassificationListVM()
        viewDataBinding?.classificationListVM = classificationListVM
        return classificationListVM
    }

    @JvmField
    @Autowired(name = "data")
    var bundleData: Bundle? = null

    override fun getLayoutId(): Int {
        return R.layout.found_classification_list_activity
    }

    override fun initData() {
        setPageBack(true, false, null)
        bundleData?.getString("title", "")?.let { setPageTitle(it) }

        bundleData?.getInt("pid", 0)?.let { viewModel.pid = it }

        viewDataBinding.swipeToLoadLayout.setOnRefreshListener {
            viewModel.pageNo = 1
            viewModel.classificationListRequest(
                LoadingStyle.LOADING_REFRESH,
                viewModel.pid,
                viewModel.pageNo,
                viewModel.pageSize
            )
        }
        viewDataBinding.swipeToLoadLayout.setOnLoadMoreListener {
            viewModel.classificationListRequest(
                LoadingStyle.LOADING_LOAD_MORE,
                viewModel.pid,
                viewModel.pageNo,
                viewModel.pageSize
            )
        }

        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        viewDataBinding.swipeTarget.layoutManager = linearLayoutManager
        viewDataBinding.swipeTarget.addItemDecoration(LinearItemDecoration(LinearLayoutManager.VERTICAL))

        viewModel.pageNo = 1
        viewModel.classificationListRequest(
            LoadingStyle.LOADING_PAGE,
            viewModel.pid,
            viewModel.pageNo,
            viewModel.pageSize
        )

        viewModel.loadMore.observe(this, Observer {
            if (it!!) {
                viewDataBinding.swipeToLoadLayout.isLoadingMore = false
                viewDataBinding.swipeToLoadLayout.isLoadMoreEnabled = false
            } else {
                viewDataBinding.swipeToLoadLayout.isLoadMoreEnabled = true
            }
        })
    }

    override fun handlerPageLoadException(
        iRPageStatusController: IRPageStatusController<out IRPageStatusController<*>>?,
        pageStatus: Int,
        `object`: Any?,
        view: View?,
        viewId: Int
    ) {
        if (pageStatus == RPageStatus.ERROR && viewId == R.id.tv_error) {
            viewModel.pageNo = 1
            viewModel.classificationListRequest(
                LoadingStyle.LOADING_PAGE,
                viewModel.pid,
                viewModel.pageNo,
                viewModel.pageSize
            )
        } else if (pageStatus == RPageStatus.NET_WORK && viewId == R.id.tv_reload) {
            viewModel.pageNo = 1
            // 此处修改页面状态是因为在 BaseApplication 中指定了当网络异常时点击不自动修改为 loading 状态
            rPageStatusController.changePageStatus(RPageStatus.LOADING)
            viewModel.classificationListRequest(
                LoadingStyle.LOADING_PAGE,
                viewModel.pid,
                viewModel.pageNo,
                viewModel.pageSize
            )
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
