package com.renj.mvvmbase.view;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.renj.mvvmbase.R;
import com.renj.mvvmbase.viewmodel.BaseViewModel;
import com.renj.mvvmbase.viewmodel.ViewDialogData;
import com.renj.utils.common.UIUtils;
import com.renj.utils.res.ResUtils;
import com.renj.utils.res.StringUtils;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-05-11   18:39
 * <p>
 * 描述：Fragment的基类<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * 如果一个新的Fragment不需要访问网络，那么就直接继承{@link BaseFragment}<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * 如果一个新的Fragment需要访问防落，那么可以继承{@link BaseLoadFragment}
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public abstract class BaseFragment<VD extends ViewDataBinding, VM extends BaseViewModel> extends Fragment implements IBaseView, View.OnClickListener {
    protected VD viewDataBinding;
    protected VM viewModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        viewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), null, false);
        viewModel = createAndBindViewModel(viewDataBinding);
        listenerViewDialogData(viewModel);
        View contentView = initRPageStatusController(viewDataBinding.getRoot());

        // 注册 ARouter 路由
        //ARouter.getInstance().inject(this);
        initPresenter();
        return contentView;
    }

    private void listenerViewDialogData(VM viewModel) {
        if (viewModel != null) {
            viewModel.viewDialogData.observeForever(new Observer<ViewDialogData>() {
                @Override
                public void onChanged(@Nullable ViewDialogData viewDialogData) {
                    if (ViewDialogData.VIEW_DIALOG_STATUS_SHOW == viewDialogData.dialogStatus) {
                        if (StringUtils.isEmptys(viewDialogData.succeedMsg, viewDialogData.failMsg)) {
                            showLoadingDialog(viewDialogData.loadingMsg);
                        } else {
                            showLoadingDialog(viewDialogData.loadingMsg, viewDialogData.succeedMsg, viewDialogData.failMsg);
                        }
                    } else if (ViewDialogData.VIEW_DIALOG_STATUS_CLOSE_SUCCESS == viewDialogData.dialogStatus) {
                        closeSucceedDialog(viewDialogData.closeMillis);
                    } else if (ViewDialogData.VIEW_DIALOG_STATUS_CLOSE_FAIL == viewDialogData.dialogStatus) {
                        closeFailDialog(viewDialogData.closeMillis);
                    } else {
                        closeLoadingDialog();
                    }
                }
            });
        }
    }

    /**
     * 创建 ViewModel，并且给 ViewDataBinding 设置 ViewModel
     *
     * @param viewDataBinding ViewDataBinding
     */
    protected abstract VM createAndBindViewModel(VD viewDataBinding);

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    @Override
    public void initData() {

    }

    /**
     * 在{@link BaseLoadFragment}中重写，初始化页面控制器
     *
     * @param view
     * @return
     */
    protected View initRPageStatusController(View view) {
        return view;
    }

    /**
     * 初始化Presenter，在{@link BaseLoadFragment}中重写
     */
    protected void initPresenter() {

    }

    @Override
    public void onClick(View v) {
        handlerClick(v, v.getId());
    }

    /**
     * 处理点击事件时，只需要重写该方法即可，不需要再实现{@link View.OnClickListener}接口
     *
     * @param v   点击的控件
     * @param vId 点击的控件id
     */
    protected void handlerClick(View v, int vId) {

    }

    @Override
    public <E> void showContentPage(@LoadingStyle int loadingStyle, @NonNull E e) {
    }

    @Override
    public void showLoadingPage(@LoadingStyle int loadingStyle) {
    }

    @Override
    public <E> void showEmptyDataPage(@LoadingStyle int loadingStyle, @NonNull E e) {
    }

    @Override
    public void showNetWorkErrorPage(@LoadingStyle int loadingStyle) {
    }

    @Override
    public void showErrorPage(@LoadingStyle int loadingStyle, Throwable e) {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        closeLoadingDialog();
    }

    /****************  加载对话框处理 ***********/

    protected LoadingDialog loadingDialog;

    @Override
    public void showLoadingDialog() {
        loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.setLoadingText(ResUtils.getString(R.string.dialog_default_loading))
                .setSuccessText(ResUtils.getString(R.string.dialog_default_succeed))
                .setFailedText(ResUtils.getString(R.string.dialog_default_fail))
                .setInterceptBack(false)
                .setLoadSpeed(LoadingDialog.Speed.SPEED_TWO);
        loadingDialog.show();
    }

    @Override
    public void showLoadingDialog(@NonNull String loadingMsg) {
        loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.setLoadingText(loadingMsg)
                .setInterceptBack(false)
                .setLoadSpeed(LoadingDialog.Speed.SPEED_TWO);
        loadingDialog.show();
    }

    @Override
    public void showLoadingDialog(@NonNull String loadingMsg, @NonNull String succeedMsg, @NonNull String failMsg) {
        loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.setLoadingText(loadingMsg)
                .setSuccessText(succeedMsg)
                .setFailedText(failMsg)
                .setInterceptBack(false)
                .setLoadSpeed(LoadingDialog.Speed.SPEED_TWO);
        loadingDialog.show();
    }

    @Override
    public void closeLoadingDialog() {
        if (loadingDialog != null)
            loadingDialog.close();
    }

    @Override
    public void closeSucceedDialog() {
        closeSucceedDialog(1800);
    }

    @Override
    public void closeSucceedDialog(long millis) {
        if (loadingDialog != null) {
            loadingDialog.loadSuccess();
            // 绘制完成之后在在关闭进度框
            UIUtils.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadingDialog.close();
                    loadingDialog = null;
                }
            }, millis);
        }
    }

    @Override
    public void closeFailDialog() {
        closeFailDialog(1800);
    }

    @Override
    public void closeFailDialog(long millis) {
        if (loadingDialog != null) {
            loadingDialog.loadFailed();
            // 绘制完成之后在在关闭进度框
            UIUtils.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadingDialog.close();
                    loadingDialog = null;
                }
            }, millis);
        }
    }
}
