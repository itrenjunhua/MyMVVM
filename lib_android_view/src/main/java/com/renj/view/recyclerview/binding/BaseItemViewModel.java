package com.renj.view.recyclerview.binding;

import android.support.annotation.LayoutRes;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-08-05   11:18
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class BaseItemViewModel<D> {
    private int BRValue;
    private int layoutId;
    private D itemData;
    private int listenerBRValue;

    public BaseItemViewModel(int BRValue, int layoutId) {
        this.BRValue = BRValue;
        this.layoutId = layoutId;
    }

    public BaseItemViewModel(int BRValue, int layoutId, D itemData) {
        this.BRValue = BRValue;
        this.layoutId = layoutId;
        this.itemData = itemData;
    }

    public BaseItemViewModel(int BRValue, int layoutId, D itemData, int listenerBRValue) {
        this.BRValue = BRValue;
        this.layoutId = layoutId;
        this.itemData = itemData;
        this.listenerBRValue = listenerBRValue;
    }

    public D getItemData() {
        return itemData;
    }

    public int getBRValue() {
        return BRValue;
    }

    @LayoutRes
    public int getLayoutId() {
        return layoutId;
    }

    public int getListenerBRValue() {
        return listenerBRValue;
    }

    public void onItemClick(D itemData) {

    }
}
