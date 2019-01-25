package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.bean.MyHostMovie;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>文件描述：<p>
 * <p>作者：${小强}<p>
 * <p>创建时间：2019/1/2514:29<p>
 * <p>更改时间：2019/1/2514:29<p>
 * <p>版本号：1<p>
 */
public class MyHostMovieAdapter extends RecyclerView.Adapter<MyHostMovieAdapter.Viewholer> {
    private List<MyHostMovie.ResultBean>mList;
    private Context mContext;

    public MyHostMovieAdapter(List<MyHostMovie.ResultBean> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyHostMovieAdapter.Viewholer onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //View view=LayoutInflater.from(mContext).inflate(R.layout.host_movie_item,null,false);
        View view=View.inflate(mContext, R.layout.host_movie_item,null);
        Viewholer viewholer=new Viewholer(view);
        return viewholer;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHostMovieAdapter.Viewholer viewholer, int i) {
        Glide.with(mContext).load(mList.get(i).getImageUrl()).into(viewholer.host_movie_img);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class Viewholer extends RecyclerView.ViewHolder {
        private ImageView host_movie_img;
        public Viewholer(@NonNull View itemView) {
            super(itemView);
            host_movie_img=itemView.findViewById(R.id.host_movie_img);
        }
    }
}
