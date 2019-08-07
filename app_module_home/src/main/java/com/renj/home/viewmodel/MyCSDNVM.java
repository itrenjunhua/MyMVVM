package com.renj.home.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import com.renj.common.cell.CommonCellFactory;
import com.renj.common.mode.http.exception.NullDataException;
import com.renj.common.mode.http.utils.CustomSubscriber;
import com.renj.common.mode.http.utils.ResponseTransformer;
import com.renj.home.mode.bean.response.BannerAndNoticeRPB;
import com.renj.home.mode.bean.response.GeneralListRPB;
import com.renj.home.mode.http.HttpHelper;
import com.renj.home.view.cell.CellFactory;
import com.renj.mvvmbase.view.LoadingStyle;
import com.renj.mvvmbase.viewmodel.PageStatusData;
import com.renj.pagestatuscontroller.annotation.RPageStatus;
import com.renj.rxsupport.rxviewmodel.RxLoadViewModel;
import com.renj.rxsupport.utils.RxUtils;
import com.renj.utils.collection.ListUtils;
import com.renj.view.recyclerview.adapter.BindingRecyclerAdapter;
import com.renj.view.recyclerview.adapter.IBindingRecyclerCell;

import java.util.ArrayList;
import java.util.List;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-04-16   16:50
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class MyCSDNVM extends RxLoadViewModel {
    private int pageNo = 1;
    private int pageSize = 20;

    private List<IBindingRecyclerCell> cells = new ArrayList<>();
    public MutableLiveData<Boolean> loadMore = new MutableLiveData();
    public BindingRecyclerAdapter recyclerAdapter = new BindingRecyclerAdapter();

    public void loadPageData() {
        pageNo = 1;
        bannerRequest(LoadingStyle.LOADING_PAGE);
        listRequest(LoadingStyle.LOADING_REFRESH, pageNo, pageSize);
    }

    public void refreshPageData() {
        pageNo = 1;
        bannerRequest(LoadingStyle.LOADING_REFRESH);
        listRequest(LoadingStyle.LOADING_REFRESH, pageNo, pageSize);
    }

    public void loadMoreData() {
        listRequest(LoadingStyle.LOADING_LOAD_MORE, pageNo, pageSize);
    }

    private void bannerRequest(int loadingStyle) {
        if (loadingStyle == LoadingStyle.LOADING_REFRESH || loadingStyle == LoadingStyle.LOADING_PAGE)
            cells.clear();

        pageStatusData.setValue(new PageStatusData(RPageStatus.LOADING, loadingStyle));
        addDisposable(mModelManager.getHttpHelper(HttpHelper.class)
                .myCSDNBannerRequest()
                .compose(new ResponseTransformer<BannerAndNoticeRPB>())
                .compose(RxUtils.newInstance().<BannerAndNoticeRPB>threadTransformer())
                .subscribeWith(new CustomSubscriber<BannerAndNoticeRPB>(loadingStyle, this) {
                    @Override
                    public void onResult(@NonNull BannerAndNoticeRPB bannerAndNoticeRPB) {
                        if (cells.size() > 0) {
                            cells.add(0, CellFactory.createBannerCell(bannerAndNoticeRPB.data.banners));
                            cells.add(1, CellFactory.createNoticeCell(bannerAndNoticeRPB.data.notices));
                        } else {
                            cells.add(CellFactory.createBannerCell(bannerAndNoticeRPB.data.banners));
                            cells.add(CellFactory.createNoticeCell(bannerAndNoticeRPB.data.notices));
                        }
                        recyclerAdapter.setData(cells);
                    }
                }));
    }

    private void listRequest(int loadingStyle, int pageNo, int pageSize) {
        pageStatusData.setValue(new PageStatusData(RPageStatus.LOADING, loadingStyle));
        addDisposable(mModelManager.getHttpHelper(HttpHelper.class)
                .myCSDNListRequest(pageNo, pageSize)
                .compose(new ResponseTransformer<GeneralListRPB>() {
                    @Override
                    protected void onNullDataJudge(GeneralListRPB generalListRPB) throws NullDataException {
                        if (ListUtils.isEmpty(generalListRPB.data.list)) {
                            throw new NullDataException(generalListRPB);
                        }
                    }
                })
                .compose(RxUtils.newInstance().<GeneralListRPB>threadTransformer())
                .subscribeWith(new CustomSubscriber<GeneralListRPB>(loadingStyle, this) {
                    @Override
                    public void onResult(@NonNull GeneralListRPB generalListRPB) {
                        cells.addAll(CellFactory.createGeneralListCell(generalListRPB.data.list));
                        recyclerAdapter.setData(cells);

                        loadMore.setValue(MyCSDNVM.this.pageNo >= generalListRPB.data.page);
                        if (pageNo >= generalListRPB.data.page)
                            recyclerAdapter.addAndNotifyAll(CommonCellFactory.createNoMoreCell());

                        MyCSDNVM.this.pageNo += 1;
                    }
                }));
    }
}
