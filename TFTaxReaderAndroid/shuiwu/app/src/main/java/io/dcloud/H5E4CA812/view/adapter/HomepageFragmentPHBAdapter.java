package io.dcloud.H5E4CA812.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import io.dcloud.H5E4CA812.view.activity.HomepagerJournalDetails;
import io.dcloud.H5E4CA812.R;
import io.dcloud.H5E4CA812.util.ImageViewUtil;
import io.dcloud.H5E4CA812.view.bean.HomeBean;
import io.dcloud.H5E4CA812.view.widget.OnRecyItemClickListener;
import io.dcloud.H5E4CA812.view.widget.listmovedeletbut.SwipeLayout;

import java.util.List;

/**
 * 首页排行榜
 */

public class HomepageFragmentPHBAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements SwipeLayout.OnSwipeStateChangeListener {
    private List<HomeBean> mDataList;
    private Context context;

    public HomepageFragmentPHBAdapter(Context context, List<HomeBean> dataList) {
        this.mDataList = dataList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_hompagefragment_phb, parent, false);
        return new RecyclerViewHolder(view);
    }

    public void setmDataList(List<HomeBean> mDataList) {
        this.mDataList = mDataList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
        ImageViewUtil.setPHBImage(context, mDataList.get(position).getProdImg(), recyclerViewHolder.phb_image);
        recyclerViewHolder.phb_text.setText(mDataList.get(position).getProdName());
        if (mDataList.get(position).isProdIsFree()) {
            recyclerViewHolder.phb_image_free.setVisibility(View.VISIBLE);
        } else {
            recyclerViewHolder.phb_image_free.setVisibility(View.GONE);
        }
        recyclerViewHolder.phb_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HomepagerJournalDetails.class);
                intent.putExtra("ReadSourceID", mDataList.get(position).getReadSourceID() + "");
                context.startActivity(intent);
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

    @Override
    public void onOpen(Object tag) {

    }

    @Override
    public void onClose(Object tag) {

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
