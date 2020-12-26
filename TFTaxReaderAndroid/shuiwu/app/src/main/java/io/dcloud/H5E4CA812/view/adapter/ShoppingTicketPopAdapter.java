package io.dcloud.H5E4CA812.view.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import io.dcloud.H5E4CA812.R;
import io.dcloud.H5E4CA812.view.activity.AddBillingInformationActivity;
import io.dcloud.H5E4CA812.view.bean.BillingInfoItmeBean;
import io.dcloud.H5E4CA812.view.widget.OnRecyItemClickListener;
import io.dcloud.H5E4CA812.view.widget.ShoppingCartConfirmOrderTicketPhotoPopWin;
import io.dcloud.H5E4CA812.view.widget.listmovedeletbut.SwipeLayout;

import java.util.List;

public class ShoppingTicketPopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements SwipeLayout.OnSwipeStateChangeListener {
    private List<BillingInfoItmeBean> mDataList;
    private Activity activity;
    private int postions = 0;
    private boolean IsPson;
    ShoppingCartConfirmOrderTicketPhotoPopWin photoPopWin;

    public ShoppingTicketPopAdapter(ShoppingCartConfirmOrderTicketPhotoPopWin pop, List<BillingInfoItmeBean> dataList, Activity activity, boolean type) {
        this.photoPopWin = pop;
        this.mDataList = dataList;
        this.activity = activity;
        this.IsPson = type;

    }

    public List<BillingInfoItmeBean> getmDataList() {
        return mDataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_shopping_ticket_pop_itme, parent, false);
        return new RecyclerViewHolder(view);
    }

    public void setmDataList(List<BillingInfoItmeBean> mDataList) {
        this.mDataList = mDataList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
        if (mDataList.get(position).getUserInvoiceReceiveType() == 1) {
            recyclerViewHolder.type.setText(mDataList.get(position).getUserInvoiceTitle());
            recyclerViewHolder.shuihao.setText(mDataList.get(position).getUserInvoicePhone());
        } else {
            recyclerViewHolder.type.setText(mDataList.get(position).getUserInvoiceTitle());
            recyclerViewHolder.shuihao.setText(mDataList.get(position).getUserInvoiceTaxpayerNo());
        }
        //商品是否被选中
        final boolean isSelect = mDataList.get(position).isSelect();
        //商品是否被选中
        if (isSelect) {
            recyclerViewHolder.iv_select.setImageResource(R.drawable.button_red_mark);
        } else {
            recyclerViewHolder.iv_select.setImageResource(R.drawable.button_white);
        }
        //商品选择框的点击事件
        recyclerViewHolder.iv_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postions = position;
                for (int i = 0; i < mDataList.size(); i++) {
                    if (i == postions) {
                        mDataList.get(i).setSelect(true);
                    } else {
                        mDataList.get(i).setSelect(false);
                    }
                }
                notifyDataSetChanged();
            }
        });

        recyclerViewHolder.iv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, AddBillingInformationActivity.class);
                Bundle bundle = new Bundle();
                mDataList.get(position).setTransmit(true);
                if (IsPson) {
                    intent.putExtra("tabType", 1);
                } else {
                    intent.putExtra("tabType", 2);
                }
                bundle.putSerializable("BillingInfoItmeBean", mDataList.get(position));
                bundle.putBoolean("isOrderTicke", true);
                intent.putExtras(bundle);
                activity.startActivityForResult(intent, 11);
                photoPopWin.dismiss();
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
                type,
                shuihao;
        private ImageView iv_edit, iv_select;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.type);
            shuihao = itemView.findViewById(R.id.shuihao);
            iv_edit = itemView.findViewById(R.id.iv_edit);
            iv_select = itemView.findViewById(R.id.iv_select);
        }

    }

    private OnRecyItemClickListener listener;

    //第二步， 写一个公共的方法
    public void setOnItemClickListener(OnRecyItemClickListener listener) {
        this.listener = listener;
    }

}
