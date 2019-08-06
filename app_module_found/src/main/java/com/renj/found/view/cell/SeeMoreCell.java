package com.renj.found.view.cell;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import com.renj.common.utils.aroute.ARouterPath;
import com.renj.common.utils.aroute.ARouterUtils;
import com.renj.found.R;
import com.renj.found.databinding.FoundCellSeeMoreBinding;
import com.renj.view.recyclerview.adapter.BindingRecyclerAdapter;
import com.renj.view.recyclerview.adapter.BindingRecyclerCell;
import com.renj.view.recyclerview.adapter.BindingRecyclerViewHolder;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-07-08   13:51
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class SeeMoreCell extends BindingRecyclerCell<String, FoundCellSeeMoreBinding> {
    public SeeMoreCell(String itemData) {
        super(itemData);
    }

    @Override
    public int getRecyclerItemType() {
        return IRecyclerCellType.SEE_MORE_TYPE;
    }

    @NonNull
    @Override
    public BindingRecyclerViewHolder onCreateViewHolder(@NonNull Context context, @NonNull ViewGroup parent, int viewType) {
        return new BindingRecyclerViewHolder(context, parent, R.layout.found_cell_see_more);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingRecyclerViewHolder holder, FoundCellSeeMoreBinding viewDataBinding, int position, String itemData) {
        viewDataBinding.setTextValue(itemData);
    }


    @Override
    public void onItemClick(@NonNull Context context, @NonNull BindingRecyclerAdapter recyclerAdapter,
                            @NonNull BindingRecyclerViewHolder holder, FoundCellSeeMoreBinding viewDataBinding,
                            @NonNull View itemView, int position, String itemData) {
        ARouterUtils.openActivity(ARouterPath.PATH_FOUND_ACTIVITY_CLASSIFICATION);
    }
}
