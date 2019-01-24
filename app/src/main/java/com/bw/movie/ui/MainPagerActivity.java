package com.bw.movie.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bw.movie.R;
import com.bw.movie.base.BaseImmerse;

public class MainPagerActivity extends AppCompatActivity {

    private BaseImmerse baseImmerse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_pager);
        baseImmerse=new BaseImmerse(MainPagerActivity.this);
        baseImmerse.setImmersion();
        baseImmerse.setRootViewFitsSystemWindows(MainPagerActivity.this,true);
    }
}
