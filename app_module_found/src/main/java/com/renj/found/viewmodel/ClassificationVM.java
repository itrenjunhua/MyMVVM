package com.renj.found.viewmodel;

import android.support.annotation.NonNull;
import com.renj.common.mode.http.exception.NullDataException;
import com.renj.common.mode.http.utils.CustomSubscriber;
import com.renj.common.mode.http.utils.ResponseTransformer;
import com.renj.found.mode.bean.response.ClassificationRPB;
import com.renj.found.mode.http.HttpHelper;
import com.renj.mvvmbase.viewmodel.PageStatusData;
import com.renj.pagestatuscontroller.annotation.RPageStatus;
import com.renj.rxsupport.rxviewmodel.RxLoadViewModel;
import com.renj.rxsupport.utils.RxUtils;
import com.renj.utils.collection.ListUtils;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-07-08   17:05
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class ClassificationVM extends RxLoadViewModel {
    public void classificationRequest(int loadingStyle) {
        pageStatusData.setValue(new PageStatusData(RPageStatus.LOADING,loadingStyle));
        addDisposable(mModelManager.getHttpHelper(HttpHelper.class)
                .classificationDataRequest()
                .compose(new ResponseTransformer<ClassificationRPB>() {
                    @Override
                    protected void onNullDataJudge(ClassificationRPB classificationRPB) throws NullDataException {
                        if (ListUtils.isEmpty(classificationRPB.data))
                            pageStatusData.setValue(new PageStatusData(RPageStatus.EMPTY,loadingStyle,classificationRPB));
                    }
                })
                .compose(RxUtils.newInstance().threadTransformer())
                .subscribeWith(new CustomSubscriber<ClassificationRPB>(loadingStyle, this) {
                    @Override
                    public void onResult(@NonNull ClassificationRPB classificationRPB) {
                        pageStatusData.setValue(new PageStatusData(RPageStatus.CONTENT,loadingStyle,classificationRPB));
                    }
                }));
    }
}
