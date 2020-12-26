package io.dcloud.H5E4CA812.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import io.dcloud.H5E4CA812.view.activity.QKDetailsSearchActivity;
import io.dcloud.H5E4CA812.R;

import io.dcloud.H5E4CA812.base.RecyclerBaseAdatpter;

import java.util.List;

public class QKDetailsYearAdapter extends RecyclerBaseAdatpter {
    private List<QKDetailsSearchActivity.YearBean> mDataList;
    Context context;

    public QKDetailsYearAdapter(List<QKDetailsSearchActivity.YearBean> dataList, Context context) {
        this.mDataList = dataList;
        this.context = context;
    }

    public void setmDataList(List<QKDetailsSearchActivity.YearBean> mDataList) {
        this.mDataList = mDataList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itme_qk_detail_year, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
        recyclerViewHolder.itme_qk_detail_year.setText(mDataList.get(position).getYear());
        if (mDataList.get(position).getIsback()) {
            recyclerViewHolder.itme_qk_detail_year_layout.setBackground(context.getResources().getDrawable(R.color.Button_register));
        } else {
            recyclerViewHolder.itme_qk_detail_year_layout.setBackground(context.getResources().getDrawable(R.color.ffffff));
        }
        recyclerViewHolder.itme_qk_detail_year_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < mDataList.size(); i++) {
                    if (position == i) {
                        mDataList.get(i).setIsback(true);
                    } else {
                        mDataList.get(i).setIsback(false);
                    }

                }
                notifyDataSetChanged();
                if (listener != null) {
                    listener.send(mDataList.get(position).getYear());
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
        public TextView itme_qk_detail_year;
        public RelativeLayout itme_qk_detail_year_layout;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            itme_qk_detail_year = (TextView) itemView.findViewById(R.id.itme_qk_detail_year);
            itme_qk_detail_year_layout = (RelativeLayout) itemView.findViewById(R.id.itme_qk_detail_year_layout);

        }
    }


    private OnSendListener listener;

    public void setOnSendClickListener(OnSendListener listener) {
        this.listener = listener;
    }

    public interface OnSendListener {
        void send(String year);
    }


}
