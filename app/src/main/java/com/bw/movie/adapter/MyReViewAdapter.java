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
import com.bw.movie.bean.MyReView;

import java.util.List;

/**
 * <p>文件描述：<p>
 * <p>作者：${小强}<p>
 * <p>创建时间：2019/1/2514:29<p>
 * <p>更改时间：2019/1/2514:29<p>
 * <p>版本号：1<p>
 */
public class MyReViewAdapter extends RecyclerView.Adapter<MyReViewAdapter.Viewholer> {
    private List<MyReView.ResultBean> mList;
    private Context mContext;


    public MyReViewAdapter(List<MyReView.ResultBean> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public Viewholer onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext, R.layout.review_item, null);
        Viewholer viewholer = new Viewholer(view);
        return viewholer;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholer viewholer, final int i) {
        Glide.with(mContext).load(mList.get(i).getCommentHeadPic()).into(viewholer.iv_comment_icon);
        viewholer.iv_comment_title.setText(mList.get(i).getCommentUserName());
        viewholer.tv_comment_content.setText(mList.get(i).getMovieComment());
        viewholer.tv_comment_date.setText("111");
        viewholer.tv_comment_num.setText(mList.get(i).getGreatNum()+"");
        viewholer.tv_comment_message.setText(mList.get(i).getReplyNum()+"");

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class Viewholer extends RecyclerView.ViewHolder {
        private ImageView iv_comment_icon;
        private TextView iv_comment_title;
        private TextView tv_comment_content;
        private TextView tv_comment_date;
        private ImageView iv_comment_praise;
        private TextView tv_comment_num;
        private ImageView iv_comment_message;
        private TextView tv_comment_message;
        public Viewholer(@NonNull View itemView) {
            super(itemView);
            iv_comment_icon=itemView.findViewById(R.id.iv_comment_icon);
            iv_comment_title=itemView.findViewById(R.id.iv_comment_title);
            tv_comment_content=itemView.findViewById(R.id.tv_comment_content);
            tv_comment_date=itemView.findViewById(R.id.tv_comment_date);
            iv_comment_praise=itemView.findViewById(R.id.iv_comment_praise);
            tv_comment_num=itemView.findViewById(R.id.tv_comment_num);
            iv_comment_message=itemView.findViewById(R.id.iv_comment_message);
            tv_comment_message=itemView.findViewById(R.id.tv_comment_message);

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
