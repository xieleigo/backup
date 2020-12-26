package io.dcloud.H5E4CA812.view.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import io.dcloud.H5E4CA812.view.activity.QKDetailsSearchActivity;
import io.dcloud.H5E4CA812.R;

import io.dcloud.H5E4CA812.util.ImageViewUtil;
import io.dcloud.H5E4CA812.view.widget.OnRecyItemClickListener;

import java.util.List;

/**
 * 详情搜索期刊
 */

public class QKDetailsQKAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<QKDetailsSearchActivity.YearBeanDetails> mDataList;
    private Activity context;

    public QKDetailsQKAdapter(Activity context, List<QKDetailsSearchActivity.YearBeanDetails> dataList) {
        this.mDataList = dataList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_qkdetails_qk, parent, false);
        return new RecyclerViewHolder(view);
    }

    public void setmDataList(List<QKDetailsSearchActivity.YearBeanDetails> mDataList) {
        this.mDataList = mDataList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
        ImageViewUtil.setPHBImage(context, "", recyclerViewHolder.phb_image);
        recyclerViewHolder.phb_text.setText(mDataList.get(position).getProdYear() + "年 第" + mDataList.get(position).getProdIssue() + "期");
        if (mDataList.get(position).isProdIsFree()) {
            recyclerViewHolder.phb_image_free.setVisibility(View.VISIBLE);
        } else {
            recyclerViewHolder.phb_image_free.setVisibility(View.GONE);
        }
        ImageViewUtil.setPHBImage(context, mDataList.get(position).getProdImg(), recyclerViewHolder.phb_image);
        recyclerViewHolder.phb_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("ReadSourceID", mDataList.get(position).getReadSourceID());
                context.setResult(100, intent);
                context.finish();
            }
        });


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
        if (mDataList != null) {
            return mDataList.size();
        }
        return 0;
    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView phb_text;
        private ImageView phb_image, phb_image_free;
        private RelativeLayout phb_layout;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            phb_layout = itemView.findViewById(R.id.phb_layout);
            phb_text = itemView.findViewById(R.id.phb_text);
            phb_image = itemView.findViewById(R.id.phb_image);
            phb_image_free = itemView.findViewById(R.id.phb_image_free);
        }

    }

    private OnRecyItemClickListener listener;

    //第二步， 写一个公共的方法
    public void setOnItemClickListener(OnRecyItemClickListener listener) {
        this.listener = listener;
    }

}
