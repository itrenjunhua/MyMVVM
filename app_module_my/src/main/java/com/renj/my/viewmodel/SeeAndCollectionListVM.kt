package com.renj.my.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.renj.common.cell.CommonCellFactory
import com.renj.common.mode.bean.dp.ListSeeAndCollectionRDB
import com.renj.common.mode.db.DBHelper
import com.renj.common.mode.http.utils.CustomSubscriber
import com.renj.mvvmbase.view.LoadingStyle
import com.renj.mvvmbase.viewmodel.PageStatusData
import com.renj.my.view.cell.CellFactory
import com.renj.pagestatuscontroller.annotation.RPageStatus
import com.renj.rxsupport.rxviewmodel.RxLoadViewModel
import com.renj.rxsupport.utils.RxUtils
import com.renj.utils.collection.ListUtils
import com.renj.view.recyclerview.adapter.BindingRecyclerAdapter
import com.renj.view.recyclerview.adapter.IBindingRecyclerCell

/**
 * ======================================================================
 *
 * 作者：Renj
 * 邮箱：itrenjunhua@163.com
 *
 * 创建时间：2019-07-11   10:21
 *
 * 描述：
 *
 * 修订历史：
 *
 * ======================================================================
 */
class SeeAndCollectionListVM : RxLoadViewModel() {
    private var pageNo = 1
    private var pageSize = 20

    var loadMore = MutableLiveData<Boolean>()
    var recyclerAdapter = BindingRecyclerAdapter<IBindingRecyclerCell<*, *>>()

    fun loadPageListCollection() {
        pageNo = 1
        listCollectionResponse(LoadingStyle.LOADING_PAGE, pageNo, pageSize)
    }

    fun refreshListCollection() {
        pageNo = 1
        listCollectionResponse(LoadingStyle.LOADING_REFRESH, pageNo, pageSize)
    }

    fun loadMoreListCollection() {
        listCollectionResponse(LoadingStyle.LOADING_LOAD_MORE, pageNo, pageSize)
    }

    private fun listCollectionResponse(@LoadingStyle loadingStyle: Int, pagNo: Int, pageSize: Int) {
        pageStatusData.value = PageStatusData(RPageStatus.LOADING, loadingStyle)
        addDisposable(
            mModelManager.getDBHelper(DBHelper::class.java)
                .getCollectionList(pagNo, pageSize)
                .compose(RxUtils.newInstance().threadTransformer())
                .subscribeWith(object : CustomSubscriber<ListSeeAndCollectionRDB>(loadingStyle, this) {
                    override fun onResult(collectionRDB: ListSeeAndCollectionRDB) {
                        if (ListUtils.isEmpty(collectionRDB?.list)) {
                            pageStatusData.value = PageStatusData(RPageStatus.EMPTY, loadingStyle)
                        } else {
                            if (loadingStyle == LoadingStyle.LOADING_REFRESH || loadingStyle == LoadingStyle.LOADING_PAGE)
                                recyclerAdapter?.setData(CellFactory.createSeeAndCollectionListCell(collectionRDB?.list, false) as List<IBindingRecyclerCell<*, *>>)
                            else
                                recyclerAdapter?.addAndNotifyAll(CellFactory.createSeeAndCollectionListCell(collectionRDB?.list, false) as List<IBindingRecyclerCell<*, *>>)

                            if (this@SeeAndCollectionListVM.pageNo >= collectionRDB!!.page)
                                recyclerAdapter?.addAndNotifyAll(CommonCellFactory.createNoMoreCell() as IBindingRecyclerCell<*, *>)

                            this@SeeAndCollectionListVM.pageNo += 1
                        }
                    }

                })
        )
    }


    // ------------------------------  我的查看部分  ------------------------------ //

    fun loadPageListSee() {
        pageNo = 1
        listSeeResponse(LoadingStyle.LOADING_PAGE, pageNo, pageSize)
    }

    fun refreshListSee() {
        pageNo = 1
        listSeeResponse(LoadingStyle.LOADING_REFRESH, pageNo, pageSize)
    }

    fun loadMoreListSee() {
        listSeeResponse(LoadingStyle.LOADING_LOAD_MORE, pageNo, pageSize)
    }

    private fun listSeeResponse(@LoadingStyle loadingStyle: Int, pagNo: Int, pageSize: Int) {
        pageStatusData.value = PageStatusData(RPageStatus.LOADING, loadingStyle)
        addDisposable(
            mModelManager.getDBHelper(DBHelper::class.java)
                .getSeeList(pagNo, pageSize)
                .compose(RxUtils.newInstance().threadTransformer())
                .subscribeWith(object : CustomSubscriber<ListSeeAndCollectionRDB>(loadingStyle, this) {
                    override fun onResult(collectionRDB: ListSeeAndCollectionRDB) {
                        if (ListUtils.isEmpty(collectionRDB?.list)) {
                            pageStatusData.value = PageStatusData(RPageStatus.EMPTY, loadingStyle)
                        } else {
                            if (loadingStyle == LoadingStyle.LOADING_REFRESH || loadingStyle == LoadingStyle.LOADING_PAGE)
                                recyclerAdapter?.setData(CellFactory.createSeeAndCollectionListCell(collectionRDB?.list, true) as List<IBindingRecyclerCell<*, *>>)
                            else
                                recyclerAdapter?.addAndNotifyAll(CellFactory.createSeeAndCollectionListCell(collectionRDB?.list, true) as List<IBindingRecyclerCell<*, *>>)

                            if (this@SeeAndCollectionListVM.pageNo >= collectionRDB!!.page)
                                recyclerAdapter?.addAndNotifyAll(CommonCellFactory.createNoMoreCell() as IBindingRecyclerCell<*, *>)

                            this@SeeAndCollectionListVM.pageNo += 1
                        }
                    }
                })
        )
    }
}