package io.dcloud.H5E4CA812.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;

import io.dcloud.H5E4CA812.R;

import io.dcloud.H5E4CA812.base.RecyclerBaseAdatpter;
import io.dcloud.H5E4CA812.protocol.ApiType;
import io.dcloud.H5E4CA812.protocol.Request;
import io.dcloud.H5E4CA812.util.EventBusCarrier;
import io.dcloud.H5E4CA812.util.ToastUtil;
import io.dcloud.H5E4CA812.view.activity.AddBillingInformationActivity;
import io.dcloud.H5E4CA812.view.bean.BillingInfoItmeBean;
import io.dcloud.H5E4CA812.view.widget.OnRecyItemClickListener;
import io.dcloud.H5E4CA812.view.widget.listmovedeletbut.SwipeLayout;
import io.dcloud.H5E4CA812.view.widget.listmovedeletbut.SwipeLayoutManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BillingInformationPersonAdapter extends RecyclerBaseAdatpter implements SwipeLayout.OnSwipeStateChangeListener {
    private List<BillingInfoItmeBean> mDataList;
    private Context context;

    public BillingInformationPersonAdapter(List<BillingInfoItmeBean> dataList, Context context) {
        this.mDataList = dataList;
        this.context = context;
    }

    public void setmDataList(List<BillingInfoItmeBean> mDataList) {
        this.mDataList = mDataList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_billing_info_person, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
        recyclerViewHolder.billing_adapter_person_name.setText(mDataList.get(position).getUserInvoiceTitle());
        recyclerViewHolder.billing_adapter_pseon_phone.setText(mDataList.get(position).getUserInvoicePhone());
        if (mDataList.get(position).getUserInvoiceEmail() == null) {
            recyclerViewHolder.billing_adapter_mail.setText("");
        } else {
            recyclerViewHolder.billing_adapter_mail.setText(mDataList.get(position).getUserInvoiceEmail() + "");
        }
        recyclerViewHolder.swipeLayout.setTag(position);
        recyclerViewHolder.swipeLayout.setOnSwipeStateChangeListener(this);
        if (mDataList.get(position).getUserInvoiceDefault() == 1) {
            recyclerViewHolder.person_defult.setVisibility(View.VISIBLE);
            recyclerViewHolder.setting_defult.setVisibility(View.GONE);
        } else {
            recyclerViewHolder.setting_defult.setVisibility(View.VISIBLE);
            recyclerViewHolder.person_defult.setVisibility(View.GONE);
        }
        recyclerViewHolder.setting_defult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map map = new HashMap();
                map.put("UserInvoiceID", mDataList.get(position).getUserInvoiceID());
                execApi(ApiType.GetInvoiceSetDefault, map, context);
            }
        });
        //删除
        recyclerViewHolder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataList.remove(mDataList.get(position));
                notifyDataSetChanged();
                SwipeLayoutManager.getInstance().closeCurrentLayout();
                SwipeLayoutManager.getInstance().clearCurrentLayout();
                Toast.makeText(context, "第" + position + "个已删除", Toast.LENGTH_SHORT).show();
            }
        });

        //跳转
        recyclerViewHolder.enterprise_title_person_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddBillingInformationActivity.class);
                Bundle bundle = new Bundle();
                intent.putExtra("tabType", 0);
                bundle.putSerializable("BillingInfoItmeBean", mDataList.get(position));
                intent.putExtras(bundle);
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
//        //置顶
//        recyclerViewHolder.tvZhiDing.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDataList.add(0, mDataList.get(position));
//                mDataList.remove(position + 1);
//
//                CommonAdapter.this.notifyDataSetChanged();
//                SwipeLayoutManager.getInstance().closeCurrentLayout();
//                SwipeLayoutManager.getInstance().clearCurrentLayout();
//            }
//        });
    }


    @Override
    public int getItemCount() {
        if (mDataList != null && mDataList.size() > 0) {
            return mDataList.size();
        } else {
            return 0;
        }
    }

    @Override
    public void onOpen(Object tag) {

    }

    @Override
    public void onClose(Object tag) {

    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView billing_adapter_person_name, billing_adapter_pseon_phone,
                billing_adapter_mail,
                enterprise_title_person,
                setting_defult,
                tvZhiDing, tvDelete, person_defult;

        private SwipeLayout swipeLayout;
        private LinearLayout enterprise_title_person_layout;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            setting_defult = itemView.findViewById(R.id.setting_defult);
            billing_adapter_person_name = itemView.findViewById(R.id.billing_adapter_person_name);
            billing_adapter_pseon_phone = itemView.findViewById(R.id.billing_adapter_pseon_phone);
            billing_adapter_mail = itemView.findViewById(R.id.billing_adapter_mail);
            tvDelete = itemView.findViewById(R.id.tv_delete);
            tvZhiDing = itemView.findViewById(R.id.tv_zhiding);
            swipeLayout = itemView.findViewById(R.id.swipelayout);
            person_defult = itemView.findViewById(R.id.person_defult);
            enterprise_title_person_layout = itemView.findViewById(R.id.enterprise_title_person_layout);
        }
    }

    private OnRecyItemClickListener listener;

    //第二步， 写一个公共的方法
    public void setOnItemClickListener(OnRecyItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onResponsedError(Request req) {
        super.onResponsedError(req);
        ToastUtil.makeText(context, req.getErrorMsg());
    }

    @Override
    public void onResponse(Request req) {
        super.onResponse(req);
        if (req.getData() != null) {
            if (req.getData().getMsg().equals("用户发票信息设置成功")) {
                ToastUtil.makeText(context, req.getData().getMsg());
                EventBusCarrier eventBusCarrier = new EventBusCarrier();
                eventBusCarrier.setEventType("1");
                EventBus.getDefault().post(eventBusCarrier); //普通事件发布
            }
        }

    }
}
