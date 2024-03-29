package com.renj.home.app;

import android.app.Application;
import com.renj.common.app.BaseApplication;
import com.renj.common.app.IApplication;
import com.renj.home.mode.db.DBHelper;
import com.renj.home.mode.file.FileHelper;
import com.renj.home.mode.http.ApiServer;
import com.renj.home.mode.http.HttpHelper;
import com.renj.httplibrary.RetrofitUtil;
import com.renj.mvvmbase.mode.ModelManager;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-05-11   16:53
 * <p>
 * 描述：继承至Application的类，设置默认全局的异常处理，初始化全局的相关变量等操作
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class HomeApplication extends BaseApplication implements IApplication {

    @Override
    public void init(Application application) {
        // 初始化 Retrofit
        RetrofitUtil.newInstance()
                .addApiServerClass(ApiServer.class);

        // 初始化 ModelManager，注意 需要先 初始化 Retrofit
        ModelManager.newInstance()
                .addDBHelper(new DBHelper())
                .addFileHelper(new FileHelper())
                .addHttpHelper(new HttpHelper());
    }
}
