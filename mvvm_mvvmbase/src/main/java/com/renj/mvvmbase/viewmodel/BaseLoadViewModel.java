package com.renj.mvvmbase.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import com.renj.mvvmbase.mode.ModelManager;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-08-01   16:14
 * <p>
 * 描述：需要调用model层加载数据时的 ViewModel 基类
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class BaseLoadViewModel extends BaseViewModel {
    /**
     * 控制页面状态
     */
    public MutableLiveData<PageStatusData> pageStatusData = new MutableLiveData<>();
    /**
     * 获取Model层管理对象
     */
    public ModelManager mModelManager = ModelManager.newInstance();

}
