package io.dcloud.H5E4CA812.view.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.dcloud.H5E4CA812.R;

public class AllOrderPayPhotoPopWin extends PopupWindow {

    @BindView(R.id.shopping_pay_pop_zfb)
    RelativeLayout shopping_pay_pop_zfb;
    @BindView(R.id.shopping_pay_pop_wx)
    RelativeLayout shopping_pay_pop_wx;
    @BindView(R.id.shopping_pay_pop_quit)
    TextView shopping_pay_pop_quit;
    @BindView(R.id.shopping_pay_pop_zfb_sele)
    ImageView shopping_pay_pop_zfb_sele;
    @BindView(R.id.shopping_pay_pop_wx_sele)
    ImageView shopping_pay_pop_wx_sele;
    @BindView(R.id.lijizhifu)
    Button lijizhifu;


    private Context mContext;
    private View view;


    public AllOrderPayPhotoPopWin(Context mContext) {
        this.mContext = mContext;
        this.view = LayoutInflater.from(mContext).inflate(R.layout.all_order_pay_pop, null);
        ButterKnife.bind(this, view);
        init();

    }

    int payType = 5;

    @OnClick({R.id.shopping_pay_pop_zfb, R.id.shopping_pay_pop_wx, R.id.shopping_pay_pop_quit, R.id.lijizhifu})
    void click(View view) {
        switch (view.getId()) {
            case R.id.shopping_pay_pop_zfb:
                payType = 5;//支付宝
                shopping_pay_pop_zfb_sele.setBackground(mContext.getDrawable(R.drawable.button_red_mark));
                shopping_pay_pop_wx_sele.setBackground(mContext.getDrawable(R.drawable.button_white));
                break;
            case R.id.shopping_pay_pop_wx:
                payType = 22;//微信
                shopping_pay_pop_zfb_sele.setBackground(mContext.getDrawable(R.drawable.button_white));
                shopping_pay_pop_wx_sele.setBackground(mContext.getDrawable(R.drawable.button_red_mark));
                break;
            case R.id.shopping_pay_pop_quit:
                dismiss();
                break;
            case R.id.lijizhifu:
                if (OnChangeListener != null) {
                    OnChangeListener.onChange(payType);
                }
                break;

        }
    }


    private void init() {
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

    //支付宝或微信
    public interface OnChangeListener {
        void onChange(int payType);
    }

    public void setOnChangeCountListener(OnChangeListener listener) {
        OnChangeListener = listener;
    }

    private OnChangeListener OnChangeListener;
}
