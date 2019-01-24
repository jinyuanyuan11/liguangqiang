package com.bw.movie.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * <p>文件描述：<p>
 * <p>作者：${小强}<p>
 * <p>创建时间：2019/1/2319:41<p>
 * <p>更改时间：2019/1/2319:41<p>
 * <p>版本号：1<p>
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    protected abstract int getLayout();

    protected abstract void initViews();

    protected abstract void setOnclick();

    protected abstract void logic();

    void init()
    {
        if(getLayout()!=0)
        {
            setContentView(getLayout());
            initViews();
            setOnclick();
            logic();
        }
    }
}
