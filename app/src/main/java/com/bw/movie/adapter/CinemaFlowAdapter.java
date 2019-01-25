package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>文件描述：<p>
 * <p>作者：${小强}<p>
 * <p>创建时间：2019/1/2418:19<p>
 * <p>更改时间：2019/1/2418:19<p>
 * <p>版本号：1<p>
 */
public class CinemaFlowAdapter extends RecyclerView.Adapter<CinemaFlowAdapter.Viewholer> {

    private Context mContext;

    private List<Integer> list = new ArrayList<>();


    public CinemaFlowAdapter(Context mContext,List<Integer>list) {
        this.mContext = mContext;
        this.list=list;
    }



    @NonNull
    @Override
    public Viewholer onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext, R.layout.loop_movie, null);
        Viewholer viewholer = new Viewholer(view);
        return viewholer;
    }



    @Override
    public void onBindViewHolder(@NonNull Viewholer viewholer, int i) {
        Glide.with(mContext).load(list.get(i)).into(viewholer.simpleDraweeView);
        viewholer.text_cinema_flow1.setText("电影时长：");
        viewholer.text_cinema_flow2.setText("120分钟");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholer extends RecyclerView.ViewHolder {
        private ImageView simpleDraweeView;
        private TextView text_cinema_flow1;
        private TextView text_cinema_flow2;
        public Viewholer(@NonNull View itemView) {
            super(itemView);
            simpleDraweeView = itemView.findViewById(R.id.simp_cinema_flow);
            text_cinema_flow1= itemView.findViewById(R.id.text_cinema_flow1);
            text_cinema_flow2= itemView.findViewById(R.id.text_cinema_flow2);


        }
    }
}
