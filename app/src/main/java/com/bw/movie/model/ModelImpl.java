package com.bw.movie.model;

import android.util.Log;

import com.bw.movie.All_Interface.MyInterface;
import com.bw.movie.utils.RetrofitUtils;
import com.google.gson.Gson;

import java.util.HashMap;

/**
 * <p>文件描述：<p>
 * <p>作者：${小强}<p>
 * <p>创建时间：2019/1/2320:07<p>
 * <p>更改时间：2019/1/2320:07<p>
 * <p>版本号：1<p>
 */
public class ModelImpl implements MyInterface.Model {
    //get请求
    @Override
    public void getData(String url, HashMap<String, Object> head, HashMap<String, Object> map, final Class kind, final MyInterface.MyCallBack myCallBack) {
        final Gson gson = new Gson();
        RetrofitUtils.getInstance().get(url, head, map, new RetrofitUtils.HttpListtener() {
            @Override
            public void OnSuccess(String jsonStr) {
                Log.e("zzz", "OnSuccess: "+jsonStr );
                Object o = gson.fromJson(jsonStr, kind);
                myCallBack.onSuccess(o);
            }

            @Override
            public void OnError(String error) {
                myCallBack.onError(error);
            }
        });

        RetrofitUtils.getInstance().post(url, head, map, new RetrofitUtils.HttpListtener() {
            @Override
            public void OnSuccess(String jsonStr) {
                Object o = gson.fromJson(jsonStr, kind);
                myCallBack.onSuccess(o);
            }

            @Override
            public void OnError(String error) {
                myCallBack.onError(error);
            }
        });

    }
}
