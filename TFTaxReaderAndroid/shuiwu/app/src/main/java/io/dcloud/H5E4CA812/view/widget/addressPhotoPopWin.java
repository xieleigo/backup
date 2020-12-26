package io.dcloud.H5E4CA812.view.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import io.dcloud.H5E4CA812.R;
import io.dcloud.H5E4CA812.util.addresspickerlib.AddressPickerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class addressPhotoPopWin extends PopupWindow {
    private Context mContext;
    private View view;
    int widthPixel = 0;
    @BindView(R.id.iv_tab_bottom_img)
    ImageView iv_tab_bottom_img;
    Activity activity;
    @BindView(R.id.apvAddress)
    AddressPickerView apvAddress;
    @BindView(R.id.apvAddress_layout)
    LinearLayout apvAddress_layout;
    @BindView(R.id.itemTvTitle_layout)
    LinearLayout itemTvTitle_layout;
    @BindView(R.id.itemTvTitle)
    TextView itemTvTitle;
    TextView textView;

    public addressPhotoPopWin(Activity activity, Context mContext, TextView textView, TextView AreaTreePath) {
        this.activity = activity;
        this.mContext = mContext;
        this.textView = textView;
        this.view = LayoutInflater.from(mContext).inflate(R.layout.address_photo_pop, null);
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
        DisplayMetrics outMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(outMetrics);
        widthPixel = outMetrics.widthPixels;
        ViewGroup.LayoutParams para;
        para = iv_tab_bottom_img.getLayoutParams();
        para.width = widthPixel / 2;
        iv_tab_bottom_img.setLayoutParams(para);
        initImageView();

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

        apvAddress.setOnAddressPickerSure(new AddressPickerView.OnAddressPickerSureListener() {
            @Override
            public void onSureClick(String address, int CountryId, int provinceCode, int cityCode, int districtCode) {
                String addresscode = CountryId + "-" + provinceCode + "-" + cityCode + "-" + districtCode;
                if (address.contains("全区")) {
                    address = address.substring(0, address.length() - 3);
                    addresscode = CountryId + "-" + provinceCode + "-" + cityCode;
                }
                textView.setText(address);
                AreaTreePath.setText(addresscode);
                textView.setTextColor(mContext.getResources().getColor(R.color.black));
                dismiss();
            }
        });

    }


    /**
     * 下划线图片宽度
     */
    private int lineWidth;
    /**
     * 偏移量（手机屏幕宽度 / 选项卡总数 - 选项卡长度） / 2
     */
    private int offset = 0;
    /**
     * 选项卡总数
     */
    private static final int TAB_COUNT = 2;

    /**
     * 初始化底部下划线
     */
    private void initImageView() {
        // 获取图片宽度
        lineWidth = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.zgsw).getWidth();
        // Android提供的DisplayMetrics可以很方便的获取屏幕分辨率
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels; // 获取分辨率宽度
        offset = (screenW / TAB_COUNT - lineWidth) / 2;  // 计算偏移值
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        // 设置下划线初始位置
        iv_tab_bottom_img.setImageMatrix(matrix);
    }

    Animation animation;

    private void move(int width1, int width) {

        animation = new TranslateAnimation(width1, width, 0, 0);
        animation.setFillAfter(true);
        animation.setDuration(300);
        // 给图片添加动画
        iv_tab_bottom_img.startAnimation(animation);

    }

    @OnClick({R.id.but_collect_fragment_title1, R.id.but_collect_fragment_title2, R.id.itemTvTitle})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.but_collect_fragment_title1:
                move(widthPixel / 2, 0);
                itemTvTitle_layout.setVisibility(View.GONE);
                apvAddress_layout.setVisibility(View.VISIBLE);
                break;
            case R.id.but_collect_fragment_title2:
                move(0, widthPixel / 2);
                itemTvTitle_layout.setVisibility(View.VISIBLE);
                apvAddress_layout.setVisibility(View.GONE);

                break;
            case R.id.itemTvTitle:
                textView.setTextColor(mContext.getResources().getColor(R.color.black));
                textView.setText(itemTvTitle.getText());
                dismiss();

                break;
        }
    }
}
