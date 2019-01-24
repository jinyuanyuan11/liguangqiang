package com.bw.movie.utils;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * <p>文件描述：<p>
 * <p>作者：${小强}<p>
 * <p>创建时间：2019/1/1810:31<p>
 * <p>更改时间：2019/1/1810:31<p>
 * <p>版本号：1<p>
 */
public interface MyApiService {
    @GET
    Observable<ResponseBody> get(@Url String murl,@HeaderMap Map<String,Object> headermap,@QueryMap Map<String,Object> map);

    @POST
    Observable<ResponseBody> post(@Url String murl, @HeaderMap HashMap<String, String> headermap, @QueryMap HashMap<String, String> map);

    @PUT
    Observable<ResponseBody> put(@Url String url,@HeaderMap Map<String,Object> headermap,@QueryMap Map<String,Object> map);

    @DELETE
    Observable<ResponseBody> delete(@Url String url,@HeaderMap Map<String,Object> headermap,@QueryMap Map<String,Object> map);

    @POST
    Observable<ResponseBody> Image(@Url String url, @HeaderMap Map<String,Object> headermap,@Body MultipartBody body);

}
