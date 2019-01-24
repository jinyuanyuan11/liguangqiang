package com.bw.movie.presenter;

import com.bw.movie.All_Interface.MyInterface;
import com.bw.movie.model.ModelImpl;

import java.util.HashMap;

/**
 * <p>文件描述：<p>
 * <p>作者：${小强}<p>
 * <p>创建时间：2019/1/2320:08<p>
 * <p>更改时间：2019/1/2320:08<p>
 * <p>版本号：1<p>
 */
public class PresenterImpl implements MyInterface.Presenter {
    private MyInterface.MyView myView;
    private ModelImpl model;

    public PresenterImpl(MyInterface.MyView myView) {
        this.myView = myView;
        model=new ModelImpl();
    }

    @Override
    public void startRequest(String url, HashMap<String, String> head,HashMap<String, String> map, Class kind) {
        model.getData(url, head, map, kind, new MyInterface.MyCallBack() {
            @Override
            public void onSuccess(Object data) {
                myView.onRequestOk(data);
            }

            @Override
            public void onError(String error) {
                myView.onRequestNo(error);
            }
        });
    }

    public void onDetch() {
        if (model != null) {
            model = null;
        }
        if (myView != null) {
            myView = null;
        }
    }
}
