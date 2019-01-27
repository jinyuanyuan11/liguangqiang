package com.bw.movie.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {
    private boolean isInitView = false;
    private boolean isVisible = false;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        isInitView = true;
        isCanLoadData();
        return initView(inflater,container);
    }


    protected abstract void initData();
    
    protected abstract void findViewById(View view);
    
    protected abstract void getNextData();
    
    protected abstract void setListener();
    
    protected abstract void progressLogic();
    
    

    View initView(LayoutInflater inflater, ViewGroup container)
    {
        View loading=getLoadView(inflater,container);
        if(loading!=null)
        {
            findViewById(loading);
            initData();
            getNextData();
            setListener();
            progressLogic();
        }
        return loading;
    }

    protected abstract View getLoadView(LayoutInflater inflater, ViewGroup container);


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见，获取该标志记录下来
        if(isVisibleToUser){
            isVisible = true;
            isCanLoadData();
        }else{
            isVisible = false;
        }
    }

    private void isCanLoadData(){
        //所以条件是view初始化完成并且对用户可见
        if(isInitView && isVisible ){
            progressLogic();

            //防止重复加载数据
            isInitView = false;
            isVisible = false;
        }
    }

    /**
     * 加载页面布局文件
     * @return
     */


    /**
     * 让布局中的view与fragment中的变量建立起映射
     */


    /**
     * 加载要显示的数据
     */



}
