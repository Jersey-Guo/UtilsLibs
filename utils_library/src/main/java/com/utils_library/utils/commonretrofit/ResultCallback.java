package com.utils_library.utils.commonretrofit;

import android.text.TextUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by guojiadong
 * on 2017/8/29.
 */

public class ResultCallback<T> implements Callback<BaseResponse<T>> {
    @Override
    public void onResponse(Call<BaseResponse<T>> call, Response<BaseResponse<T>> response) {
        if(response.isSuccessful()){
            if(TextUtils.equals(response.body().msgCode,"0")){

            }else{

            }
        }
    }

    @Override
    public void onFailure(Call<BaseResponse<T>> call, Throwable t) {

    }

}
