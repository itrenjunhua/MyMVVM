package com.renj.binding.custom;

import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-08-07   10:22
 * <p>
 * 描述：与资源绑定相关的属性集合
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class ResBindingAdapters {
    @BindingAdapter("android:src")
    public static void setSrc(ImageView view, int resId) {
        view.setImageResource(resId);
    }

    @BindingAdapter("android:background")
    public static void setBackground(View view, int resId) {
        view.setBackgroundResource(resId);
    }
}
