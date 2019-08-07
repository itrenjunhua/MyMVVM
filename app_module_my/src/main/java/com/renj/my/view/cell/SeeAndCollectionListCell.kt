package com.renj.my.view.cell

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.renj.common.mode.bean.bundle.WebActivityBundleData
import com.renj.common.mode.bean.bundle.WebActivityType
import com.renj.common.mode.bean.dp.ListSeeAndCollectionDB
import com.renj.common.utils.aroute.ARouterPath
import com.renj.common.utils.aroute.ARouterUtils
import com.renj.my.R
import com.renj.my.databinding.CellSeeAndCollectionListBinding
import com.renj.view.recyclerview.adapter.BindingRecyclerAdapter
import com.renj.view.recyclerview.adapter.BindingRecyclerCell
import com.renj.view.recyclerview.adapter.BindingRecyclerViewHolder
import com.renj.view.recyclerview.adapter.IBindingRecyclerCell

/**
 * ======================================================================
 *
 *
 * 作者：Renj
 * 邮箱：itrenjunhua@163.com
 *
 *
 * 创建时间：2019-06-14   14:27
 *
 *
 * 描述：收藏和查看List条目样式
 *
 *
 * 修订历史：
 *
 *
 * ======================================================================
 */
class SeeAndCollectionListCell(itemData: ListSeeAndCollectionDB, isSeeList: Boolean) :
    BindingRecyclerCell<ListSeeAndCollectionDB, CellSeeAndCollectionListBinding>(itemData) {

    private var isSeeList: Boolean = false

    init {
        this.isSeeList = isSeeList
    }

    override fun getRecyclerItemType(): Int {
        return IRecyclerCellType.COLLECTION_SEE_TYPE
    }

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): BindingRecyclerViewHolder<CellSeeAndCollectionListBinding> {
        return BindingRecyclerViewHolder(context, parent, R.layout.cell_see_and_collection_list)
    }

    override fun onBindViewHolder(
        holder: BindingRecyclerViewHolder<*>,
        viewDataBinding: CellSeeAndCollectionListBinding?,
        position: Int,
        itemData: ListSeeAndCollectionDB?
    ) {
        viewDataBinding?.visible = isSeeList
        viewDataBinding?.seeAndCollectionDB = itemData
    }

    override fun onItemClick(
        context: Context,
        recyclerAdapter: BindingRecyclerAdapter<out IBindingRecyclerCell<*, *>>,
        holder: BindingRecyclerViewHolder<*>,
        viewDataBinding: CellSeeAndCollectionListBinding?,
        itemView: View,
        position: Int,
        itemData: ListSeeAndCollectionDB?
    ) {
        val bundleData = WebActivityBundleData(
            itemData!!.pid,
            itemData.dataId,
            itemData.title,
            itemData.content,
            itemData.url,
            itemData.images.split(","),
            WebActivityType.TYPE_LIST
        )
        ARouterUtils.openActivity(ARouterPath.PATH_COMMON_ACTIVITY_WEB, "data", bundleData)
    }
}
