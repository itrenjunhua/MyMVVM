package com.renj.mvvmbase.viewmodel;

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
     * 是否显示
     */
    public boolean showDialog;
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

    public ViewDialogData(boolean showDialog) {
        this(showDialog, "加载中...");
    }

    public ViewDialogData(boolean showDialog, String loadingMsg) {
        this(showDialog, loadingMsg, null, null, 0);
    }

    public ViewDialogData(boolean showDialog, String loadingMsg, String succeedMsg, String failMsg, long closeMillis) {
        this.showDialog = showDialog;
        this.loadingMsg = loadingMsg;
        this.succeedMsg = succeedMsg;
        this.failMsg = failMsg;
        this.closeMillis = closeMillis;
    }
}
