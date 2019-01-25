package com.bw.movie.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.bw.movie.All_Interface.MyInterface;
import com.bw.movie.R;
import com.bw.movie.adapter.CinemaFlowAdapter;
import com.bw.movie.adapter.MIngMovieAdapter;
import com.bw.movie.adapter.MyHostMovieAdapter;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.bean.MyElseMovie;
import com.bw.movie.bean.MyHostMovie;
import com.bw.movie.bean.MyNearMovie;
import com.bw.movie.presenter.PresenterImpl;
import com.bw.movie.utils.Contact;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.HotCity;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import recycler.coverflow.RecyclerCoverFlow;

/**
 * <p>文件描述：<p>
 * <p>作者：${小强}<p>
 * <p>创建时间：2019/1/2414:25<p>
 * <p>更改时间：2019/1/2414:25<p>
 * <p>版本号：1<p>
 */
public class Fragment_Film<T> extends BaseFragment implements GeocodeSearch.OnGeocodeSearchListener, MyInterface.MyView<T> {
    @BindView(R.id.rcf_cinema_flow)
    RecyclerCoverFlow rcfCinemaFlow;
    @BindView(R.id.film_pager_get_address)
    ImageButton filmPagerGetAddress;
    Unbinder unbinder;
    @BindView(R.id.film_pager_get_jing)
    TextView filmPagerGetJing;
    @BindView(R.id.film_pager_get_wei)
    TextView filmPagerGetWei;
    @BindView(R.id.loop_movie_progressBar)
    ProgressBar loopMovieProgressBar;
    Unbinder unbinder1;
    @BindView(R.id.et_search_content)
    EditText etSearchContent;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    Unbinder unbinder2;
    @BindView(R.id.host_recy)
    RecyclerView hostRecy;
    Unbinder unbinder3;
    @BindView(R.id.Ing_recy)
    RecyclerView IngRecy;
    Unbinder unbinder4;
    private CinemaFlowAdapter cinemaFlowAdapter;
    private MyHostMovieAdapter myHostMovieAdapter;
    private MIngMovieAdapter mIngMovieAdapter;

    private View view;
    List<HotCity> hotCities = new ArrayList<>();
    private List<MyHostMovie.ResultBean> mHost = new ArrayList<>();
    private List<MyElseMovie.ResultBean> mIng = new ArrayList<>();
    private GeocodeSearch geocodeSearch;

    private PresenterImpl presenter;
    private List<MyNearMovie.ResultBean> mList = new ArrayList<>();
    private HashMap<String, Object> head = new HashMap<>();
    private HashMap<String, Object> map = new HashMap<>();
    private int imgs[] = {R.mipmap.loop1, R.mipmap.loop2, R.mipmap.loop3, R.mipmap.loop4, R.mipmap.loop5, R.mipmap.loop7, R.mipmap.loop8, R.mipmap.loop10, R.mipmap.loop11};
    private List<Integer> mImg = new ArrayList<>();
    boolean isExpand = false;
    private TransitionSet mSet;
    private int flag;

    @Override
    protected void initData() {


    }

    @Override
    protected void findViewById(View view) {
        for (int i = 0; i < imgs.length; i++) {
            mImg.add(imgs[i]);
        }
        cinemaFlowAdapter = new CinemaFlowAdapter(getActivity(), mImg);
        rcfCinemaFlow.setAdapter(cinemaFlowAdapter);

        LinearLayoutManager manager1 = new LinearLayoutManager(getActivity());
        manager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        hostRecy.setLayoutManager(manager1);

        LinearLayoutManager manager2 = new LinearLayoutManager(getActivity());
        manager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        IngRecy.setLayoutManager(manager2);

        mIngMovieAdapter = new MIngMovieAdapter(mIng, getActivity());
        IngRecy.setAdapter(mIngMovieAdapter);

        myHostMovieAdapter = new MyHostMovieAdapter(mHost, getActivity());
        hostRecy.setAdapter(myHostMovieAdapter);

    }

    @Override
    protected void getNextData() {

        hotCities.add(new HotCity("北京", "北京", "101010100"));
        hotCities.add(new HotCity("上海", "上海", "101020100"));
        hotCities.add(new HotCity("广州", "广东", "101280101"));
        hotCities.add(new HotCity("深圳", "广东", "101280601"));
        hotCities.add(new HotCity("杭州", "浙江", "101210101"));

    }

    @Override
    protected void setListener() {


    }

    @Override
    protected void progressLogic() {
        presenter = new PresenterImpl(this);
        head.put("userId", 0);
        head.put("sessionId", "15320748258726");
        map.put("page", 1);
        map.put("count", 10);
        presenter.startRequest(Contact.POPULAR_MOVIE, head, map, MyHostMovie.class);

    }

