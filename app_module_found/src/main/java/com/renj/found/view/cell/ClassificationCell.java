package com.renj.found.view.cell;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import com.renj.common.utils.aroute.ARouterPath;
import com.renj.common.utils.aroute.ARouterUtils;
import com.renj.found.R;
import com.renj.found.databinding.FoundCellClassificationBinding;
import com.renj.found.mode.bean.response.ClassificationRPB;
import com.renj.view.recyclerview.adapter.BindingRecyclerAdapter;
import com.renj.view.recyclerview.adapter.BindingRecyclerCell;
import com.renj.view.recyclerview.adapter.BindingRecyclerViewHolder;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-07-08   17:16
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class ClassificationCell extends BindingRecyclerCell<ClassificationRPB, FoundCellClassificationBinding> {
    public ClassificationCell(ClassificationRPB itemData) {
        super(itemData);
    }

    @Override
    public int getRecyclerItemType() {
        return IRecyclerCellType.CLASSIFICATION_TYPE;
    }

    @NonNull
    @Override
    public BindingRecyclerViewHolder onCreateViewHolder(@NonNull Context context, @NonNull ViewGroup parent, int viewType) {
        return new BindingRecyclerViewHolder(context, parent, R.layout.found_cell_classification);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingRecyclerViewHolder holder, FoundCellClassificationBinding viewDataBinding, int position, ClassificationRPB itemData) {
        viewDataBinding.setClassificationRPB(itemData);
    }

    @Override
    public void onItemClick(@NonNull Context context, @NonNull BindingRecyclerAdapter recyclerAdapter,
                            @NonNull BindingRecyclerViewHolder holder, FoundCellClassificationBinding viewDataBinding,
                            @NonNull View itemView, int position, ClassificationRPB itemData) {
        Bundle bundle = new Bundle();
        bundle.putString("title", itemData.label);
        bundle.putInt("pid", itemData.id);
        ARouterUtils.openActivity(ARouterPath.PATH_FOUND_ACTIVITY_CLASSIFICATION_LIST, "data", bundle);
    }
}
