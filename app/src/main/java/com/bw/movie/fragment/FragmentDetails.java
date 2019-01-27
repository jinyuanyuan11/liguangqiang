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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.movie.All_Interface.MyInterface;
import com.bw.movie.R;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.bean.MyDetails;
import com.bw.movie.presenter.PresenterImpl;
import com.bw.movie.utils.Contact;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * <p>文件描述：<p>
 * <p>作者：${小强}<p>
 * <p>创建时间：2019/1/279:33<p>
 * <p>更改时间：2019/1/279:33<p>
 * <p>版本号：1<p>
 */
public class FragmentDetails extends BottomSheetDialogFragment  {


    @BindView(R.id.detail_type)
    TextView detailType;
    @BindView(R.id.detail_director)
    TextView detailDirector;
    @BindView(R.id.details_long)
    TextView detailsLong;
    @BindView(R.id.details_address)
    TextView detailsAddress;
    @BindView(R.id.detail_count)
    TextView detailCount;
    @BindView(R.id.details_btn1)
    TextView detailsBtn1;
    @BindView(R.id.details_btn2)
    TextView detailsBtn2;
    @BindView(R.id.details_img)
    ImageView detailsImg;
    Unbinder unbinder;
    @BindView(R.id.predator)
    ImageView predator;
    @BindView(R.id.detail_name1)
    TextView detailName1;
    @BindView(R.id.detail_name2)
    TextView detailName2;
    @BindView(R.id.detail_name3)
    TextView detailName3;
    @BindView(R.id.detail_name4)
    TextView detailName4;
    private View view;
    private BottomSheetBehavior<View> mBottomSheetBehavior;
    private MyDetails myDetails;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.details_fragment, null);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        Glide.with(this).load(myDetails.getResult().getImageUrl()).into(detailsImg);
        detailType.setText(myDetails.getResult().getMovieTypes());
        detailDirector.setText(myDetails.getResult().getDirector());
        detailsLong.setText(myDetails.getResult().getDuration());
        detailsAddress.setText(myDetails.getResult().getPlaceOrigin());
        detailCount.setText(myDetails.getResult().getSummary());
        String m=myDetails.getResult().getStarring();
        String[] split = m.split("\\,");
        detailName1.setText(split[0]);
        detailName2.setText(split[1]);
        detailName3.setText(split[2]);
        detailName4.setText(split[3]);
        return view;
    }





    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getInfo(Object object) {
        myDetails= (MyDetails) object;

    }









    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }







    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback= new BottomSheetBehavior.BottomSheetCallback() {

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
                int height = display.getHeight() * 15/19;
                mBottomSheetBehavior.setPeekHeight(height);
                parent.setBackgroundColor(Color.TRANSPARENT);

            }
        });
    }

}
