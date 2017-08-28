package com.demowork;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.demowork.base.BaseActivity;
import com.demowork.bean.JokeModel;
import com.demowork.service.ApiManager;
import com.utils_library.commonretrofit.RetrofitUtil;
import com.utils_library.commonutil.LogUtil;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by guojiadong
 * on 2017/6/29.
 */

public class BasicRetrofitActivity extends BaseActivity {
    private TextView helloTv;

    @Override
    public int setContentView(Bundle savedInstanceState) {
        return R.layout.activity_basicretrofit;
    }

    @Override
    public void initView() {
        helloTv = (TextView) findViewById(R.id.hello);
    }

    @Override
    public void setModel() {
        getNumData();
    }

    private void getData() {
        //创建接口动态代理的实例
        ApiManager apiManager = RetrofitUtil.getIntance().getRetrofit(this, "").create(ApiManager.class);
        Map<String, String> map = new HashMap();
        map.put("sort", "desc");
        map.put("pagesize", "20");
        map.put("key", "0fa4cd937c5553ff49c5df199f9c1ace");
        map.put("time", String.valueOf(System.currentTimeMillis() / 1000));

        //调用请求方法
        Call<JokeModel> call = apiManager.getJokeModelForGet("list.from", map);
        //执行请求获取请求结果
        call.enqueue(new Callback<JokeModel>() {
            @Override
            public void onResponse(Call<JokeModel> call, Response<JokeModel> response) {
                LogUtil.e(true, response.raw().toString());
                if (response.isSuccessful()) {
                    JokeModel model = response.body();
                    if (model != null) {
                        helloTv.setText(response.body().toString());
                    }
                } else {
                    helloTv.setText(response.errorBody().toString());
                }
                call.cancel();
            }


            @Override
            public void onFailure(Call<JokeModel> call, Throwable t) {
                helloTv.setText(t.toString());
            }

        });
    }

    private static class AsyncTaskM extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] params) {
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);
        }
    }

    public void getNumData(){
        ApiManager apiManager = RetrofitUtil.getIntance().getRetrofit(this,"").create(ApiManager.class);
        Map<String,String> map = new HashMap<>();
        map.put("sort", "desc");
        map.put("pagesize", "20");
        map.put("key", "0fa4cd937c5553ff49c5df199f9c1ace");
        map.put("time", String.valueOf(System.currentTimeMillis() / 1000));
        Call<String> call = apiManager.getJokeJsonlForGet("list.from", map);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                LogUtil.e(true,response.body());
                helloTv.setText(response.body().toString());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}
