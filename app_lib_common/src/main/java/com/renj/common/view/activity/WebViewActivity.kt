package com.renj.common.view.activity

import android.os.Build
import android.support.annotation.RequiresApi
import android.view.KeyEvent
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.renj.common.R
import com.renj.common.databinding.WebViewActivityBinding
import com.renj.common.mode.bean.bundle.WebActivityBundleData
import com.renj.common.mode.bean.bundle.WebActivityType.TYPE_LIST
import com.renj.common.mode.db.GeneralListData
import com.renj.common.utils.aroute.ARouterPath
import com.renj.mvp.presenter.WebViewVM
import com.renj.mvvmbase.view.BaseLoadActivity


/**
 * ======================================================================
 *
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 *
 * 创建时间：2019-07-08   14:46
 *
 * 描述：
 *
 * 修订历史：
 *
 * ======================================================================
 */
@Route(path = ARouterPath.PATH_COMMON_ACTIVITY_WEB)
class WebViewActivity : BaseLoadActivity<WebViewActivityBinding, WebViewVM>() {
    override fun createAndBindViewModel(viewDataBinding: WebViewActivityBinding?): WebViewVM {
        var webViewVM = WebViewVM()
        viewDataBinding?.webViewVM = webViewVM
        return webViewVM
    }

    @JvmField
    @Autowired(name = "data")
    var bundleData: WebActivityBundleData? = null

    override fun getLayoutId(): Int {
        return R.layout.web_view_activity
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initData() {
        bundleData?.title?.let { setPageTitle(it) }
        setPageBack(true, false, null)
        viewModel.webBottomVisible.value = bundleData!!.type == TYPE_LIST

        if (bundleData!!.type == TYPE_LIST) {
            handlerSeeCount(bundleData)
            viewModel.getCollectionStatus(bundleData!!.pid, bundleData!!.id)

            viewDataBinding.ivCollection.setOnClickListener {
                var collectionStatus = viewModel.webCollectionStatus.value
                viewModel.changeCollectionStatus(bundleData!!.pid, bundleData!!.id, !collectionStatus!!)
            }
        }

        webSetting()
        viewDataBinding.wevView.loadUrl(bundleData!!.url)
    }

    private fun handlerSeeCount(bundleData: WebActivityBundleData?) {
        var generalListBean = GeneralListData()
        generalListBean.pid = bundleData!!.pid
        generalListBean.id = bundleData!!.id
        generalListBean.title = bundleData!!.title
        generalListBean.content = bundleData!!.content
        generalListBean.url = bundleData!!.url
        generalListBean.images = bundleData!!.images
        viewModel.addSeeCount(generalListBean)
    }

    /**
     * webView相关设置
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun webSetting() {
        // 获取WebView的设置对象
        val settings = viewDataBinding.wevView.settings

        // 设置WebView显示放大和缩小的按钮
        //settings.setBuiltInZoomControls(true);

        // 设置双击放大和缩小
        //settings.setUseWideViewPort(true);

        // 设置可以加载JavaScript脚本
        settings.javaScriptEnabled = true

        // 允许https中嵌套http  Android 5.0及以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW

        viewDataBinding.wevView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                // 强制让超链接在当前的WebView中打开
                view.loadUrl(url)
                return true
            }
        }

        viewDataBinding.wevView.webChromeClient = object : WebChromeClient() {
            /**
             * 加载进度改变时调用
             * @param view 当前的WebView对象
             * @param newProgress 当前的进度
             */
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if (100 <= newProgress) {
                    viewDataBinding.progressWebView.visibility = View.GONE
                } else {
                    viewDataBinding.progressWebView.max = 100
                    viewDataBinding.progressWebView.progress = newProgress
                    viewDataBinding.progressWebView.visibility = View.VISIBLE
                }
            }

            /**
             * 正在加载的标题
             * @param view WebView对象
             * @param title 加载的标题
             */
            override fun onReceivedTitle(view: WebView, title: String) {
                setPageTitle(title)
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.repeatCount === 0) {
            goPrePage()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    /**
     * 返回上一页，先判断WebView是否为最上层网页，如果不是，返回到上一层网页，否则退出当前的Activity
     */
    private fun goPrePage() {
        if (null != viewDataBinding.wevView && viewDataBinding.wevView.canGoBack()) {
            viewDataBinding.wevView.goBack()
        } else {
            finish()
        }
    }

    override fun onDestroy() {
        viewDataBinding.wevView.destroy()
        super.onDestroy()
    }

}