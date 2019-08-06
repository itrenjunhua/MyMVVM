package com.renj.mvp.presenter

import android.arch.lifecycle.MutableLiveData
import com.renj.common.R
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
    var collectionDrawableId = MutableLiveData<Int>()
    var seeCount = MutableLiveData<String>()
    var bottomVisible = MutableLiveData<Int>()

    init {
        collectionDrawableId.value = R.mipmap.web_collection_n
    }

    fun getCollectionStatus(pid: Int, id: Int) {
        addDisposable(mModelManager.getDBHelper(DBHelper::class.java)
            .getCollectionStatus(pid, id)
            .compose(RxUtils.newInstance().threadTransformer())
            .subscribeWith(object : ResourceSubscriber<Boolean>() {
                override fun onComplete() {
                }

                override fun onNext(collectionStatus: Boolean?) {
                    collectionDrawableId.value =
                        if (collectionStatus!!) R.mipmap.web_collection_s else R.mipmap.web_collection_n
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
                }

                override fun onNext(seeCount: Long?) {
                    this@WebViewVM.seeCount.value = seeCount.toString()
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
                    collectionDrawableId.value =
                        if (collectionStatus!!) R.mipmap.web_collection_s else R.mipmap.web_collection_n
                }

                override fun onError(t: Throwable?) {
                }

            })
        )
    }

}
