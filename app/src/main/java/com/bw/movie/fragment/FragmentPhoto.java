package com.bw.movie.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.base.BaseFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * <p>文件描述：<p>
 * <p>作者：${小强}<p>
 * <p>创建时间：2019/1/2715:13<p>
 * <p>更改时间：2019/1/2715:13<p>
 * <p>版本号：1<p>
 */
public class FragmentPhoto extends BottomSheetDialogFragment {
    @BindView(R.id.photo_img1)
    ImageView photoImg1;
    @BindView(R.id.photo_img2)
    ImageView photoImg2;
    @BindView(R.id.photo_img3)
    ImageView photoImg3;
    @BindView(R.id.photo_img4)
    ImageView photoImg4;
    @BindView(R.id.photo_img5)
    ImageView photoImg5;
    @BindView(R.id.photo_img6)
    ImageView photoImg6;
    Unbinder unbinder;
    private View view;
    private List<String> movieId;
    private BottomSheetBehavior<View> mBottomSheetBehavior;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.photo_layout, null);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        Glide.with(getActivity()).load(movieId.get(0)).into(photoImg1);
        Glide.with(getActivity()).load(movieId.get(1)).into(photoImg2);
        Glide.with(getActivity()).load(movieId.get(2)).into(photoImg3);
        Glide.with(getActivity()).load(movieId.get(3)).into(photoImg4);
        Glide.with(getActivity()).load(movieId.get(4)).into(photoImg5);
        Glide.with(getActivity()).load(movieId.get(5)).into(photoImg6);
        return view;
    }




    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getInfo(Object object) {
        movieId = (List<String>) object;
        Toast.makeText(getActivity(), "" + movieId, Toast.LENGTH_SHORT).show();

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
                int height = display.getHeight() * 8 / 11;
                mBottomSheetBehavior.setPeekHeight(height);
                parent.setBackgroundColor(Color.TRANSPARENT);

            }
        });
    }





    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
