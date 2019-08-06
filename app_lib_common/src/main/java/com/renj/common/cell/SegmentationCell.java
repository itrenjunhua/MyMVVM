package com.renj.common.cell;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import com.renj.common.R;
import com.renj.common.databinding.CellSegmentationBinding;
import com.renj.view.recyclerview.adapter.BindingRecyclerCell;
import com.renj.view.recyclerview.adapter.BindingRecyclerViewHolder;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-07-08   10:27
 * <p>
 * 描述：推荐分割样式
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class SegmentationCell extends BindingRecyclerCell<String, CellSegmentationBinding> {
    public SegmentationCell(String itemData) {
        super(itemData);
    }

    @Override
    public int getRecyclerItemType() {
        return 65534;
    }

    @NonNull
    @Override
    public BindingRecyclerViewHolder onCreateViewHolder(@NonNull Context context, @NonNull ViewGroup parent, int viewType) {
        return new BindingRecyclerViewHolder(context, parent, R.layout.cell_segmentation);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingRecyclerViewHolder holder, CellSegmentationBinding viewDataBinding, int position, String itemData) {
        viewDataBinding.setSegmentationName(itemData);
    }
}
