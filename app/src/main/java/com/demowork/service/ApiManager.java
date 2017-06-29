package com.demowork.service;


import com.demowork.bean.JokeModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by guojiadong
 * on 2017/6/29.
 */

public interface ApiManager {

    /**
     * 此方法为最初始的方法
     * @param page
     * @param pageSeize
     * @param sort
     * @param key
     * @param time
     * @return
     */
    @GET("list.from?")
    Call<JokeModel> getJokeModel(@Query("page") int page , @Query("pagesize") int pageSeize , @Query("sort") String sort
    , @Query("key") String key, @Query("time") Long time);

    @GET("{url}")
    Call<JokeModel> getJokeModel(@Path("url") String url
    , @QueryMap Map<String ,String> maps);
}
