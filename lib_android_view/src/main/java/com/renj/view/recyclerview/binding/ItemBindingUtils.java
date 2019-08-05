package com.renj.view.recyclerview.binding;

import android.support.annotation.NonNull;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

import java.util.List;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-08-05   13:37
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class ItemBindingUtils {
    private static volatile ItemBindingUtils instance = new ItemBindingUtils();

    public static ItemBindingUtils getInstance() {
        return instance;
    }

    public ItemBinding createItemBinding(@NonNull List<BaseItemViewModel> baseItemViewModels) {
        final List<BaseItemViewModel> itemViewModels = baseItemViewModels;
        OnItemBind onItemBind = new OnItemBind() {
            @Override
            public void onItemBind(ItemBinding itemBinding, int position, Object item) {
                BaseItemViewModel itemViewModel = itemViewModels.get(position);
                itemBinding.set(itemViewModel.getBRValue(), itemViewModel.getLayoutId());
            }
        };

        ItemBinding itemBinding = ItemBinding.of(onItemBind);
        for (BaseItemViewModel itemViewModel : itemViewModels) {
            itemBinding.bindExtra(itemViewModel.getListenerBRValue(), itemViewModel);
        }
        return itemBinding;
    }
}
