package com.renj.home.view.cell;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import com.renj.home.R;
import com.renj.home.databinding.CellNoticeBinding;
import com.renj.home.mode.bean.data.NoticeBean;
import com.renj.view.recyclerview.adapter.BindingRecyclerCell;
import com.renj.view.recyclerview.adapter.BindingRecyclerViewHolder;

import java.util.List;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-07-01   16:02
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class NoticeCell extends BindingRecyclerCell<List<NoticeBean>, CellNoticeBinding> {
    public NoticeCell(List<NoticeBean> itemData) {
        super(itemData);
    }

    @Override
    public int getRecyclerItemType() {
        return IRecyclerCellType.NOTICE_CELL_TYPE;
    }

    @NonNull
    @Override
    public BindingRecyclerViewHolder onCreateViewHolder(@NonNull Context context, @NonNull ViewGroup parent, int viewType) {
        return new BindingRecyclerViewHolder(context, parent, R.layout.cell_notice);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingRecyclerViewHolder holder, CellNoticeBinding viewDataBinding, int position, List<NoticeBean> itemData) {
        viewDataBinding.noticeText.setData(itemData);
    }
}
