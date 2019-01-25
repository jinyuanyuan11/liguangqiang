package com.bw.movie.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.bw.movie.R;
import com.bw.movie.base.BaseFragment;

/**
 * <p>文件描述：<p>
 * <p>作者：${小强}<p>
 * <p>创建时间：2019/1/2414:26<p>
 * <p>更改时间：2019/1/2414:26<p>
 * <p>版本号：1<p>
 */
public class Fragment_MySelf extends BaseFragment {
    private View view;
    @Override
    protected void initData() {

    }

    @Override
    protected void findViewById(View view) {

    }

    @Override
    protected void getNextData() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void progressLogic() {

    }

    @Override
    protected View getLoadView(LayoutInflater inflater, ViewGroup container) {
        view=inflater.inflate(R.layout.myself_pager,null);
        return view;
    }
}
