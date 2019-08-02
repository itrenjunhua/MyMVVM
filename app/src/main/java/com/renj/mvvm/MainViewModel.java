package com.renj.mvvm;

import android.arch.lifecycle.MutableLiveData;
import android.os.SystemClock;
import com.renj.mvvmbase.viewmodel.BaseViewModel;
import com.renj.mvvmbase.viewmodel.ViewDialogData;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-08-02   10:57
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class MainViewModel extends BaseViewModel {
    public MutableLiveData<String> name = new MutableLiveData<>();

    public MainViewModel(){
        name.setValue("张三");

        viewDialogData.setValue(new ViewDialogData(ViewDialogData.VIEW_DIALOG_STATUS_SHOW));

        new Thread(){
            @Override
            public void run() {
                SystemClock.sleep(3000);
                viewDialogData.postValue(new ViewDialogData(ViewDialogData.VIEW_DIALOG_STATUS_CLOSE));
            }
        }.start();
    }
}
