package com.bw.movie.fragment;


import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.All_Interface.MyInterface;
import com.bw.movie.R;
import com.bw.movie.adapter.MainAdapter;
import com.bw.movie.adapter.MyHostMovieAdapter;
import com.bw.movie.base.BaseFragment;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * <p>文件描述：<p>
 * <p>作者：${小强}<p>
 * <p>创建时间：2019/1/2414:25<p>
 * <p>更改时间：2019/1/2414:25<p>
 * <p>版本号：1<p>
 */
public class Fragment_Film<T> extends BaseFragment implements MyInterface.MyView<T> {



    @BindView(R.id.film_pager_recy)
    XRecyclerView filmPagerRecy;
    Unbinder unbinder;
    private MainAdapter mainAdapter;
    private EditText et_search_content;
    private ImageButton film_pager_get_address;
    private TextView film_pager_get_jing;
    private TextView tv_search;
    private LinearLayout ll_search;
    boolean isExpand = false;
    private TransitionSet mSet;
    private MyHostMovieAdapter myHostMovieAdapter;

    @Override
    public void onRequestOk(T data) {

    }

    @Override
    public void onRequestNo(String error) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void findViewById(View view) {
        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        filmPagerRecy.setLayoutManager(manager);
        mainAdapter=new MainAdapter(getActivity());
        filmPagerRecy.setAdapter(mainAdapter);
    }

    @Override
    protected void getNextData() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void progressLogic() {
        mainAdapter.setCheckItem(new MainAdapter.CheckItem() {
            @Override
            public void setItemCheck(View view) {
                et_search_content=view.findViewById(R.id.et_search_content);
                film_pager_get_address=view.findViewById(R.id.film_pager_get_address);
                film_pager_get_jing=view.findViewById(R.id.film_pager_get_jing);
                tv_search = view.findViewById(R.id.tv_search);
                ll_search = view.findViewById(R.id.ll_search);
                tv_search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!isExpand) {
                            expand();
                            isExpand = true;
                        } else if (isExpand) {
                            reduce();
                            isExpand = false;
                        }
                    }
                });

                ll_search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!isExpand) {

                            expand();
                            isExpand = true;
                        } else if (isExpand) {
                            reduce();
                            isExpand = false;
                        }
                    }
                });
            }
        });



    }

    @Override
    protected View getLoadView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.film_pager, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void expand() {
        //设置伸展状态时的布局
        //etSearchContent.setHint("搜索你想搜索的内容");
        et_search_content.setVisibility(View.VISIBLE);
        tv_search.setVisibility(View.VISIBLE);
        RelativeLayout.LayoutParams LayoutParams = (RelativeLayout.LayoutParams) ll_search.getLayoutParams();
        LayoutParams.width = dip2px(220);
        LayoutParams.setMargins(dip2px(0), dip2px(32), dip2px(0), dip2px(0));
        ll_search.setLayoutParams(LayoutParams);
        //开始动画
        beginDelayedTransition(ll_search,mSet);

    }


    private void reduce() {
        //设置收缩状态时的布局
        et_search_content.setText("");
        et_search_content.setVisibility(View.GONE);
        tv_search.setVisibility(View.GONE);
        RelativeLayout.LayoutParams LayoutParams = (RelativeLayout.LayoutParams) ll_search.getLayoutParams();
        LayoutParams.width = dip2px(60);
        LayoutParams.setMargins(dip2px(0), dip2px(32), dip2px(0), dip2px(0));
        ll_search.setLayoutParams(LayoutParams);
        //开始动画
        beginDelayedTransition(ll_search,mSet);
    }

    @SuppressLint("NewApi")
    void beginDelayedTransition(View view, TransitionSet mSet) {
        this.mSet = new AutoTransition();
        this.mSet.setDuration(300);
        TransitionManager.beginDelayedTransition((ViewGroup) view,mSet);
    }

    private int dip2px(float dpVale) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpVale * scale + 0.5f);
    }
}
