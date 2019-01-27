package com.bw.movie.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.base.BaseFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * <p>文件描述：<p>
 * <p>作者：${小强}<p>
 * <p>创建时间：2019/1/2714:24<p>
 * <p>更改时间：2019/1/2714:24<p>
 * <p>版本号：1<p>
 */
public class FragmentNotice extends BottomSheetDialogFragment {

    @BindView(R.id.jcvideoplayer)
    JZVideoPlayerStandard jcvideoplayer;
    Unbinder unbinder;
    @BindView(R.id.notice_layout_down)
    ImageView noticeLayoutDown;
    private View view;
    private String movieId;
    private BottomSheetBehavior<View> mBottomSheetBehavior;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.notice_layout, null);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        jcvideoplayer.setUp(movieId, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "x");
        //Glide.with(mContext).load(默认图片的网址).crossFade().into(jzVideoPlayer.thumbImageView);
        jcvideoplayer.thumbImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return view;
    }




    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getInfo(Object object) {
        movieId = (String) object;
        Toast.makeText(getActivity(), ""+movieId, Toast.LENGTH_SHORT).show();

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
                int height = display.getHeight() * 14/19;
                mBottomSheetBehavior.setPeekHeight(height);
                parent.setBackgroundColor(Color.TRANSPARENT);

            }
        });
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        jcvideoplayer.releaseAllVideos();
        unbinder.unbind();
    }



    @OnClick(R.id.notice_layout_down)
    public void onViewClicked() {
        jcvideoplayer.releaseAllVideos();


    }


}
