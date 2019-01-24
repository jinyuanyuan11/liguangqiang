package com.bw.movie.All_Interface;

import java.util.HashMap;

/**
 * <p>文件描述：<p>
 * <p>作者：${小强}<p>
 * <p>创建时间：2019/1/2320:05<p>
 * <p>更改时间：2019/1/2320:05<p>
 * <p>版本号：1<p>
 */
public interface MyInterface {

    interface MyCallBack<T> {
        void onSuccess(T data);

        void onError(String error);
    }

    interface Model {
        void getData(String url, HashMap<String, String> head,HashMap<String, String> map, Class kind, MyCallBack myCallBack);
    }

    interface MyView<T> {
        void onRequestOk(T data);

        void onRequestNo(String error);
    }

    interface Presenter {
        void startRequest(String url,HashMap<String, String> head, HashMap<String, String> map, Class kind);
    }
}
