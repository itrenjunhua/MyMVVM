package com.renj.mvvm.app;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2019-07-26   11:00
 * <p>
 * 描述：各个module的application全路径名
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public interface ModuleConfig {
    String[] MODULE_APPLICATION_CLASS = {
            "com.renj.home.app.HomeApplication",
            "com.renj.found.app.FoundApplication",
            "com.renj.my.app.MyApplication"
    };
}
