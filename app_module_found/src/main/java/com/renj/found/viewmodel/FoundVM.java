package com.renj.found.viewmodel;

import android.support.annotation.NonNull;
import com.renj.common.cell.CommonCellFactory;
import com.renj.common.mode.http.utils.CustomSubscriber;
import com.renj.common.mode.http.utils.ResponseTransformer;
import com.renj.found.R;
import com.renj.found.mode.bean.response.FoundRPB;
import com.renj.found.mode.http.HttpHelper;
import com.renj.found.view.cell.CellFactory;
import com.renj.mvvmbase.viewmodel.PageStatusData;
import com.renj.pagestatuscontroller.annotation.RPageStatus;
import com.renj.rxsupport.rxviewmodel.RxLoadViewModel;
import com.renj.rxsupport.utils.RxUtils;
import com.renj.utils.res.ResUtils;
import com.renj.view.recyclerview.adapter.IRecyclerCell;
import com.renj.view.recyclerview.adapter.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-04-17   14:29
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class FoundVM extends RxLoadViewModel {
    public RecyclerAdapter recyclerAdapter = new RecyclerAdapter();

    public void foundRequest(int loadingStyle) {
        pageStatusData.setValue(new PageStatusData(RPageStatus.LOADING, loadingStyle));
        addDisposable(mModelManager.getHttpHelper(HttpHelper.class)
                .foundDataRequest()
                .compose(new ResponseTransformer<FoundRPB>())
                .compose(RxUtils.newInstance().threadTransformer())
                .subscribeWith(new CustomSubscriber<FoundRPB>(loadingStyle, this) {
                    @Override
                    public void onResult(@NonNull FoundRPB foundRPB) {
                        List<IRecyclerCell<?>> cells = new ArrayList<>();
                        cells.add(CellFactory.createBannerCell(foundRPB.data.banners));
                        cells.add(CommonCellFactory.createSegmentationCell(ResUtils.getString(R.string.found_segmentation_name)));
                        cells.addAll(CellFactory.createGeneralListCell(foundRPB.data.beanList));
                        cells.add(CellFactory.createSeeMoreCell());
                        recyclerAdapter.setData(cells);
                    }
                }));
    }
}
