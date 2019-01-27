package com.bw.movie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bw.movie.All_Interface.MyInterface;
import com.bw.movie.R;
import com.bw.movie.bean.MyElseMovie;
import com.bw.movie.bean.MyHostMovie;
import com.bw.movie.bean.MyNearMovie;
import com.bw.movie.bean.MySoonMovie;
import com.bw.movie.presenter.PresenterImpl;
import com.bw.movie.ui.AllListActivity;
import com.bw.movie.ui.DetailsActivity;
import com.bw.movie.utils.Contact;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import recycler.coverflow.RecyclerCoverFlow;

/**
 * <p>文件描述：<p>
 * <p>作者：${小强}<p>
 * <p>创建时间：2019/1/269:07<p>
 * <p>更改时间：2019/1/269:07<p>
 * <p>版本号：1<p>
 */
public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements MyInterface.MyView {
    private Context mContext;
    private final int TYPE_ONE = 1;
    private final int TYPE_TWO = 2;
    private CinemaFlowAdapter cinemaFlowAdapter;
    private int imgs[] = {R.mipmap.loop1, R.mipmap.loop2, R.mipmap.loop3, R.mipmap.loop4, R.mipmap.loop5, R.mipmap.loop7, R.mipmap.loop8, R.mipmap.loop10, R.mipmap.loop11};
    private List<Integer> mImg = new ArrayList<>();
    boolean isExpand = false;
    private PresenterImpl presenter;
    private HashMap<String, Object> head = new HashMap<>();
    private HashMap<String, Object> map = new HashMap<>();
    private List<MyHostMovie.ResultBean> mHost = new ArrayList<>();
    private List<MyElseMovie.ResultBean> mIng = new ArrayList<>();
    private List<MySoonMovie.ResultBean> mSoon = new ArrayList<>();
    private List<MyNearMovie.ResultBean> mList = new ArrayList<>();

    private MyHostMovieAdapter myHostMovieAdapter;
    private MIngMovieAdapter mIngMovieAdapter;
    private MySoonMovieAdapter mySoonMovieAdapter;



    public MainAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.mContext = viewGroup.getContext();
        View view = null;
        RecyclerView.ViewHolder holder = null;
        switch (i) {
            case TYPE_ONE:
                view = View.inflate(viewGroup.getContext(), R.layout.film_recy_movie, null);
                holder = new HolderOne(view);
                break;
            case TYPE_TWO:
                view = View.inflate(viewGroup.getContext(), R.layout.film_loop, null);
                holder = new HolderTwo(view);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        if (viewHolder instanceof HolderOne) {
            final LinearLayoutManager manager1 = new LinearLayoutManager(mContext);
            manager1.setOrientation(LinearLayoutManager.HORIZONTAL);
            ((HolderOne) viewHolder).host_recy.setLayoutManager(manager1);
            myHostMovieAdapter = new MyHostMovieAdapter(mHost, mContext);
            ((HolderOne) viewHolder).host_recy.setAdapter(myHostMovieAdapter);
            myHostMovieAdapter.setCheckItem(new MyHostMovieAdapter.CheckItem() {
                @Override
                public void setItemCheck(View view, int i) {
                    Intent intent = new Intent(mContext, DetailsActivity.class);
                    intent.putExtra("key", i);
                    mContext.startActivity(intent);
                }
            });

            LinearLayoutManager manager2 = new LinearLayoutManager(mContext);
            manager2.setOrientation(LinearLayoutManager.HORIZONTAL);
            ((HolderOne) viewHolder).Ing_recy.setLayoutManager(manager2);
            mIngMovieAdapter = new MIngMovieAdapter(mIng, mContext);
            ((HolderOne) viewHolder).Ing_recy.setAdapter(mIngMovieAdapter);
            mIngMovieAdapter.setCheckItem(new MIngMovieAdapter.CheckItem() {
                @Override
                public void setItemCheck(View view, int i) {
                    Intent intent = new Intent(mContext, DetailsActivity.class);
                    intent.putExtra("key", i);
                    mContext.startActivity(intent);
                }
            });

            LinearLayoutManager manager3 = new LinearLayoutManager(mContext);
            manager3.setOrientation(LinearLayoutManager.HORIZONTAL);
            ((HolderOne) viewHolder).soon_recy.setLayoutManager(manager3);
            mySoonMovieAdapter = new MySoonMovieAdapter(mSoon, mContext);
            ((HolderOne) viewHolder).soon_recy.setAdapter(mySoonMovieAdapter);
            mySoonMovieAdapter.setCheckItem(new MySoonMovieAdapter.CheckItem() {
                @Override
                public void setItemCheck(View view, int i) {
                    Intent intent = new Intent(mContext, DetailsActivity.class);
                    intent.putExtra("key", i);
                    mContext.startActivity(intent);
                }
            });

            presenter = new PresenterImpl(this);
            head.put("userId", 0);
            head.put("sessionId", "15320748258726");
            map.put("page", 1);
            map.put("count", 10);
            presenter.startRequest(Contact.POPULAR_MOVIE, head, map, MyHostMovie.class);
            presenter.startRequest(Contact.NOW_SHOWING_LIST, head, map, MyElseMovie.class);
            presenter.startRequest(Contact.COMING_SHOWING_LIST, head, map, MySoonMovie.class);

            ((HolderOne) viewHolder).all_host.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, AllListActivity.class);
                    intent.putExtra("zhi", 1);
                    mContext.startActivity(intent);
                }
            });

            ((HolderOne) viewHolder).all_ing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, AllListActivity.class);
                    intent.putExtra("zhi", 2);
                    mContext.startActivity(intent);
                }
            });

            ((HolderOne) viewHolder).all_now.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, AllListActivity.class);
                    intent.putExtra("zhi", 3);
                    mContext.startActivity(intent);
                }
            });

        } else {
            for (int j = 0; j < imgs.length; j++) {
                mImg.add(imgs[j]);
            }
            cinemaFlowAdapter = new CinemaFlowAdapter(mContext, mImg);
            ((HolderTwo) viewHolder).rcf_cinema_flow.setAdapter(cinemaFlowAdapter);
            ((HolderTwo) viewHolder).rcf_cinema_flow.smoothScrollToPosition(imgs.length / 2);
            ((HolderTwo) viewHolder).ll_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkItem != null) {
                        checkItem.setItemCheck(v);
                    }
                }
            });


        }
        viewHolder.itemView.setTag(i);
    }


    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 1) {
            return TYPE_ONE;
        } else {
            return TYPE_TWO;
        }
    }

    @Override
    public void onRequestOk(Object data) {
        if (data instanceof MyNearMovie) {
            MyNearMovie myNearMovie = (MyNearMovie) data;
            if (myNearMovie.getMessage().equals("查询成功")) {
                Toast.makeText(mContext, myNearMovie.getMessage(), Toast.LENGTH_SHORT).show();
                mList.addAll(myNearMovie.getResult());
            } else {
                Toast.makeText(mContext, myNearMovie.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        if (data instanceof MyHostMovie) {
            MyHostMovie myHostMovie = (MyHostMovie) data;
            mHost.addAll(myHostMovie.getResult());
            myHostMovieAdapter.notifyDataSetChanged();
        }
        if (data instanceof MyElseMovie) {
            MyElseMovie myElseMovie = (MyElseMovie) data;
            mIng.addAll(myElseMovie.getResult());
            mIngMovieAdapter.notifyDataSetChanged();
        }
        if (data instanceof MySoonMovie) {
            MySoonMovie myElseMovie = (MySoonMovie) data;
            mSoon.addAll(myElseMovie.getResult());
            mySoonMovieAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRequestNo(String error) {

    }

    class HolderTwo extends RecyclerView.ViewHolder {
        private RecyclerCoverFlow rcf_cinema_flow;
        private LinearLayout ll_search;


        public HolderTwo(@NonNull View itemView) {
            super(itemView);
            rcf_cinema_flow = itemView.findViewById(R.id.rcf_cinema_flow);
            ll_search = itemView.findViewById(R.id.ll_search);

        }
    }

    class HolderOne extends RecyclerView.ViewHolder {
        private RecyclerView host_recy;
        private RecyclerView Ing_recy;
        private RecyclerView soon_recy;
        private ImageView all_host;
        private ImageView all_ing;
        private ImageView all_now;

        public HolderOne(@NonNull View itemView) {
            super(itemView);
            host_recy = itemView.findViewById(R.id.host_recy);
            Ing_recy = itemView.findViewById(R.id.Ing_recy);
            soon_recy = itemView.findViewById(R.id.soon_recy);
            all_host = itemView.findViewById(R.id.all_host);
            all_ing=itemView.findViewById(R.id.all_ing);
            all_now=itemView.findViewById(R.id.all_now);
        }
    }

    public interface CheckItem {
        void setItemCheck(View view);
    }

    private CheckItem checkItem;

    public void setCheckItem(CheckItem checkItem) {
        this.checkItem = checkItem;
    }


}
