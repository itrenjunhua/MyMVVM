package com.renj.common.app;


import com.renj.utils.common.PackageUtils;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2017-12-12   17:13
 * <p>
 * 描述：APP全局配置类
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public interface AppConfig {
    /**
     * Base Url
     */
    String BASE_URL = "http://129.28.203.98:8888/app/";
    /**
     * APP访问网络token
     */
    String APP_TOKEN_KEY = "token";
    String APP_TOKEN_VALUE = "Renj_1994_(token)@20190705014318@(TOKEN)_Android";
    /**
     * 数据库名字
     */
    String DATABASE_NAME = "mvvm.db";
    /**
     * APP 版本名
     */
    String versionName = PackageUtils.getVersionName();
    /**
     * APP 版本号
     */
    int versionCode = PackageUtils.getVersionCode();
    /**
     * APP的包名
     */
    String packageName = PackageUtils.getPackageName();
}
