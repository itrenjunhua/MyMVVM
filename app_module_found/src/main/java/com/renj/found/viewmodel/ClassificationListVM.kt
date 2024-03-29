package com.renj.found.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.os.Bundle
import com.renj.common.cell.CommonCellFactory
import com.renj.common.mode.http.exception.NullDataException
import com.renj.common.mode.http.utils.CustomSubscriber
import com.renj.common.mode.http.utils.ResponseTransformer
import com.renj.found.mode.bean.response.GeneralListRPB
import com.renj.found.mode.http.HttpHelper
import com.renj.found.view.cell.CellFactory
import com.renj.mvvmbase.view.LoadingStyle
import com.renj.mvvmbase.viewmodel.PageStatusData
import com.renj.pagestatuscontroller.annotation.RPageStatus
import com.renj.rxsupport.rxviewmodel.RxLoadViewModel
import com.renj.rxsupport.utils.RxUtils
import com.renj.utils.collection.ListUtils
import com.renj.view.recyclerview.adapter.BindingRecyclerAdapter
import com.renj.view.recyclerview.adapter.IBindingRecyclerCell
import io.reactivex.Flowable
import org.reactivestreams.Publisher
import retrofit2.Response

/**
 * ======================================================================
 *
 *
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 *
 *
 * 创建时间：2019-07-09   10:20
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
class ClassificationListVM(bundleData: Bundle?) : RxLoadViewModel() {
    var loadMore: MutableLiveData<Boolean> = MutableLiveData()
    var recyclerAdapter: BindingRecyclerAdapter<IBindingRecyclerCell<*, *>>? = BindingRecyclerAdapter()

    private var pageNo = 1
    private var pageSize = 20
    private var pid: Int = 0

    init {
        pid = bundleData?.getInt("pid", 0)!!
    }

    fun loadPageClassificationListData() {
        pageNo = 1
        classificationListRequest(LoadingStyle.LOADING_PAGE, pid, pageNo, pageSize)
    }

    fun refreshClassificationListData() {
        pageNo = 1
        classificationListRequest(LoadingStyle.LOADING_REFRESH, pid, pageNo, pageSize)
    }

    fun loadMoreClassificationListData() {
        classificationListRequest(LoadingStyle.LOADING_LOAD_MORE, pid, pageNo, pageSize)
    }

    private fun classificationListRequest(loadingStyle: Int, pid: Int, pageNo: Int, pageSize: Int) {
        pageStatusData.value = PageStatusData(RPageStatus.LOADING, loadingStyle)
        addDisposable(
            mModelManager.getHttpHelper(HttpHelper::class.java)
                .classificationListRequest(pid, pageNo, pageSize)
                .compose(object : ResponseTransformer<GeneralListRPB>() {
                    override fun apply(upstream: Flowable<Response<GeneralListRPB>>?): Publisher<GeneralListRPB> {
                        return super.apply(upstream)
                    }

                    @Throws(NullDataException::class)
                    override fun onNullDataJudge(generalListRPB: GeneralListRPB?) {
                        if (ListUtils.isEmpty(generalListRPB?.data?.list))
                            throw NullDataException(generalListRPB!!)
                    }
                })
                .compose(RxUtils.newInstance().threadTransformer())
                .subscribeWith(object : CustomSubscriber<GeneralListRPB>(loadingStyle, this) {
                    override fun onResult(generalListRPB: GeneralListRPB) {
                        if (loadingStyle == LoadingStyle.LOADING_REFRESH || loadingStyle == LoadingStyle.LOADING_PAGE)
                            recyclerAdapter?.setData(CellFactory.createGeneralListCell(generalListRPB.data.list) as List<IBindingRecyclerCell<*, *>>)
                        if (loadingStyle == LoadingStyle.LOADING_LOAD_MORE)
                            recyclerAdapter?.addAndNotifyAll(CellFactory.createGeneralListCell(generalListRPB.data.list) as List<IBindingRecyclerCell<*, *>>)

                        loadMore.value = pageNo >= generalListRPB.data.page
                        if (pageNo >= generalListRPB.data.page) {
                            recyclerAdapter?.addAndNotifyAll(CommonCellFactory.createNoMoreCell() as IBindingRecyclerCell<*, *>)
                        }

                        this@ClassificationListVM.pageNo += 1
                    }
                })
        )
    }
}
