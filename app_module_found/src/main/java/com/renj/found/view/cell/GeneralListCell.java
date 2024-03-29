package com.renj.found.view.cell;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import com.renj.common.mode.bean.bundle.WebActivityBundleData;
import com.renj.common.mode.bean.bundle.WebActivityType;
import com.renj.common.utils.aroute.ARouterPath;
import com.renj.common.utils.aroute.ARouterUtils;
import com.renj.found.R;
import com.renj.found.databinding.FoundCellGeneralListBinding;
import com.renj.found.mode.bean.data.GeneralListBean;
import com.renj.view.recyclerview.adapter.BindingRecyclerAdapter;
import com.renj.view.recyclerview.adapter.BindingRecyclerCell;
import com.renj.view.recyclerview.adapter.BindingRecyclerViewHolder;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：itrenjunhua@163.com
 * <p>
 * 创建时间：2019-06-14   14:27
 * <p>
 * 描述：一般的List条目样式
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class GeneralListCell extends BindingRecyclerCell<GeneralListBean, FoundCellGeneralListBinding> {
    public GeneralListCell(GeneralListBean itemData) {
        super(itemData);
    }

    @Override
    public int getRecyclerItemType() {
        return IRecyclerCellType.GENERAL_LIST_TYPE;
    }

    @NonNull
    @Override
    public BindingRecyclerViewHolder onCreateViewHolder(@NonNull Context context, @NonNull ViewGroup parent, int viewType) {
        return new BindingRecyclerViewHolder(context, parent, R.layout.found_cell_general_list);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingRecyclerViewHolder holder, FoundCellGeneralListBinding viewDataBinding, int position, GeneralListBean itemData) {
        viewDataBinding.setItemValue(itemData);
    }

    @Override
    public void onItemClick(@NonNull Context context, @NonNull BindingRecyclerAdapter recyclerAdapter,
                            @NonNull BindingRecyclerViewHolder holder, FoundCellGeneralListBinding viewDataBinding,
                            @NonNull View itemView, int position, GeneralListBean itemData) {
        WebActivityBundleData bundleData = new WebActivityBundleData(itemData.pid, itemData.id, itemData.title, itemData.content, itemData.url, itemData.images, WebActivityType.TYPE_LIST);
        ARouterUtils.openActivity(ARouterPath.PATH_COMMON_ACTIVITY_WEB, "data", bundleData);
    }
}
