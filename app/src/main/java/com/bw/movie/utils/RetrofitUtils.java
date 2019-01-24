package com.bw.movie.utils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * <p>文件描述：<p>
 * <p>作者：${小强}<p>
 * <p>创建时间：2019/1/1810:29<p>
 * <p>更改时间：2019/1/1810:29<p>
 * <p>版本号：1<p>
 */
public class RetrofitUtils {
    private MyApiService myApiService;

    private RetrofitUtils() {
        //日志拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        //设置日志拦截器的等级
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                //当网络请求失败的时候，等到网络正常自动加载
                .retryOnConnectionFailure(true).build();


        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Contact.BASE_URL)
                .client(okHttpClient)
                .build();

        myApiService = retrofit.create(MyApiService.class);

    }

    public static RetrofitUtils getInstance() {
        return RetroHolder.retro;
    }

    private static class RetroHolder {
        private static final RetrofitUtils retro = new RetrofitUtils();
    }



    public RetrofitUtils get(String url, Map<String, Object> head,Map<String,Object>map)
    {
        //请求网络放在子线程
        myApiService.get(url,head,map).subscribeOn(Schedulers.io())
                //成功后回调到主线程（observeOn）是观察者
                .observeOn(AndroidSchedulers.mainThread())
                //订阅
                .subscribe(observer);
        //返回本类对象
        return RetrofitUtils.getInstance();
    }

    public RetrofitUtils post(String url, HashMap<String, String> head, HashMap<String, String> map)
    {
        if(map==null)
        {
            map=new HashMap<>();
        }
        myApiService.post(url,head,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return RetrofitUtils.getInstance();
    }

    public RetrofitUtils image(String murl,Map<String,Object> headermap,Map<String,Object> map,List<Object> list){
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("uid",String.valueOf(map.get("uid")));
        if (list.size()==1) {
            for (int i = 0; i < list.size(); i++) {
                File file = new File((String) list.get(i));
                builder.addFormDataPart("file", file.getName(),RequestBody.create(MediaType.parse("multipart/octet-stream"),file));
            }
        }
        myApiService.Image(murl,headermap,builder.build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return RetrofitUtils.getInstance();
    }



    public Observer observer=new Observer<ResponseBody>() {
        //关闭
        @Override
        public void onCompleted() {

        }

        //失败
        @Override
        public void onError(Throwable e) {
            if(httpListtener!=null)
            {
                httpListtener.OnError(e.getMessage());
            }
        }

        //成功
        @Override
        public void onNext(ResponseBody responseBody) {
            if(httpListtener!=null)
            {
                try {
                    httpListtener.OnSuccess(responseBody.string());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };


    //自定义接口
    public interface HttpListtener
    {
        void OnSuccess(String jsonStr);

        void OnError(String error);
    }

    private HttpListtener httpListtener;

    public void setHttpListtener(HttpListtener listtener) {
        this.httpListtener = listtener;
    }

}
