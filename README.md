**MVVM架构模式+组件化编程**

# MyMVVM
将MVVM框架基本代码和常用代码抽取成一个个独立的库，方便引入并快速搭建MVVM项目框架；同时方便替换底层实现。

##  APP框架库
* mvvm_mvvmbase：MVVM 框架的各个基类，定义MVVM基本框架
* mvvm_rxsupport：Rx相关工具类以及BaseViewModel的Rx实现

## APP 组件化子module
各个子module作为 library 或者 application 的切换控制在  gradle.properties 文件的 isBuildAsLibrary 属性

* app：APP 主项目，壳工程
* app_lib_common：当前APP的公共类、资源、工具类以及ARouter路由工具类
* app_module_home：APP 项目的首页模块
* app_module_found：APP 项目的发现模块
* app_module_my：APP 项目的我的模块

## 工具库/支持库
* lib_custom_binding：自定义部分控件的Binding支持，提升控件的Binding的属性。
* lib_http：网络通讯框架，使用 Retrofit 框架搭建。
* 其他封装的框架：
    * [OkHttp 框架的二次封装](https://github.com/itrenjunhua/MyOkHttp "OkHttp 框架的二次封装")
    * [Volley 框架的二次封装](https://github.com/itrenjunhua/RVolleyTest "Volley 框架的二次封装")
* lib_android_utils：Android常用工具类。[查看这里](https://github.com/itrenjunhua/AndroidUtils "AndroidUtils")
* lib_rpagestatuscontroller：Android 页面状态控制框架 [查看这里](https://github.com/itrenjunhua/RPageStatusController "RPageStatusController")
* lib_imageLoader_interface、lib_imageLoader_glide：Android 图片加载库: [查看这里](https://github.com/itrenjunhua/ImageLoader "图片加载库封装")
    * lib_imageLoader_interface 库定义图片加载的接口
    * lib_imageLoader_glide 库为lib_imageLoader_interface的一个实现库。
* lib_loading_dialog：加载进度框的封装
* lib_android_view：Android常用控件封装。[查看这里](https://github.com/itrenjunhua/AndroidView "AndroidView")
    * RecyclerView 基本使用、Adapter 的以及 分割线的绘制类封装。[查看这里](https://github.com/itrenjunhua/RRecyclerView "RRecyclerView")
* lib_android_cache：缓存库，可以缓存各种类型数据。[查看这里](https://github.com/itrenjunhua/CacheUtils "缓存库封装")

