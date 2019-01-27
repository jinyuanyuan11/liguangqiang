package com.bw.movie.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bw.movie.All_Interface.MyInterface;
import com.bw.movie.R;
import com.bw.movie.adapter.MyReViewAdapter;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.bean.MyReView;
import com.bw.movie.presenter.PresenterImpl;
import com.bw.movie.utils.Contact;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * <p>文件描述：<p>
 * <p>作者：${小强}<p>
 * <p>创建时间：2019/1/2716:18<p>
 * <p>更改时间：2019/1/2716:18<p>
 * <p>版本号：1<p>
 */
public class FragmentReView extends BottomSheetDialogFragment implements MyInterface.MyView {
    @BindView(R.id.review_recy)
    XRecyclerView reviewRecy;
    Unbinder unbinder;
    private BottomSheetBehavior<View> mBottomSheetBehavior;
    private View view;
    private PresenterImpl presenter;
    private List<MyReView.ResultBean> mList = new ArrayList<>();
    private MyReViewAdapter myReViewAdapter;
    private HashMap<String, Object> head = new HashMap<>();
    private HashMap<String, Object> map = new HashMap<>();
    private int movieId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.review_layout, null);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        reviewRecy.setLayoutManager(manager);

        myReViewAdapter = new MyReViewAdapter(mList, getActivity());
        reviewRecy.setAdapter(myReViewAdapter);

        presenter = new PresenterImpl(this);
        head.put("userId", 1910);
        head.put("sessionId", "15484926221381910");
        map.put("movieId", movieId);
        map.put("page", 1);
        map.put("count", 5);
        presenter.startRequest(Contact.MOVIE_COMMENT, head, map, MyReView.class);
        return view;
    }







    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getInfo(Object object) {
        movieId = (int) object;

    }



    @Override
    public void onRequestOk(Object data) {
        if (data instanceof MyReView) {
            MyReView myReView = (MyReView) data;
            mList.addAll(myReView.getResult());
            myReViewAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRequestNo(String error) {

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback
            = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            //禁止拖拽，
            if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                //设置为收缩状态
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }

        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };

    /**
     * 设置高度
     */
    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();

        if (dialog != null) {
            View bottomSheet = dialog.findViewById(R.id.design_bottom_sheet);
            bottomSheet.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
        }
        final View view = getView();
        view.post(new Runnable() {
            @Override
            public void run() {
                View parent = (View) view.getParent();
                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) (parent).getLayoutParams();
                CoordinatorLayout.Behavior behavior = params.getBehavior();
                mBottomSheetBehavior = (BottomSheetBehavior) behavior;
                mBottomSheetBehavior.setBottomSheetCallback(mBottomSheetBehaviorCallback);
                Display display = getActivity().getWindowManager().getDefaultDisplay();
                //设置高度
                int height = display.getHeight() *14/19;
                mBottomSheetBehavior.setPeekHeight(height);
                parent.setBackgroundColor(Color.TRANSPARENT);

            }
        });
    }
}
