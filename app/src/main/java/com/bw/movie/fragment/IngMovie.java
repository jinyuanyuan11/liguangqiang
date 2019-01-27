package com.bw.movie.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bw.movie.All_Interface.MyInterface;
import com.bw.movie.R;
import com.bw.movie.adapter.MyAllHostAdapter;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.bean.MyHostMovie;
import com.bw.movie.presenter.PresenterImpl;
import com.bw.movie.utils.Contact;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * <p>文件描述：<p>
 * <p>作者：${小强}<p>
 * <p>创建时间：2019/1/2619:13<p>
 * <p>更改时间：2019/1/2619:13<p>
 * <p>版本号：1<p>
 */
public class IngMovie extends BaseFragment implements MyInterface.MyView {
    @BindView(R.id.host_recy)
    XRecyclerView hostRecy;
    Unbinder unbinder;
    private View view;
    private PresenterImpl presenter;
    private MyAllHostAdapter myAllHostAdapter;
    private HashMap<String, Object> head = new HashMap<>();
    private HashMap<String, Object> map = new HashMap<>();
    private List<MyHostMovie.ResultBean> mHost = new ArrayList<>();

    @Override
    protected void initData() {

    }

    @Override
    protected void findViewById(View view) {

        LinearLayoutManager manager1 = new LinearLayoutManager(getActivity());
        hostRecy.setLayoutManager(manager1);
        myAllHostAdapter = new MyAllHostAdapter(mHost, getActivity());
        hostRecy.setAdapter(myAllHostAdapter);
    }

    @Override
    protected void getNextData() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void progressLogic() {
        presenter = new PresenterImpl(this);
        head.put("userId", 1910);
        head.put("sessionId", "15484926221381910");
        map.put("page", 1);
        map.put("count", 10);
        presenter.startRequest(Contact.POPULAR_MOVIE, head, map, MyHostMovie.class);
    }

    @Override
    protected View getLoadView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.host_fragment, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onRequestOk(Object data) {
        if(data instanceof MyHostMovie)
        {
            MyHostMovie myHostMovie = (MyHostMovie) data;
            mHost.addAll(myHostMovie.getResult());
            myAllHostAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRequestNo(String error) {
        Log.d("sss",error+"");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
