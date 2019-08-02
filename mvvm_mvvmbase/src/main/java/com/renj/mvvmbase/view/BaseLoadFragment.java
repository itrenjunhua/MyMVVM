package com.renj.mvvmbase.view;

import android.arch.lifecycle.Observer;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import com.renj.mvvmbase.viewmodel.BaseLoadViewModel;
import com.renj.mvvmbase.viewmodel.PageStatusData;
import com.renj.pagestatuscontroller.IRPageStatusController;
import com.renj.pagestatuscontroller.RPageStatusController;
import com.renj.pagestatuscontroller.annotation.RPageStatus;
import com.renj.pagestatuscontroller.listener.OnRPageEventListener;


/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-05-12   10:22
 * <p>
 * 描述：需要访问网络的Fragment的基类，同时也是{@link BaseFragment}的子类<br/>
 * 如果定义了泛型参数，那么就会将该泛型的Presenter初始化出来，子类直接使用即可。参数名：<code>mPresenter</code>
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public abstract class BaseLoadFragment<VD extends ViewDataBinding, VM extends BaseLoadViewModel> extends BaseFragment<VD, VM> {
    protected RPageStatusController rPageStatusController;

    @Override
    protected void initPresenter() {

    }

    /**
     * 在{@link BaseLoadFragment}中重写，初始化页面控制器
     *
     * @param view
     * @return
     */
    @Override
    protected View initRPageStatusController(View view) {
        rPageStatusController = RPageStatusController.get();
        rPageStatusController.resetOnRPageEventListener(RPageStatus.ERROR, new OnRPageEventListener() {
            @Override
            public void onViewClick(@NonNull IRPageStatusController iRPageStatusController, int pageStatus, @NonNull Object object, @NonNull View view, int viewId) {
                handlerPageLoadException(iRPageStatusController, pageStatus, object, view, viewId);
            }
        }).resetOnRPageEventListener(RPageStatus.NET_WORK, new OnRPageEventListener() {
            @Override
            public void onViewClick(@NonNull IRPageStatusController iRPageStatusController, int pageStatus, @NonNull Object object, @NonNull View view, int viewId) {
                handlerPageLoadException(iRPageStatusController, pageStatus, object, view, viewId);
            }
        }).resetOnRPageEventListener(RPageStatus.EMPTY, new OnRPageEventListener() {
            @Override
            public void onViewClick(@NonNull IRPageStatusController iRPageStatusController, int pageStatus, @NonNull Object object, @NonNull View view, int viewId) {
                handlerPageLoadException(iRPageStatusController, pageStatus, object, view, viewId);
            }
        }).resetOnRPageEventListener(RPageStatus.NOT_FOUND, new OnRPageEventListener() {
            @Override
            public void onViewClick(@NonNull IRPageStatusController iRPageStatusController, int pageStatus, @NonNull Object object, @NonNull View view, int viewId) {
                handlerPageLoadException(iRPageStatusController, pageStatus, object, view, viewId);
            }
        });
        // 监听页面状态改变
        listenerPageStatusValue();
        return rPageStatusController.bind(this, view);
    }

    private void listenerPageStatusValue() {
        if (viewModel != null) {
            viewModel.pageStatusData.observeForever(new Observer<PageStatusData>() {
                @Override
                public void onChanged(@Nullable PageStatusData pageStatusData) {
                    if (RPageStatus.LOADING == pageStatusData.pageStatus)
                        showLoadingPage(pageStatusData.loadingStyle);
                    if (RPageStatus.CONTENT == pageStatusData.pageStatus)
                        showContentPage(pageStatusData.loadingStyle, pageStatusData.object);
                    if (RPageStatus.EMPTY == pageStatusData.pageStatus)
                        showEmptyDataPage(pageStatusData.loadingStyle, pageStatusData.object);
                    if (RPageStatus.NET_WORK == pageStatusData.pageStatus)
                        showNetWorkErrorPage(pageStatusData.loadingStyle);
                    if (RPageStatus.ERROR == pageStatusData.pageStatus)
                        showErrorPage(pageStatusData.loadingStyle, (Throwable) pageStatusData.object);
                }
            });
        }
    }

    /**
     * 处理状态页面的事件
     *
     * @param iRPageStatusController 控制器
     * @param pageStatus             点击事件产生的页面状态
     * @param object                 绑定对象
     * @param view                   点击事件产生的 View
     * @param viewId                 点击事件产生的 View 的 id
     */
    protected void handlerPageLoadException(IRPageStatusController iRPageStatusController, @RPageStatus int pageStatus, Object object, View view, int viewId) {
    }

    /**
     * 该方法在{@link #showLoadingPage(int)} 中调用，
     * 当 {@link LoadingStyle} 为 {@link LoadingStyle#LOADING_DIALOG}、{@link LoadingStyle#LOADING_PAGE} 状态之外的其他状态调用
     *
     * @param loadingStyle {@link LoadingStyle}
     */
    protected void showCustomLoadingPage(@LoadingStyle int loadingStyle) {
    }

    /**
     * 该方法在{@link #showContentPage(int, Object)}、{@link #showEmptyDataPage(int, Object)}
     * {@link #showNetWorkErrorPage(int)}、{@link #showErrorPage(int, Throwable)} 时就是使用子类的 {@link RPageStatusController} 中调用，
     * 当 {@link LoadingStyle} 为 {@link LoadingStyle#LOADING_DIALOG}、{@link LoadingStyle#LOADING_PAGE} 状态之外的其他状态调用
     *
     * @param pageStatus   当前状态，使用 {@link RPageStatus} 值
     * @param loadingStyle {@link LoadingStyle}
     * @param object       信息，包括 正确结果数据、异常信息等
     */
    protected void showCustomResultPage(@RPageStatus int pageStatus, @LoadingStyle int loadingStyle, @Nullable Object object) {
    }

    /**
     * 使用子类的 {@link RPageStatusController} 替换父类的 {@link RPageStatusController}，替换之后再调用<br/>
     * {@link #showContentPage(int, Object)}、{@link #showLoadingPage(int)}、{@link #showEmptyDataPage(int, Object)}
     * {@link #showNetWorkErrorPage(int)}、{@link #showErrorPage(int, Throwable)} 时就是使用子类的 {@link RPageStatusController}
     *
     * @param rPageStatusController
     */
    protected void replaceSupperPageStatusController(@NonNull RPageStatusController rPageStatusController) {
        this.rPageStatusController = rPageStatusController;
    }

    @Override
    public <E> void showContentPage(@LoadingStyle int loadingStyle, @NonNull E e) {
        if (loadingStyle == LoadingStyle.LOADING_DIALOG) {
            closeLoadingDialog();
        } else if (loadingStyle == LoadingStyle.LOADING_PAGE) {
            changePageStatus(RPageStatus.CONTENT);
        } else {
            showCustomResultPage(RPageStatus.CONTENT, loadingStyle, e);
        }
    }

    @Override
    public void showLoadingPage(@LoadingStyle int loadingStyle) {
        if (loadingStyle == LoadingStyle.LOADING_DIALOG) {
            showLoadingDialog();
        } else if (loadingStyle == LoadingStyle.LOADING_PAGE) {
            changePageStatus(RPageStatus.LOADING);
        } else {
            showCustomLoadingPage(loadingStyle);
        }
    }

    @Override
    public <E> void showEmptyDataPage(@LoadingStyle int loadingStyle, @NonNull E e) {
        if (loadingStyle == LoadingStyle.LOADING_DIALOG) {
            closeLoadingDialog();
        } else if (loadingStyle == LoadingStyle.LOADING_PAGE) {
            changePageStatus(RPageStatus.EMPTY);
        } else {
            showCustomResultPage(RPageStatus.EMPTY, loadingStyle, e);
        }
    }

    @Override
    public void showNetWorkErrorPage(@LoadingStyle int loadingStyle) {
        if (loadingStyle == LoadingStyle.LOADING_DIALOG) {
            closeLoadingDialog();
        } else if (loadingStyle == LoadingStyle.LOADING_PAGE) {
            changePageStatus(RPageStatus.NET_WORK);
        } else {
            showCustomResultPage(RPageStatus.NET_WORK, loadingStyle, null);
        }
    }

    @Override
    public void showErrorPage(@LoadingStyle int loadingStyle, Throwable e) {
        if (loadingStyle == LoadingStyle.LOADING_DIALOG) {
            closeLoadingDialog();
        } else if (loadingStyle == LoadingStyle.LOADING_PAGE) {
            changePageStatus(RPageStatus.ERROR);
        } else {
            showCustomResultPage(RPageStatus.ERROR, loadingStyle, e);
        }
    }

    protected void changePageStatus(@RPageStatus int pageStatus) {
        if (rPageStatusController != null)
            rPageStatusController.changePageStatus(pageStatus);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
