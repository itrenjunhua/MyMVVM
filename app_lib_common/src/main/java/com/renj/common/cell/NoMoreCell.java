package com.renj.common.cell;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import com.renj.common.R;
import com.renj.common.databinding.StatusViewNoMoreItemBinding;
import com.renj.view.recyclerview.adapter.BindingRecyclerCell;
import com.renj.view.recyclerview.adapter.BindingRecyclerViewHolder;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-07-01   14:43
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class NoMoreCell extends BindingRecyclerCell<String, StatusViewNoMoreItemBinding> {
    public NoMoreCell(String itemData) {
        super(itemData);
    }

    @Override
    public int getRecyclerItemType() {
        return 65535;
    }

    @NonNull
    @Override
    public BindingRecyclerViewHolder onCreateViewHolder(@NonNull Context context, @NonNull ViewGroup parent, int viewType) {
        return new BindingRecyclerViewHolder(context,parent, R.layout.status_view_no_more_item);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingRecyclerViewHolder holder, StatusViewNoMoreItemBinding viewDataBinding, int position, String itemData) {

    }
}
