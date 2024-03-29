package com.renj.common.mode.bean.response;

import com.renj.mvvmbase.mode.MVVMBaseRB;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2018-07-31   16:31
 * <p>
 * 描述：网络响应结果封装基类
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class BaseResponseBean<E> extends MVVMBaseRB {
    public String message;
    public int code;
    public E data;
}
