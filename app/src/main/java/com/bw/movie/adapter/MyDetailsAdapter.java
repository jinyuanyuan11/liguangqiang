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
import com.bw.movie.bean.MyDetails;

import java.util.List;

/**
 * <p>文件描述：<p>
 * <p>作者：${小强}<p>
 * <p>创建时间：2019/1/2514:29<p>
 * <p>更改时间：2019/1/2514:29<p>
 * <p>版本号：1<p>
 */
public class MyDetailsAdapter extends RecyclerView.Adapter<MyDetailsAdapter.Viewholer> {
    private List<Object> mList;
    private Context mContext;


    public MyDetailsAdapter(List<Object> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Viewholer onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext, R.layout.details_fragment, null);
        Viewholer viewholer = new Viewholer(view);
        return viewholer;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholer viewholer, int i) {
        Glide.with(mContext).load(mList.get(0)).into(viewholer.details_img);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class Viewholer extends RecyclerView.ViewHolder {
        private TextView detail_type;
        private TextView detail_director;
        private TextView details_long;
        private TextView details_address;
        private TextView detail_count;
        private TextView details_btn1;
        private TextView details_btn2;
        private ImageView details_img;

        public Viewholer(@NonNull View itemView) {
            super(itemView);
            details_img = itemView.findViewById(R.id.details_img);
        }
    }
}
