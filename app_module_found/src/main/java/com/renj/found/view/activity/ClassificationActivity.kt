package com.renj.found.view.activity

import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.renj.common.utils.aroute.ARouterPath
import com.renj.found.R
import com.renj.found.databinding.FoundClassificationActivityBinding
import com.renj.found.viewmodel.ClassificationVM
import com.renj.mvvmbase.view.BaseLoadActivity
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
 * 创建时间：2019-07-08   17:10
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
@Route(path = ARouterPath.PATH_FOUND_ACTIVITY_CLASSIFICATION)
class ClassificationActivity : BaseLoadActivity<FoundClassificationActivityBinding, ClassificationVM>() {
    override fun createAndBindViewModel(viewDataBinding: FoundClassificationActivityBinding?): ClassificationVM {
        var classificationVM = ClassificationVM()
        viewDataBinding?.classificationVM = classificationVM
        return classificationVM
    }

    override fun getLayoutId(): Int {
        return R.layout.found_classification_activity
    }

    override fun initData() {
        setPageTitle(R.string.title_classification)
        setPageBack(true, false, null)

        viewDataBinding.swipeToLoadLayout.setOnRefreshListener { viewModel.refreshClassificationPageData() }

        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        viewDataBinding.swipeTarget.layoutManager = linearLayoutManager
        viewDataBinding.swipeTarget.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        viewModel.loadClassificationPageData()
    }

    override fun handlerPageLoadException(
        iRPageStatusController: IRPageStatusController<out IRPageStatusController<*>>?,
        pageStatus: Int,
        `object`: Any?,
        view: View?,
        viewId: Int
    ) {
        if (pageStatus == RPageStatus.ERROR && viewId == R.id.tv_error) {
            viewModel.loadClassificationPageData()
        } else if (pageStatus == RPageStatus.NET_WORK && viewId == R.id.tv_reload) {
            // 此处修改页面状态是因为在 BaseApplication 中指定了当网络异常时点击不自动修改为 loading 状态
            rPageStatusController.changePageStatus(RPageStatus.LOADING)
            viewModel.loadClassificationPageData()
        } else if (pageStatus == RPageStatus.NET_WORK && viewId == R.id.tv_net_work) {
            NetWorkUtils.openNetWorkActivity()
        }
    }

    override fun showCustomResultPage(status: Int, loadingStyle: Int, `object`: Any?) {
        viewDataBinding.swipeToLoadLayout.isRefreshing = false
    }
}
