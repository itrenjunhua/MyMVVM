package com.renj.rxsupport.rxviewmodel;

import android.support.annotation.NonNull;
import com.renj.mvvmbase.viewmodel.BaseLoadViewModel;
import com.renj.rxsupport.utils.RxBus;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-08-02   14:36
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class RxLoadViewModel extends BaseLoadViewModel {
    protected CompositeDisposable compositeDisposable = new CompositeDisposable();

    /**
     * 将 {@link Disposable} 增加到 {@link CompositeDisposable} 中，对生命周期进行控制
     *
     * @param disposable 需要增加的 {@link Disposable}
     */
    public void addDisposable(@NonNull Disposable disposable) {
        if (compositeDisposable == null)
            compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(disposable);
    }

    /**
     * 增加指定观察者的事件
     *
     * @param tClass   事件类型
     * @param consumer 观察者
     */
    public <T> void addDefaultFlowable(@NonNull Class<T> tClass, @NonNull Consumer<T> consumer) {
        if (compositeDisposable == null)
            compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(RxBus.newInstance().toDefaultFlowable(tClass, consumer));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        // 清除事件
        if (compositeDisposable != null)
            compositeDisposable.clear();
    }
}
