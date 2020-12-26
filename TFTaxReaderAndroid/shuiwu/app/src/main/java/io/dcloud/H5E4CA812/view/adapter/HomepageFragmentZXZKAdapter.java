package io.dcloud.H5E4CA812.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import io.dcloud.H5E4CA812.R;
import io.dcloud.H5E4CA812.util.ImageViewUtil;
import io.dcloud.H5E4CA812.view.bean.HomePeriodicalBean;
import io.dcloud.H5E4CA812.view.widget.OnRecyItemClickListener;

import java.util.List;

/**
 * 首页最新期刊
 */

public class HomepageFragmentZXZKAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<HomePeriodicalBean> mDataList;
    Context context;

    public HomepageFragmentZXZKAdapter(List<HomePeriodicalBean> dataList, Context context) {
        this.mDataList = dataList;
        this.context = context;
    }

    public void setmDataList(List<HomePeriodicalBean> mDataList) {
        this.mDataList = mDataList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_hompagefragment_zxzk, parent, false);

        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
        ImageViewUtil.setImage(context, mDataList.get(position).getProdImg(), recyclerViewHolder.new_periodical_image);
        recyclerViewHolder.new_periodical_name.setText(mDataList.get(position).getProdName());
        recyclerViewHolder.new_periodical_num.setText(mDataList.get(position).getProdYear() + "");
        try {
            recyclerViewHolder.new_periodical_info1.setText(mDataList.get(position).getArticleList().get(0).getArticleTitle());
            recyclerViewHolder.new_periodical_info2.setText(mDataList.get(position).getArticleList().get(1).getArticleTitle());
            recyclerViewHolder.new_periodical_info3.setText(mDataList.get(position).getArticleList().get(2).getArticleTitle());
            recyclerViewHolder.new_periodical_info4.setText(mDataList.get(position).getArticleList().get(3).getArticleTitle());

        } catch (Exception e) {

        }
        if (mDataList.get(position).isProdIsFree()) {
            recyclerViewHolder.image_free.setVisibility(View.VISIBLE);
        } else {
            recyclerViewHolder.image_free.setVisibility(View.GONE);
        }
        recyclerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(v, position);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView new_periodical_name, new_periodical_num, new_periodical_info1, new_periodical_info2,
                new_periodical_info3, new_periodical_info4;
        private ImageView new_periodical_image, image_free;


        RecyclerViewHolder(View itemView) {
            super(itemView);
            new_periodical_name = itemView.findViewById(R.id.new_periodical_name);
            new_periodical_num = itemView.findViewById(R.id.new_periodical_num);
            new_periodical_info1 = itemView.findViewById(R.id.new_periodical_info1);
            new_periodical_info2 = itemView.findViewById(R.id.new_periodical_info2);
            new_periodical_info3 = itemView.findViewById(R.id.new_periodical_info3);
            new_periodical_info4 = itemView.findViewById(R.id.new_periodical_info4);
            new_periodical_image = itemView.findViewById(R.id.new_periodical_image);
            image_free = itemView.findViewById(R.id.image_free);

        }
    }

    private OnRecyItemClickListener listener;

    //第二步， 写一个公共的方法
    public void setOnItemClickListener(OnRecyItemClickListener listener) {
        this.listener = listener;
    }

}
