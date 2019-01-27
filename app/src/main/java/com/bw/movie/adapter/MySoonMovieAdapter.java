package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.bean.MyElseMovie;
import com.bw.movie.bean.MySoonMovie;

import java.util.List;

/**
 * <p>文件描述：<p>
 * <p>作者：${小强}<p>
 * <p>创建时间：2019/1/2514:29<p>
 * <p>更改时间：2019/1/2514:29<p>
 * <p>版本号：1<p>
 */
public class MySoonMovieAdapter extends RecyclerView.Adapter<MySoonMovieAdapter.Viewholer> {
    private List<MySoonMovie.ResultBean>mList;
    private Context mContext;

    public MySoonMovieAdapter(List<MySoonMovie.ResultBean> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MySoonMovieAdapter.Viewholer onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=View.inflate(mContext, R.layout.host_movie_item,null);
        Viewholer viewholer=new Viewholer(view);
        return viewholer;
    }

    @Override
    public void onBindViewHolder(@NonNull MySoonMovieAdapter.Viewholer viewholer, final int i) {
        Glide.with(mContext).load(mList.get(i).getImageUrl()).into(viewholer.host_movie_img);
        viewholer.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkItem!=null)
                {
                    checkItem.setItemCheck(v,mList.get(i).getId());
                }
            }
        });
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

    public interface CheckItem {
        void setItemCheck(View view,int i);
    }

    private CheckItem checkItem;

    public void setCheckItem(CheckItem checkItem) {
        this.checkItem = checkItem;
    }
}