    @Override
    protected View getLoadView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.film_pager, null);
        unbinder = ButterKnife.bind(this, view);
        geocodeSearch = new GeocodeSearch(getActivity());
        geocodeSearch.setOnGeocodeSearchListener(this);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.film_pager_get_address)
    public void onViewClicked() {
        CityPicker.getInstance()
                .setFragmentManager(getFragmentManager())  //此方法必须调用
                .enableAnimation(true)  //启用动画效果//自定义动画
                .setLocatedCity(new LocatedCity("杭州", "浙江", "101210101"))  //APP自身已定位的城市，默认为null（定位失败）
                .setHotCities(hotCities)  //指定热门城市
                .setOnPickListener(new OnPickListener() {
                    @Override
                    public void onPick(int position, City data) {
                        Toast.makeText(getActivity(), data.getName(), Toast.LENGTH_SHORT).show();
                        filmPagerGetWei.setText(data.getName());
                        GeocodeQuery query = new GeocodeQuery(filmPagerGetWei.getText().toString(), null);
                        geocodeSearch.getFromLocationNameAsyn(query);
                    }

                    @Override
                    public void onLocate() {
                        //开始定位，这里模拟一下定位
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //定位完成之后更新数据
                                CityPicker.getInstance()
                                        .locateComplete(new LocatedCity("深圳", "广东", "101280601"), LocateState.SUCCESS);
                            }
                        }, 2000);
                    }
                })
                .show();

        presenter.startRequest(Contact.User_NEAR, head, map, MyNearMovie.class);

    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
        Log.e("onGeocodeSearched", "onGeocodeSearched" + geocodeResult.toString());
//从查出来的结果集 得到地址对象
        GeocodeAddress address = geocodeResult.getGeocodeAddressList().get(0);
        //从地址对象里面得到 经纬度的类
        LatLonPoint latLonPoint = address.getLatLonPoint();
        //从这个point取经纬度即可
        filmPagerGetJing.setText("经度是：" + latLonPoint.getLongitude() + ",纬度是：" + latLonPoint.getLatitude());
    }


    @Override
    public void onRequestOk(T data) {
        if (data instanceof MyNearMovie) {
            MyNearMovie myNearMovie = (MyNearMovie) data;
            if (myNearMovie.getMessage().equals("查询成功")) {
                Toast.makeText(getActivity(), myNearMovie.getMessage(), Toast.LENGTH_SHORT).show();
                mList.addAll(myNearMovie.getResult());
            } else {
                Toast.makeText(getActivity(), myNearMovie.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        if (data instanceof MyHostMovie) {
            MyHostMovie myHostMovie = (MyHostMovie) data;
            mHost.addAll(myHostMovie.getResult());
            myHostMovieAdapter.notifyDataSetChanged();
            presenter.startRequest(Contact.NOW_SHOWING_LIST, head, map, MyElseMovie.class);
        }
        if (data instanceof MyElseMovie) {
            MyElseMovie myElseMovie = (MyElseMovie) data;
            mIng.addAll(myElseMovie.getResult());

        }
    }

    @Override
    public void onRequestNo(String error) {
        Log.d("zzz", error);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDetch();
        //unbinder.unbind();
    }


    @OnClick({R.id.tv_search, R.id.ll_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                if (!isExpand) {
                    expand();
                    isExpand = true;
                } else if (isExpand) {
                    reduce();
                    isExpand = false;
                }
                break;
            case R.id.ll_search:
                if (!isExpand) {
                    expand();
                    isExpand = true;
                } else if (isExpand) {
                    reduce();
                    isExpand = false;
                }
                break;
        }
    }

    private void expand() {
        //设置伸展状态时的布局
        //etSearchContent.setHint("搜索你想搜索的内容");
        etSearchContent.setVisibility(View.VISIBLE);
        tvSearch.setVisibility(View.VISIBLE);
        RelativeLayout.LayoutParams LayoutParams = (RelativeLayout.LayoutParams) llSearch.getLayoutParams();
        LayoutParams.width = dip2px(220);
        LayoutParams.setMargins(dip2px(0), dip2px(32), dip2px(0), dip2px(0));
        llSearch.setLayoutParams(LayoutParams);
        //开始动画
        beginDelayedTransition(llSearch);
    }

    private void reduce() {
        //设置收缩状态时的布局
        etSearchContent.setText("");
        etSearchContent.setVisibility(View.GONE);
        tvSearch.setVisibility(View.GONE);
        RelativeLayout.LayoutParams LayoutParams = (RelativeLayout.LayoutParams) llSearch.getLayoutParams();
        LayoutParams.width = dip2px(60);
        LayoutParams.setMargins(dip2px(0), dip2px(32), dip2px(0), dip2px(0));
        llSearch.setLayoutParams(LayoutParams);
        //开始动画
        beginDelayedTransition(llSearch);
    }

    @SuppressLint("NewApi")
    void beginDelayedTransition(ViewGroup view) {
        mSet = new AutoTransition();
        mSet.setDuration(300);
        TransitionManager.beginDelayedTransition(view, mSet);
    }

    private int dip2px(float dpVale) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpVale * scale + 0.5f);
    }



}
