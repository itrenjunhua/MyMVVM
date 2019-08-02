package com.renj.mvvmbase.viewmodel;

import android.support.annotation.Nullable;
import com.renj.mvvmbase.view.LoadingStyle;
import com.renj.pagestatuscontroller.annotation.RPageStatus;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-08-02   10:28
 * <p>
 * 描述：页面状态数据
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class PageStatusData {
    @RPageStatus
    public int pageStatus;
    @LoadingStyle
    public int loadingStyle;
    @Nullable
    public Object object;
}
