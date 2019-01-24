package com.bw.movie.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.base.BaseImmerse;
import com.bw.movie.fragment.Fragment_Cinema;
import com.bw.movie.fragment.Fragment_Film;
import com.bw.movie.fragment.Fragment_MySelf;
import com.bw.movie.wight.CustomViewPager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainPagerActivity extends BaseActivity {

    @BindView(R.id.main_pager_btn1)
    ImageView mainPagerBtn1;
    @BindView(R.id.main_pager_btn2)
    ImageView mainPagerBtn2;
    @BindView(R.id.main_pager_btn3)
    ImageView mainPagerBtn3;
    @BindView(R.id.mian_pager_rel)
    RelativeLayout mianPagerRel;
    @BindView(R.id.main_pager_vp)
    CustomViewPager mainPagerVp;
    private BaseImmerse baseImmerse;

    private FragmentManager manager;
    private Fragment_Film fragment_film;
    private Fragment_Cinema fragmentCinema;
    private Fragment_MySelf fragmentMySelf;

    private List<Fragment> fragments;


    @Override
    protected int getLayout() {
        return R.layout.main_pager;
    }

    @Override
    protected void initViews() {
        //沉浸式
        baseImmerse = new BaseImmerse(MainPagerActivity.this);
        baseImmerse.setImmersion();
        baseImmerse.setRootViewFitsSystemWindows(MainPagerActivity.this, true);
        ButterKnife.bind(this);
        manager = getSupportFragmentManager();


    }

    @Override
    protected void setOnclick() {

    }

    @Override
    protected void logic() {

    }


    @OnClick({R.id.main_pager_btn1, R.id.main_pager_btn2, R.id.main_pager_btn3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_pager_btn1:
                manager.beginTransaction().replace(R.id.main_pager_frg, fragment_film).commit();
                break;
            case R.id.main_pager_btn2:
                manager.beginTransaction().replace(R.id.main_pager_frg, null).commit();
                break;
            case R.id.main_pager_btn3:
                manager.beginTransaction().replace(R.id.main_pager_frg, null).commit();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
