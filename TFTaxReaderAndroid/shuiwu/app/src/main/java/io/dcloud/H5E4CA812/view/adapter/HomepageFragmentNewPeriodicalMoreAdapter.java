package io.dcloud.H5E4CA812.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import io.dcloud.H5E4CA812.R;

import io.dcloud.H5E4CA812.view.bean.HomePeriodicalBean;
import io.dcloud.H5E4CA812.util.ImageViewUtil;
import io.dcloud.H5E4CA812.view.widget.OnRecyItemClickListener;
import io.dcloud.H5E4CA812.view.widget.listmovedeletbut.SwipeLayout;

import java.util.List;

public class HomepageFragmentNewPeriodicalMoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements SwipeLayout.OnSwipeStateChangeListener {
    private List<HomePeriodicalBean> mDataList;
    private Context context;

    public HomepageFragmentNewPeriodicalMoreAdapter(List<HomePeriodicalBean> dataList, Context context) {
        this.mDataList = dataList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_homepagefragment_newperiodical_more, parent, false);
        return new RecyclerViewHolder(view);
    }

    public void setmDataList(List<HomePeriodicalBean> mDataList) {
        this.mDataList = mDataList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
        ImageViewUtil.setImage(context, mDataList.get(position).getProdImg(), recyclerViewHolder.image_itme_collectfagment_preriodicalfragment);
        recyclerViewHolder.name_itme_collectfagment_preriodicalfragment.setText(mDataList.get(position).getProdName());
        if (mDataList.get(position).getProdIssue() > 10) {
            recyclerViewHolder.new_periodical_laout.setText(mDataList.get(position).getProdYear() + "年" + mDataList.get(position).getProdIssue() + "期");

        } else {
            recyclerViewHolder.new_periodical_laout.setText(mDataList.get(position).getProdYear() + "年" + "0" + mDataList.get(position).getProdIssue() + "期");
        }
        try {
            recyclerViewHolder.info1_itme_collectfagment_preriodicalfragment.setText(mDataList.get(position).getArticleList().get(0).getArticleTitle());
            recyclerViewHolder.info2_itme_collectfagment_preriodicalfragment.setText(mDataList.get(position).getArticleList().get(1).getArticleTitle());
            recyclerViewHolder.info3_itme_collectfagment_preriodicalfragment.setText(mDataList.get(position).getArticleList().get(2).getArticleTitle());
            recyclerViewHolder.info4_itme_collectfagment_preriodicalfragment.setText(mDataList.get(position).getArticleList().get(3).getArticleTitle());
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

    @Override
    public void onOpen(Object tag) {

    }

    @Override
    public void onClose(Object tag) {

    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView
                name_itme_collectfagment_preriodicalfragment,
                new_periodical_laout,
                info1_itme_collectfagment_preriodicalfragment,
                info2_itme_collectfagment_preriodicalfragment,
                info3_itme_collectfagment_preriodicalfragment,
                info4_itme_collectfagment_preriodicalfragment;
        private ImageView image_itme_collectfagment_preriodicalfragment, image_free;


        RecyclerViewHolder(View itemView) {
            super(itemView);
            name_itme_collectfagment_preriodicalfragment = itemView.findViewById(R.id.name_itme_collectfagment_preriodicalfragment);
            new_periodical_laout = itemView.findViewById(R.id.new_periodical_laout);
            info1_itme_collectfagment_preriodicalfragment = itemView.findViewById(R.id.info1_itme_collectfagment_preriodicalfragment);
            info2_itme_collectfagment_preriodicalfragment = itemView.findViewById(R.id.info2_itme_collectfagment_preriodicalfragment);
            info3_itme_collectfagment_preriodicalfragment = itemView.findViewById(R.id.info3_itme_collectfagment_preriodicalfragment);
            info4_itme_collectfagment_preriodicalfragment = itemView.findViewById(R.id.info4_itme_collectfagment_preriodicalfragment);
            image_itme_collectfagment_preriodicalfragment = itemView.findViewById(R.id.image_itme_collectfagment_preriodicalfragment);
            image_free = itemView.findViewById(R.id.image_free);
        }

    }

    private OnRecyItemClickListener listener;

    //第二步， 写一个公共的方法
    public void setOnItemClickListener(OnRecyItemClickListener listener) {
        this.listener = listener;
    }

}
