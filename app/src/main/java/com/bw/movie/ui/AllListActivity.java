package com.bw.movie.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.All_Interface.MyInterface;
import com.bw.movie.R;
import com.bw.movie.adapter.MIngMovieAdapter;
import com.bw.movie.adapter.MyAllElseAdapter;
import com.bw.movie.adapter.MyAllHostAdapter;
import com.bw.movie.adapter.MyAllNowAdapter;
import com.bw.movie.adapter.MySoonMovieAdapter;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.bean.MyElseMovie;
import com.bw.movie.bean.MyHostMovie;
import com.bw.movie.bean.MySoonMovie;
import com.bw.movie.presenter.PresenterImpl;
import com.bw.movie.utils.Contact;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <p>文件描述：<p>
 * <p>作者：${小强}<p>
 * <p>创建时间：2019/1/2618:38<p>
 * <p>更改时间：2019/1/2618:38<p>
 * <p>版本号：1<p>
 */
public class AllListActivity extends BaseActivity implements MyInterface.MyView {
    @BindView(R.id.all_list_address)
    ImageView allListAddress;
    @BindView(R.id.et_search_content)
    EditText etSearchContent;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.all_list_Host)
    RadioButton allListHost;
    @BindView(R.id.all_list_Ing)
    RadioButton allListIng;
    @BindView(R.id.all_list_Now)
    RadioButton allListNow;
    @BindView(R.id.all_list)
    XRecyclerView allList;

    private PresenterImpl presenter;
    private MyAllHostAdapter myAllHostAdapter;
    private MyAllElseAdapter myAllElseAdapter;
    private MyAllNowAdapter myAllNowAdapter;
    private MySoonMovieAdapter mySoonMovieAdapter;
    private HashMap<String, Object> head = new HashMap<>();
    private HashMap<String, Object> map = new HashMap<>();
    private List<MyHostMovie.ResultBean> mHost = new ArrayList<>();
    private List<MyElseMovie.ResultBean> mIng = new ArrayList<>();
    private List<MySoonMovie.ResultBean> mSoon = new ArrayList<>();
    private FragmentManager manager;

    @Override
    protected int getLayout() {
        return R.layout.all_list_layout;
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        myAllHostAdapter = new MyAllHostAdapter(mHost, this);
        myAllElseAdapter = new MyAllElseAdapter(mIng,this);
        myAllNowAdapter = new MyAllNowAdapter(mSoon,this);
        Intent intent=getIntent();
        int zhi = intent.getIntExtra("zhi", 0);
        manager = getSupportFragmentManager();
        presenter=new PresenterImpl(this);
        head.put("userId", 1910);
        head.put("sessionId", "15485097298621910");
        map.put("page", 1);
        map.put("count", 10);
        switch (zhi)
        {
            case 1:
                LinearLayoutManager manager1 = new LinearLayoutManager(this);
                allList.setLayoutManager(manager1);

                allList.setAdapter(myAllHostAdapter);
                presenter.startRequest(Contact.POPULAR_MOVIE, head, map, MyHostMovie.class);
                break;
            case 2:
                LinearLayoutManager manager2 = new LinearLayoutManager(this);
                allList.setLayoutManager(manager2);

                allList.setAdapter(myAllElseAdapter);
                presenter.startRequest(Contact.NOW_SHOWING_LIST, head, map, MyElseMovie.class);
                break;
                case 3:
                    LinearLayoutManager manager3 = new LinearLayoutManager(this);
                    allList.setLayoutManager(manager3);
                    allList.setAdapter(myAllNowAdapter);
                    presenter.startRequest(Contact.COMING_SHOWING_LIST,head,map,MySoonMovie.class);
                    break;
        }
    }

    @Override
    protected void setOnclick() {

        myAllHostAdapter.setCheckItem(new MyAllHostAdapter.CheckItem() {
            @Override
            public void setItemCheck(View view, int i) {
                Intent intent=new Intent(AllListActivity.this,DetailsActivity.class);
                intent.putExtra("key",i);
                startActivity(intent);
            }
        });

        myAllElseAdapter.setCheckItem(new MyAllElseAdapter.CheckItem() {
            @Override
            public void setItemCheck(View view, int i) {
                Intent intent=new Intent(AllListActivity.this,DetailsActivity.class);
                intent.putExtra("key",i);
                startActivity(intent);
            }
        });

        myAllNowAdapter.setCheckItem(new MyAllNowAdapter.CheckItem() {
            @Override
            public void setItemCheck(View view, int i) {
                Intent intent=new Intent(AllListActivity.this,DetailsActivity.class);
                intent.putExtra("key",i);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void logic() {

    }


    @OnClick({R.id.all_list_address, R.id.tv_search, R.id.ll_search, R.id.all_list_Host, R.id.all_list_Ing, R.id.all_list_Now})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.all_list_address:
                break;
            case R.id.tv_search:
                break;
            case R.id.ll_search:
                break;
            case R.id.all_list_Host:
                LinearLayoutManager manager1 = new LinearLayoutManager(this);
                allList.setLayoutManager(manager1);
                allList.setAdapter(myAllHostAdapter);
                presenter.startRequest(Contact.POPULAR_MOVIE, head, map, MyHostMovie.class);
                break;
            case R.id.all_list_Ing:
               LinearLayoutManager manager2 = new LinearLayoutManager(this);
                allList.setLayoutManager(manager2);
                allList.setAdapter(myAllElseAdapter);
                presenter.startRequest(Contact.NOW_SHOWING_LIST, head, map, MyElseMovie.class);
                break;
            case R.id.all_list_Now:
                LinearLayoutManager manager3 = new LinearLayoutManager(this);
                allList.setLayoutManager(manager3);
                myAllNowAdapter = new MyAllNowAdapter(mSoon,this);
                allList.setAdapter(myAllNowAdapter);
                presenter.startRequest(Contact.COMING_SHOWING_LIST,head,map,MySoonMovie.class);
                break;
        }
    }


    @Override
    public void onRequestOk(Object data) {
        if(data instanceof MyHostMovie)
        {
            mIng.clear();
            mSoon.clear();
            MyHostMovie myHostMovie = (MyHostMovie) data;
            mHost.addAll(myHostMovie.getResult());
            myAllHostAdapter.notifyDataSetChanged();
        }
        if(data instanceof MyElseMovie)
        {
            mHost.clear();
            mSoon.clear();
            MyElseMovie myElseMovie = (MyElseMovie) data;
            mIng.addAll(myElseMovie.getResult());
            myAllElseAdapter.notifyDataSetChanged();
        }
        if(data instanceof MySoonMovie)
        {
            mHost.clear();
            mIng.clear();
            MySoonMovie myElseMovie = (MySoonMovie) data;
            mSoon.addAll(myElseMovie.getResult());
            myAllNowAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRequestNo(String error) {

    }
}
