package com.renj.mvvmbase.viewmodel;

import android.support.annotation.IntRange;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-08-01   16:19
 * <p>
 * 描述：ViewModel 中控制 Dialog 显示隐藏等的相关数据
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class ViewDialogData {
    /**
     * 显示 Dialog
     */
    public static final int VIEW_DIALOG_STATUS_SHOW = 0;
    /**
     * 直接关闭 Dialog
     */
    public static final int VIEW_DIALOG_STATUS_CLOSE = 1;
    /**
     * 显示成功并关闭 Dialog
     */
    public static final int VIEW_DIALOG_STATUS_CLOSE_SUCCESS = 2;
    /**
     * 显示失败并关闭 Dialog
     */
    public static final int VIEW_DIALOG_STATUS_CLOSE_FAIL = 3;
    /**
     * 默认成功和失败Dialog关闭时间
     */
    private static final long DEFAULT_CLOSE_TIME = 1800;
    /**
     * 是否显示
     */
    @IntRange(from = VIEW_DIALOG_STATUS_SHOW, to = VIEW_DIALOG_STATUS_CLOSE_FAIL)
    public int dialogStatus;
    /**
     * 加载中的文字
     */
    public String loadingMsg;
    /**
     * 加载成功的文字
     */
    public String succeedMsg;
    /**
     * 加载失败的文字
     */
    public String failMsg;
    /**
     * 关闭成功/失败框的时间
     */
    public long closeMillis;

    public ViewDialogData(@IntRange(from = VIEW_DIALOG_STATUS_SHOW, to = VIEW_DIALOG_STATUS_CLOSE_FAIL) int dialogStatus) {
        this(dialogStatus, "加载中...");
    }

    public ViewDialogData(@IntRange(from = VIEW_DIALOG_STATUS_SHOW, to = VIEW_DIALOG_STATUS_CLOSE_FAIL) int dialogStatus, String loadingMsg) {
        this(dialogStatus, loadingMsg, null, null, DEFAULT_CLOSE_TIME);
    }

    public ViewDialogData(@IntRange(from = VIEW_DIALOG_STATUS_SHOW, to = VIEW_DIALOG_STATUS_CLOSE_FAIL) int dialogStatus, String loadingMsg, String succeedMsg, String failMsg, long closeMillis) {
        this.dialogStatus = dialogStatus;
        this.loadingMsg = loadingMsg;
        this.succeedMsg = succeedMsg;
        this.failMsg = failMsg;
        this.closeMillis = closeMillis;
    }
}
