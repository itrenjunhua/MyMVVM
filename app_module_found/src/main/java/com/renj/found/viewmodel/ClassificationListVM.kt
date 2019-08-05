package com.renj.found.viewmodel

import android.arch.lifecycle.MutableLiveData
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
import com.renj.view.recyclerview.adapter.IRecyclerCell
import com.renj.view.recyclerview.adapter.RecyclerAdapter
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
class ClassificationListVM : RxLoadViewModel() {
    var loadMore: MutableLiveData<Boolean>  = MutableLiveData()
    var pageNo = 1
    var pageSize = 20
    var pid: Int = 0
    var recyclerAdapter: RecyclerAdapter<IRecyclerCell<*>>? = null

    fun classificationListRequest(loadingStyle: Int, pid: Int, pageNo: Int, pageSize: Int) {
        pageStatusData.value = PageStatusData(RPageStatus.LOADING, loadingStyle)
        addDisposable(mModelManager.getHttpHelper(HttpHelper::class.java)
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
                            recyclerAdapter?.setData(CellFactory.createGeneralListCell(generalListRPB.data.list) as List<IRecyclerCell<*>>)
                        if (loadingStyle == LoadingStyle.LOADING_LOAD_MORE)
                            recyclerAdapter?.addAndNotifyAll(CellFactory.createGeneralListCell(generalListRPB.data.list) as List<IRecyclerCell<*>>)

                        loadMore.value = pageNo >= generalListRPB.data.page
                        if (pageNo >= generalListRPB.data.page) {
                            recyclerAdapter?.addAndNotifyAll(CommonCellFactory.createNoMoreCell() as IRecyclerCell<*>)
                        }

                        this@ClassificationListVM.pageNo += 1
                        pageStatusData.value = PageStatusData(RPageStatus.CONTENT, loadingStyle, generalListRPB)
                    }
                }))
    }
}
