package com.renj.mvp.presenter

import android.arch.lifecycle.MutableLiveData
import com.renj.common.mode.db.DBHelper
import com.renj.common.mode.db.GeneralListData
import com.renj.rxsupport.rxviewmodel.RxLoadViewModel
import com.renj.rxsupport.utils.RxUtils
import io.reactivex.subscribers.ResourceSubscriber

/**
 * ======================================================================
 *
 *
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 *
 *
 * 创建时间：2019-07-11   15:08
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
class WebViewVM : RxLoadViewModel() {
    var webCollectionStatus = MutableLiveData<Boolean>()
    var webSeeCount = MutableLiveData<Long>()
    var webBottomVisible = MutableLiveData<Boolean>()

    fun getCollectionStatus(pid: Int, id: Int) {
        addDisposable(mModelManager.getDBHelper(DBHelper::class.java)
            .getCollectionStatus(pid, id)
            .compose(RxUtils.newInstance().threadTransformer())
            .subscribeWith(object : ResourceSubscriber<Boolean>() {
                override fun onComplete() {
                }

                override fun onNext(collectionStatus: Boolean?) {
                    webCollectionStatus.value = collectionStatus
                }

                override fun onError(t: Throwable?) {
                }

            })
        )
    }

    fun addSeeCount(generalListData: GeneralListData) {
        addDisposable(mModelManager.getDBHelper(DBHelper::class.java)
            .addSeeCount(generalListData)
            .compose(RxUtils.newInstance().threadTransformer())
            .subscribeWith(object : ResourceSubscriber<Long>() {
                override fun onComplete() {
                    webSeeCount.value = 10
                }

                override fun onNext(seeCount: Long?) {
                    webSeeCount.value = seeCount
                }

                override fun onError(t: Throwable?) {
                }

            })
        )
    }

    fun changeCollectionStatus(pid: Int, id: Int, collectionStatus: Boolean) {
        addDisposable(mModelManager.getDBHelper(DBHelper::class.java)
            .changeCollectionStatus(pid, id, collectionStatus)
            .compose(RxUtils.newInstance().threadTransformer())
            .subscribeWith(object : ResourceSubscriber<Boolean>() {
                override fun onComplete() {
                }

                override fun onNext(collectionStatus: Boolean?) {
                    webCollectionStatus.value = collectionStatus
                }

                override fun onError(t: Throwable?) {
                }

            })
        )
    }

}
