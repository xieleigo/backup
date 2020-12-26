package io.dcloud.H5E4CA812.view.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import io.dcloud.H5E4CA812.view.activity.ReceivingAddressInfoActivity;
import io.dcloud.H5E4CA812.R;
import io.dcloud.H5E4CA812.view.bean.ReceivingAddressBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReceivingAddressItmePhotoPopWin extends PopupWindow {

    private Context mContext;

    private View view;
    @BindView(R.id.receiving_address_pop_seting)
    TextView receiving_address_pop_seting;
    @BindView(R.id.receiving_address_pop_edit)
    TextView receiving_address_pop_edit;
    @BindView(R.id.receiving_address_pop_delet)
    TextView receiving_address_pop_delet;
    @BindView(R.id.receiving_address_pop_exte)
    TextView receiving_address_pop_exte;

    ReceivingAddressBean receivingAddressBean;

    int postion;

    public ReceivingAddressItmePhotoPopWin(Context mContext, ReceivingAddressBean receivingAddressBean, int postion) {
        this.mContext = mContext;
        this.receivingAddressBean = receivingAddressBean;
        this.postion = postion;
        this.view = LayoutInflater.from(mContext).inflate(R.layout.receiving_address_itme_pop, null);
        ButterKnife.bind(this, view);
        // 设置外部可点击
        this.setOutsideTouchable(true);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        this.view.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
//
//                int height = view.findViewById(R.id.pop_layout).getTop();
//
//                int y = (int) event.getY();
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    if (y < height) {
//                        dismiss();
//                    }
//                }
                return true;
            }
        });


        /* 设置弹出窗口特征 */
        // 设置视图
        this.setContentView(this.view);
        // 设置弹出窗体的宽和高
        this.setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);

        // 设置弹出窗体可点击
        this.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);

        // 设置弹出窗体显示时的动画，从底部向上弹出
        this.setAnimationStyle(R.style.take_photo_anim);

    }

    @OnClick({R.id.receiving_address_pop_seting,
            R.id.receiving_address_pop_edit,
            R.id.receiving_address_pop_delet,
            R.id.receiving_address_pop_exte
    })
    void click(View view) {
        switch (view.getId()) {
            case R.id.receiving_address_pop_seting:
                mSetingListener.onSeting(receivingAddressBean);
                break;
            case R.id.receiving_address_pop_edit:
                Intent intent = new Intent(mContext, ReceivingAddressInfoActivity.class);
                intent.putExtra("receivingAddressBean", receivingAddressBean);
                mContext.startActivity(intent);
                dismiss();
                break;
            case R.id.receiving_address_pop_delet:
                mDeleteListener.onDelete(receivingAddressBean, postion);
                break;
            case R.id.receiving_address_pop_exte:
                dismiss();
                break;


        }
    }

    //删除的回调
    public interface OnDeleteListener {
        void onDelete(ReceivingAddressBean receivingAddressBean, int postion);
    }

    public void setOnDeleteListener(ReceivingAddressItmePhotoPopWin.OnDeleteListener listener) {
        mDeleteListener = listener;
    }

    private ReceivingAddressItmePhotoPopWin.OnDeleteListener mDeleteListener;

    //删除的回调
    public interface OnSetingListener {
        void onSeting(ReceivingAddressBean receivingAddressBean);
    }

    public void setOnSetingListener(ReceivingAddressItmePhotoPopWin.OnSetingListener listener) {
        mSetingListener = listener;
    }

    private ReceivingAddressItmePhotoPopWin.OnSetingListener mSetingListener;


}
