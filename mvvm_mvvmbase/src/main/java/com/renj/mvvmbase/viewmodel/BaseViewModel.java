package com.renj.mvvmbase.viewmodel;


import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2017-09-29   16:53
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class BaseViewModel extends ViewModel {
    /**
     * 控制是否显示 Dialog
     */
    public MutableLiveData<ViewDialogData> viewDialogData = new MutableLiveData<>();
}
