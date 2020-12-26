package io.dcloud.H5E4CA812.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;

import io.dcloud.H5E4CA812.protocol.ApiType;
import io.dcloud.H5E4CA812.protocol.Request;
import io.dcloud.H5E4CA812.R;

import io.dcloud.H5E4CA812.base.RecyclerBaseAdatpter;
import io.dcloud.H5E4CA812.util.EventBusCarrier;
import io.dcloud.H5E4CA812.util.ToastUtil;
import io.dcloud.H5E4CA812.view.activity.AddBillingInformationActivity;
import io.dcloud.H5E4CA812.view.bean.BillingInfoItmeBean;
import io.dcloud.H5E4CA812.view.widget.OnRecyItemClickListener;
import io.dcloud.H5E4CA812.view.widget.listmovedeletbut.SwipeLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BillingInformationAdapter extends RecyclerBaseAdatpter implements
        SwipeLayout.OnSwipeStateChangeListener {
    private List<BillingInfoItmeBean> mDataList;
    private Context context;

    public BillingInformationAdapter(List<BillingInfoItmeBean> dataList, Context context) {
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
                .inflate(R.layout.adapter_billing_info, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
        recyclerViewHolder.billing_adapter_name.setText(mDataList.get(position).getUserInvoiceTitle());
        recyclerViewHolder.billing_adapter_person_name.setText(mDataList.get(position).getUserInvoiceTitle());
        recyclerViewHolder.billing_adapter_code.setText(mDataList.get(position).getUserInvoiceTaxpayerNo());
        if (mDataList.get(position).getUserInvoiceorAddress() == null) {
            recyclerViewHolder.billing_adapter_address.setText("");
        } else {
            recyclerViewHolder.billing_adapter_address.setText(mDataList.get(position).getUserInvoiceorAddress() + "" + mDataList.get(position).getUserInvoicePhone());
        }
        if (mDataList.get(position).getUserInvoiceBuyerBankName() == null) {
            recyclerViewHolder.billing_adapter_yh.setText("");
        } else {
            recyclerViewHolder.billing_adapter_yh.setText(mDataList.get(position).getUserInvoiceBuyerBankName().toString());
        }
        if (mDataList.get(position).getUserInvoiceBankAcount() == null) {
            recyclerViewHolder.billing_adapter_yh_code.setText("");
        } else {
            recyclerViewHolder.billing_adapter_yh_code.setText(mDataList.get(position).getUserInvoiceBankAcount().toString());
        }
        recyclerViewHolder.billing_adapter_pseon_phone.setText(mDataList.get(position).getUserInvoicePhone());
        if (mDataList.get(position).getUserInvoiceEmail() == null) {
            recyclerViewHolder.billing_adapter_mail.setText("");
        } else {
            recyclerViewHolder.billing_adapter_mail.setText(mDataList.get(position).getUserInvoiceEmail().toString());
        }

        recyclerViewHolder.swipeLayout.setTag(position);
        if (mDataList.get(position).getUserInvoiceReceiveType() != 1) {
            if (mDataList.get(position).getUserInvoiceDefault() == 1) {
                recyclerViewHolder.gs_defult.setVisibility(View.VISIBLE);
                recyclerViewHolder.gs_setting_defult.setVisibility(View.GONE);
            } else {
                recyclerViewHolder.gs_setting_defult.setVisibility(View.VISIBLE);
                recyclerViewHolder.gs_defult.setVisibility(View.GONE);
            }
            recyclerViewHolder.gs_info_layout.setVisibility(View.VISIBLE);
            recyclerViewHolder.pserson_info_layout.setVisibility(View.GONE);
        } else {
            if (mDataList.get(position).getUserInvoiceDefault() == 1) {
                recyclerViewHolder.person_defult.setVisibility(View.VISIBLE);
                recyclerViewHolder.person_setting_defult.setVisibility(View.GONE);

            } else {
                recyclerViewHolder.person_setting_defult.setVisibility(View.VISIBLE);
                recyclerViewHolder.person_defult.setVisibility(View.GONE);
            }
            recyclerViewHolder.gs_info_layout.setVisibility(View.GONE);
            recyclerViewHolder.pserson_info_layout.setVisibility(View.VISIBLE);
        }
        recyclerViewHolder.gs_setting_defult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map map = new HashMap();
                map.put("UserInvoiceID", mDataList.get(position).getUserInvoiceID());
                execApi(ApiType.GetInvoiceSetDefault, map, context);
            }
        });
        recyclerViewHolder.person_setting_defult.setOnClickListener(new View.OnClickListener() {
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
                mCollectionListener.onCollection(position);
            }
        });
        //点击跳转
        recyclerViewHolder.enterprise_title_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddBillingInformationActivity.class);
                Bundle bundle = new Bundle();
                if (mDataList.get(position).getUserInvoiceReceiveType() != 1) {
                    intent.putExtra("tabType", 2);
                } else {
                    intent.putExtra("tabType", 0);
                }
                bundle.putSerializable("BillingInfoItmeBean", mDataList.get(position));
                intent.putExtras(bundle);
                context.startActivity(intent);
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
        private TextView billing_adapter_name, billing_adapter_code,
                billing_adapter_address,
                billing_adapter_yh,
                billing_adapter_yh_code,
                enterprise_title,
                tvZhiDing, tvDelete, billing_adapter_person_name, billing_adapter_pseon_phone,
                billing_adapter_mail,
                person_defult,
                person_setting_defult,
                gs_setting_defult,
                gs_defult;
        private SwipeLayout swipeLayout;
        private LinearLayout enterprise_title_layout, gs_info_layout, pserson_info_layout;


        RecyclerViewHolder(View itemView) {
            super(itemView);
            billing_adapter_name = itemView.findViewById(R.id.billing_adapter_name);
            billing_adapter_code = itemView.findViewById(R.id.billing_adapter_code);
            billing_adapter_address = itemView.findViewById(R.id.billing_adapter_address);
            billing_adapter_yh = itemView.findViewById(R.id.billing_adapter_yh);
            billing_adapter_yh_code = itemView.findViewById(R.id.billing_adapter_yh_code);
            tvDelete = itemView.findViewById(R.id.tv_delete);
            tvZhiDing = itemView.findViewById(R.id.tv_zhiding);
            swipeLayout = itemView.findViewById(R.id.swipelayout);
            enterprise_title_layout = itemView.findViewById(R.id.enterprise_title_layout);
            gs_info_layout = itemView.findViewById(R.id.gs_info_layout);
            pserson_info_layout = itemView.findViewById(R.id.pserson_info_layout);
            gs_defult = itemView.findViewById(R.id.gs_defult);
            person_defult = itemView.findViewById(R.id.person_defult);
            person_setting_defult = itemView.findViewById(R.id.person_setting_defult);
            gs_setting_defult = itemView.findViewById(R.id.gs_setting_defult);

            billing_adapter_person_name = itemView.findViewById(R.id.billing_adapter_person_name);
            billing_adapter_pseon_phone = itemView.findViewById(R.id.billing_adapter_pseon_phone);
            billing_adapter_mail = itemView.findViewById(R.id.billing_adapter_mail);
        }

    }

    @Override
    public int getItemViewType(int position) {
        return mDataList.get(position).getUserInvoiceReceiveType();
    }

    private OnRecyItemClickListener listener;

    //第二步， 写一个公共的方法
    public void setOnItemClickListener(OnRecyItemClickListener listener) {
        this.listener = listener;
    }


    //删除的回调
    public interface OnCollectionListener {
        void onCollection(int postion);
    }

    public void setOnCollectionListener(BillingInformationAdapter.OnCollectionListener listener) {
        mCollectionListener = listener;
    }

    private BillingInformationAdapter.OnCollectionListener mCollectionListener;

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
