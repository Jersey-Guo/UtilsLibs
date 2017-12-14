package com.utils_library.utils.commonretrofit;

/**
 * Created by guojiadong
 * on 2017/8/29.
 */

public class BaseResponse<T> {
    public String msgCode;
    public String error;
    public T data;
}
