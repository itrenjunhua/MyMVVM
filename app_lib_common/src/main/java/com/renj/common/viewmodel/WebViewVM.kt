package com.renj.mvp.presenter

import android.arch.lifecycle.MutableLiveData
import com.renj.common.mode.bean.bundle.WebActivityBundleData
import com.renj.common.mode.bean.bundle.WebActivityType
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
class WebViewVM(var bundleData: WebActivityBundleData?) : RxLoadViewModel() {

    var webCollectionStatus = MutableLiveData<Boolean>()
    var webSeeCount = MutableLiveData<Long>()
    var webBottomVisible = MutableLiveData<Boolean>()

    init {
        webBottomVisible.value = bundleData?.type == WebActivityType.TYPE_LIST

        if (bundleData?.type == WebActivityType.TYPE_LIST) {
            addSeeCount()
            getCollectionStatus()
        }
    }

    private fun getCollectionStatus() {
        addDisposable(
            mModelManager.getDBHelper(DBHelper::class.java)
                .getCollectionStatus(bundleData!!.pid, bundleData!!.id)
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

    private fun addSeeCount() {
        var generalListBean = GeneralListData()
        generalListBean.pid = bundleData!!.pid
        generalListBean.id = bundleData!!.id
        generalListBean.title = bundleData!!.title
        generalListBean.content = bundleData!!.content
        generalListBean.url = bundleData!!.url
        generalListBean.images = bundleData!!.images

        addDisposable(
            mModelManager.getDBHelper(DBHelper::class.java)
                .addSeeCount(generalListBean)
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

    fun changeCollectionStatus() {
        addDisposable(
            mModelManager.getDBHelper(DBHelper::class.java)
                .changeCollectionStatus(bundleData!!.pid, bundleData!!.id, !webCollectionStatus.value!!)
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
