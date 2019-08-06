package com.renj.home.view.cell;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.renj.common.mode.bean.bundle.WebActivityBundleData;
import com.renj.common.mode.bean.bundle.WebActivityType;
import com.renj.common.utils.ImageLoaderUtils;
import com.renj.common.utils.aroute.ARouterPath;
import com.renj.common.utils.aroute.ARouterUtils;
import com.renj.home.R;
import com.renj.home.databinding.CellBannerBinding;
import com.renj.home.mode.bean.data.BannerBean;
import com.renj.imageloaderlibrary.config.ImageLoadConfig;
import com.renj.view.recyclerview.adapter.BindingRecyclerCell;
import com.renj.view.recyclerview.adapter.BindingRecyclerViewHolder;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-07-01   16:30
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class BannerCell extends BindingRecyclerCell<List<BannerBean>, CellBannerBinding> {
    public BannerCell(List<BannerBean> itemData) {
        super(itemData);
    }

    @Override
    public int getRecyclerItemType() {
        return IRecyclerCellType.BANNER_CELL_TYPE;
    }

    @NonNull
    @Override
    public BindingRecyclerViewHolder onCreateViewHolder(@NonNull Context context, @NonNull ViewGroup parent, int viewType) {
        return new BindingRecyclerViewHolder(context, parent, R.layout.cell_banner);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingRecyclerViewHolder holder, CellBannerBinding viewDataBinding, int position, List<BannerBean> itemData) {
        List<String> images = new ArrayList<>();
        List<String> titles = new ArrayList<>();

        for (BannerBean itemDatum : itemData) {
            images.add(itemDatum.image);
            titles.add(itemDatum.title);
        }

        //设置banner样式
        viewDataBinding.banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        viewDataBinding.banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                ImageLoadConfig config = new ImageLoadConfig
                        .Builder()
                        .url((String) path)
                        .target(imageView)
                        .loadingImageId(R.mipmap.default_loading)
                        .errorImageId(R.mipmap.default_loading)
                        .build();
                ImageLoaderUtils.getDefaultImageLoaderModule().loadImage(config);
            }
        });
        // 设置点击事件
        viewDataBinding.banner.setOnBannerListener(position1 -> {
            BannerBean bannerBean = itemData.get(position1);
            WebActivityBundleData bundleData = new WebActivityBundleData(0, bannerBean.id, bannerBean.title, "", bannerBean.url, new ArrayList<>(), WebActivityType.TYPE_BANNER);
            ARouterUtils.openActivity(ARouterPath.PATH_COMMON_ACTIVITY_WEB, "data", bundleData);
        });
        //设置图片集合
        viewDataBinding.banner.setImages(images);
        //设置banner动画效果
        viewDataBinding.banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        viewDataBinding.banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
//        viewDataBinding.banner.isAutoPlay(true);
        //设置轮播时间
//        viewDataBinding.banner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        viewDataBinding.banner.setIndicatorGravity(BannerConfig.RIGHT);
        //banner设置方法全部调用完毕时最后调用
        viewDataBinding.banner.start();
    }
}
