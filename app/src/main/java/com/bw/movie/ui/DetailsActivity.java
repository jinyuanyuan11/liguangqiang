package com.bw.movie.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.movie.All_Interface.MyInterface;
import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.bean.MyDetails;
import com.bw.movie.fragment.FragmentDetails;
import com.bw.movie.fragment.FragmentNotice;
import com.bw.movie.fragment.FragmentPhoto;
import com.bw.movie.fragment.FragmentReView;
import com.bw.movie.presenter.PresenterImpl;
import com.bw.movie.utils.Contact;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <p>文件描述：<p>
 * <p>作者：${小强}<p>
 * <p>创建时间：2019/1/2615:16<p>
 * <p>更改时间：2019/1/2615:16<p>
 * <p>版本号：1<p>
 */
public class DetailsActivity<T> extends BaseActivity implements MyInterface.MyView<T> {


    @BindView(R.id.details_movie_image_big)
    ImageView detailsMovieImageBig;
    @BindView(R.id.details_movie_text_name)
    TextView detailsMovieTextName;
    @BindView(R.id.details_lin)
    LinearLayout detailsLin;
    @BindView(R.id.details_movie_button_back)
    Button detailsMovieButtonBack;
    @BindView(R.id.details_movie_button_buy)
    Button detailsMovieButtonBuy;
    @BindView(R.id.details_lin_movie_text_details)
    TextView detailsLinMovieTextDetails;
    @BindView(R.id.details_lin_movie_text_notice)
    TextView detailsLinMovieTextNotice;
    @BindView(R.id.details_lin_movie_text_photo)
    TextView detailsLinMovieTextPhoto;
    @BindView(R.id.details_lin_movie_text_review)
    TextView detailsLinMovieTextReview;

    private List<String>mList=new ArrayList<>();



    private PresenterImpl presenter;
    private HashMap<String, Object> head = new HashMap<>();
    private HashMap<String, Object> map = new HashMap<>();
    private FragmentManager manager;


    @Override
    protected int getLayout() {
        return R.layout.details_item;
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        manager = getSupportFragmentManager();
    }

    @Override
    protected void setOnclick() {


    }

    @Override
    protected void logic() {
        Intent intent = getIntent();
        int key = intent.getIntExtra("key", 0);
        EventBus.getDefault().postSticky(key);
        presenter = new PresenterImpl(this);
        //int userId = SpUtil.getInt("userId", 0);
        //String sessionId=SpUtil.getString("sessionId","");
        //Toast.makeText(this, ""+userId, Toast.LENGTH_SHORT).show();
        head.put("userId", 1910);
        head.put("sessionId", "15484926221381910");
        map.put("movieId", key);
        presenter.startRequest(Contact.MOVIE_DETAILE, head, map, MyDetails.class);
    }


    @Override
    public void onRequestOk(T data) {
        if (data instanceof MyDetails) {
            MyDetails myDetails = (MyDetails) data;
            Glide.with(this).load(myDetails.getResult().getImageUrl()).into(detailsMovieImageBig);
            detailsMovieTextName.setText(myDetails.getResult().getName());
            EventBus.getDefault().postSticky(myDetails.getResult().getShortFilmList().get(0).getVideoUrl());
            EventBus.getDefault().postSticky(myDetails);
            mList.addAll(myDetails.getResult().getPosterList());
            EventBus.getDefault().postSticky(mList);
        }
    }

    @Override
    public void onRequestNo(String error) {
        Log.d("zzz", error + "");
    }


    @OnClick({R.id.details_lin_movie_text_details, R.id.details_lin_movie_text_notice, R.id.details_lin_movie_text_photo, R.id.details_lin_movie_text_review})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.details_lin_movie_text_details:
                FragmentDetails fragmentDetails=new FragmentDetails();
                fragmentDetails.show(getSupportFragmentManager(),"dialog");
                break;
            case R.id.details_lin_movie_text_notice:
                FragmentNotice fragmentNotice=new FragmentNotice();
                fragmentNotice.show(getSupportFragmentManager(),"dialog");
                break;
            case R.id.details_lin_movie_text_photo:
                FragmentPhoto fragmentPhoto=new FragmentPhoto();
                fragmentPhoto.show(getSupportFragmentManager(),"dialog");
                break;
            case R.id.details_lin_movie_text_review:
                FragmentReView fragmentReView=new FragmentReView();
                fragmentReView.show(getSupportFragmentManager(),"dialog");
                break;
        }
    }



}
