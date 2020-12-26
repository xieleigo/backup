package io.dcloud.H5E4CA812.view.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import io.dcloud.H5E4CA812.R;

import io.dcloud.H5E4CA812.util.ToastUtil;
import io.dcloud.H5E4CA812.view.bean.JHMBean;
import io.dcloud.H5E4CA812.view.widget.OnRecyItemClickListener;
import io.dcloud.H5E4CA812.view.widget.listmovedeletbut.SwipeLayout;

import java.util.List;

public class AllOrderCopyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements SwipeLayout.OnSwipeStateChangeListener {
    private List<JHMBean> mDataList;
    Context context;

    public AllOrderCopyAdapter(List<JHMBean> dataList, Context context) {
        this.mDataList = dataList;
        this.context = context;
    }

    public void setmDataList(List<JHMBean> mDataList) {
        this.mDataList = mDataList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itme_allorder_copy_jhm, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
        if (mDataList.get(position).getActivationCodeStatus() == 1) {
            recyclerViewHolder.itme_copy_but.setBackground(context.getDrawable(R.drawable.order_hui_button_shape));
        } else {
            recyclerViewHolder.itme_copy_but.setBackground(context.getDrawable(R.drawable.order_button_shape));
        }

        recyclerViewHolder.itme_copy_pass.setText("密码：" + mDataList.get(position).getActivationCodePWD());
        recyclerViewHolder.itme_copy_xlh.setText("序列号：" + mDataList.get(position).getActivationCodeSN());
        recyclerViewHolder.itme_copy_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDataList.get(position).getActivationCodeStatus() == 0) {
                    copy("序列号：" + mDataList.get(position).getActivationCodeSN() + "  " + "密码：" + mDataList.get(position).getActivationCodePWD());
                }else {
                    ToastUtil.makeText(context, "激活码已失效");
                }
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
        private TextView itme_copy_xlh, itme_copy_pass,
                itme_copy_but;


        RecyclerViewHolder(View itemView) {
            super(itemView);
            itme_copy_xlh = itemView.findViewById(R.id.itme_copy_xlh);
            itme_copy_pass = itemView.findViewById(R.id.itme_copy_pass);
            itme_copy_but = itemView.findViewById(R.id.itme_copy_but);
        }

    }

    private OnRecyItemClickListener listener;

    //第二步， 写一个公共的方法
    public void setOnItemClickListener(OnRecyItemClickListener listener) {
        this.listener = listener;
    }

    //复制
    private void copy(String data) {
        // 获取系统剪贴板
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText(null, data);
        // 把数据集设置（复制）到剪贴板
        clipboard.setPrimaryClip(clipData);
        ToastUtil.makeText(context, "复制成功");
    }


}
