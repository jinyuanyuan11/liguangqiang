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
import com.bw.movie.bean.MyHostMovie;

import java.util.List;

/**
 * <p>文件描述：<p>
 * <p>作者：${小强}<p>
 * <p>创建时间：2019/1/2514:29<p>
 * <p>更改时间：2019/1/2514:29<p>
 * <p>版本号：1<p>
 */
public class MyAllHostAdapter extends RecyclerView.Adapter<MyAllHostAdapter.Viewholer> {
    private List<MyHostMovie.ResultBean>mList;
    private Context mContext;


    public MyAllHostAdapter(List<MyHostMovie.ResultBean> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyAllHostAdapter.Viewholer onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=View.inflate(mContext, R.layout.all_list_layout_item,null);
        Viewholer viewholer=new Viewholer(view);
        return viewholer;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAllHostAdapter.Viewholer viewholer, final int i) {
        Glide.with(mContext).load(mList.get(i).getImageUrl()).into(viewholer.all_list_layout_img);
        viewholer.all_list_layout_title.setText(mList.get(i).getName());
        viewholer.all_list_layout_count.setText(mList.get(i).getSummary());
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
        private ImageView all_list_layout_img;
        private TextView all_list_layout_title;
        private TextView all_list_layout_count;
        public Viewholer(@NonNull View itemView) {
            super(itemView);
            all_list_layout_img=itemView.findViewById(R.id.all_list_layout_img);
            all_list_layout_title=itemView.findViewById(R.id.all_list_layout_title);
            all_list_layout_count=itemView.findViewById(R.id.all_list_layout_count);
        }
    }

    public interface CheckItem {
        void setItemCheck(View view, int i);
    }

    private CheckItem checkItem;

    public void setCheckItem(CheckItem checkItem) {
        this.checkItem = checkItem;
    }
}
