package com.renj.found.view.activity

import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.renj.common.utils.aroute.ARouterPath
import com.renj.found.R
import com.renj.found.databinding.FoundClassificationActivityBinding
import com.renj.found.mode.bean.response.ClassificationRPB
import com.renj.found.view.cell.CellFactory
import com.renj.found.view.cell.ClassificationCell
import com.renj.found.viewmodel.ClassificationVM
import com.renj.mvvmbase.view.BaseLoadActivity
import com.renj.mvvmbase.view.LoadingStyle
import com.renj.pagestatuscontroller.IRPageStatusController
import com.renj.pagestatuscontroller.annotation.RPageStatus
import com.renj.utils.net.NetWorkUtils
import com.renj.view.recyclerview.adapter.RecyclerAdapter

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

    private var recyclerAdapter: RecyclerAdapter<ClassificationCell>? = null

    override fun getLayoutId(): Int {
        return R.layout.found_classification_activity
    }

    override fun initData() {
        setPageTitle(R.string.title_classification)
        setPageBack(true, false, null)
        initSwipeToLoadLayout()
        initRecyclerView()

        viewModel.classificationRequest(LoadingStyle.LOADING_PAGE)
    }

    private fun initSwipeToLoadLayout() {
        viewDataBinding.swipeToLoadLayout.setOnRefreshListener { viewModel.classificationRequest(LoadingStyle.LOADING_REFRESH) }
    }

    private fun initRecyclerView() {
        recyclerAdapter = RecyclerAdapter()
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        viewDataBinding.swipeTarget.layoutManager = linearLayoutManager
        viewDataBinding.swipeTarget.adapter = recyclerAdapter
        viewDataBinding.swipeTarget.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
    }

    fun classificationRequestSuccess(requestCode: Int, classificationRPB: ClassificationRPB) {
        recyclerAdapter?.setData(CellFactory.createClassificationCell(classificationRPB.data))
    }

    override fun handlerPageLoadException(
        iRPageStatusController: IRPageStatusController<out IRPageStatusController<*>>?,
        pageStatus: Int,
        `object`: Any?,
        view: View?,
        viewId: Int
    ) {
        if (pageStatus == RPageStatus.ERROR && viewId == R.id.tv_error) {
            viewModel.classificationRequest(LoadingStyle.LOADING_PAGE)
        } else if (pageStatus == RPageStatus.NET_WORK && viewId == R.id.tv_reload) {
            // 此处修改页面状态是因为在 BaseApplication 中指定了当网络异常时点击不自动修改为 loading 状态
            rPageStatusController.changePageStatus(RPageStatus.LOADING)
            viewModel.classificationRequest(LoadingStyle.LOADING_PAGE)
        } else if (pageStatus == RPageStatus.NET_WORK && viewId == R.id.tv_net_work) {
            NetWorkUtils.openNetWorkActivity()
        }
    }

    override fun showCustomResultPage(status: Int, loadingStyle: Int, `object`: Any?) {
        viewDataBinding.swipeToLoadLayout.isRefreshing = false
    }
}
