package io.dcloud.H5E4CA812.view.adapter;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import io.dcloud.H5E4CA812.protocol.ApiType;
import io.dcloud.H5E4CA812.protocol.Request;
import io.dcloud.H5E4CA812.R;
import io.dcloud.H5E4CA812.base.RecyclerBaseAdatpter;
import io.dcloud.H5E4CA812.util.StringUtil;
import io.dcloud.H5E4CA812.util.ToastUtil;
import io.dcloud.H5E4CA812.util.loading.Loading;
import io.dcloud.H5E4CA812.view.activity.ReceivingAddressInfoActivity;
import io.dcloud.H5E4CA812.view.bean.ReceivingAddressBean;
import io.dcloud.H5E4CA812.view.widget.OnRecyItemClickListener;
import io.dcloud.H5E4CA812.view.widget.ReceivingAddressItmePhotoPopWin;
import io.dcloud.H5E4CA812.view.widget.listmovedeletbut.SwipeLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReceivingAddressAdapter extends RecyclerBaseAdatpter implements SwipeLayout.OnSwipeStateChangeListener {
    private List<ReceivingAddressBean> mDataList;
    private Activity context;

    public ReceivingAddressAdapter(List<ReceivingAddressBean> dataList, Activity context) {
        this.mDataList = dataList;
        this.context = context;
    }

    public void setmDataList(List<ReceivingAddressBean> mDataList) {
        this.mDataList = mDataList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_receiving_address, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
        recyclerViewHolder.shopping_order_name.setText(mDataList.get(position).getUserAddressMan());
        recyclerViewHolder.shopping_order_phone.setText(mDataList.get(position).getUserAddressMobile());
        recyclerViewHolder.shopping_order_address.setText(mDataList.get(position).getUserAddressDetail());
        if (mDataList.get(position).getUserAddressIsDefault() == 1) {
            recyclerViewHolder.mrderss_test.setVisibility(View.VISIBLE);
            recyclerViewHolder.shopping_order_address_image.setVisibility(View.VISIBLE);
        } else {
            recyclerViewHolder.mrderss_test.setVisibility(View.GONE);
            recyclerViewHolder.shopping_order_address_image.setVisibility(View.GONE);
        }


        recyclerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(v, position);
                }
            }
        });
        recyclerViewHolder.itme_receiving_address_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTicketBottom(v, mDataList.get(position), position);
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
                shopping_order_name,
                shopping_order_phone,
                mrderss_test,
                shopping_order_address;
        private ImageView shopping_order_address_image, itme_receiving_address_more;


        RecyclerViewHolder(View itemView) {
            super(itemView);
            shopping_order_name = itemView.findViewById(R.id.shopping_order_name);
            shopping_order_phone = itemView.findViewById(R.id.shopping_order_phone);
            shopping_order_address = itemView.findViewById(R.id.shopping_order_address);
            shopping_order_address_image = itemView.findViewById(R.id.shopping_order_address_image);
            mrderss_test = itemView.findViewById(R.id.mrderss_test);
            itme_receiving_address_more = itemView.findViewById(R.id.itme_receiving_address_more);
        }

    }

    private OnRecyItemClickListener listener;

    //第二步， 写一个公共的方法
    public void setOnItemClickListener(OnRecyItemClickListener listener) {
        this.listener = listener;
    }


    public void showTicketBottom(View view, ReceivingAddressBean receivingAddressBean, int pos) {
        takePhotoPopWin = new ReceivingAddressItmePhotoPopWin(this.context, receivingAddressBean, pos);
        //showAtLocation(View parent, int gravity, int x, int y)
        takePhotoPopWin.setOnDeleteListener(new ReceivingAddressItmePhotoPopWin.OnDeleteListener() {
            @Override
            public void onDelete(ReceivingAddressBean receivingAddressBean, int poston) {
                Loading.getInstance(context).show();
                ReceivingAddressAdapter.this.postion = poston;
                Map map = new HashMap();
                map.put("UserAddrID", receivingAddressBean.getUserAddrID());
                execApi(ApiType.GetAddressDelete, map, context);
            }
        });
        takePhotoPopWin.setOnSetingListener(new ReceivingAddressItmePhotoPopWin.OnSetingListener() {
            @Override
            public void onSeting(ReceivingAddressBean receivingAddressBean) {
                Map map = new HashMap();
                map.put("UserAddrID", receivingAddressBean.getUserAddrID());
                execApi(ApiType.GetAddressSetDefault, map, context);
            }
        });
        takePhotoPopWin.showAtLocation(context.findViewById(R.id.itme_receiving_address_more), Gravity.CENTER, 0, 0);
    }

    public int postion;
    public ReceivingAddressItmePhotoPopWin takePhotoPopWin;

    @Override
    public void onResponse(Request req) {
        super.onResponse(req);
        Loading.getInstance(context).hide();
        if (req.getData() == null) {
            if (req.getErrorMsg().equals("authorization参数不能为空")) {
                StringUtil.showLoginDialog(context);
            }
        }
        if (req.getData() != null && req.getData().getRet()) {
            if (req.getData().getUrl().contains("/Address/Delete")) {
                Message msg = new Message();
                msg.what = 100;
                handler.sendMessage(msg);
            }
            if (req.getData().getUrl().contains("/Address/SetDefault")) {
                Message msg = new Message();
                msg.what = 200;
                handler.sendMessage(msg);
            }
        }
    }

    android.os.Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    mDataList.remove(postion);
                    notifyDataSetChanged();
                    takePhotoPopWin.dismiss();
                    break;
                case 200:
                    mSetingListener.onSeting();
                    takePhotoPopWin.dismiss();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onResponsedError(Request req) {
        super.onResponsedError(req);
        Loading.getInstance(context).hide();
        if (req.getData() != null && !req.getData().getRet()) {
            ToastUtil.makeText(context, req.getData().getMsg());
        }
    }

    //设置回调
    public interface OnSetingListener {
        void onSeting();
    }

    public void setOnSetingListener(ReceivingAddressAdapter.OnSetingListener listener) {
        mSetingListener = listener;
    }

    private ReceivingAddressAdapter.OnSetingListener mSetingListener;

}
