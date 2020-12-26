package io.dcloud.H5E4CA812.view.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.dcloud.H5E4CA812.GlobleApplication;
import io.dcloud.H5E4CA812.R;
import io.dcloud.H5E4CA812.util.ToastUtil;
import io.dcloud.H5E4CA812.view.activity.HomepagerJournalDetails;
import io.dcloud.H5E4CA812.view.activity.ReceivingAddressInfoActivity;
import io.dcloud.H5E4CA812.view.bean.ReceivingAddressBean;

public class SharePhotoPopWin extends PopupWindow {

    private Context mContext;

    private View view;
    @BindView(R.id.share_wx)
    TextView share_wx;
    @BindView(R.id.share_pyq)
    TextView share_pyq;
    @BindView(R.id.share_qq)
    TextView share_qq;
    @BindView(R.id.share_wb)
    TextView share_wb;
    @BindView(R.id.share_pop_exte)
    TextView share_pop_exte;

    String url, title, description;


    public SharePhotoPopWin(Context mContext, String url, String title, String description) {
        this.mContext = mContext;
        this.view = LayoutInflater.from(mContext).inflate(R.layout.share_pop, null);
        this.url = url;
        this.title = title;
        this.description = description;
        ButterKnife.bind(this, view);
        iwxapi = WXAPIFactory.createWXAPI(mContext, GlobleApplication.WXappId, false);

        initView();
    }

    @OnClick({R.id.share_wx,
            R.id.share_pyq,
            R.id.share_qq,
            R.id.share_wb,
            R.id.share_pop_exte,
    })
    void click(View view) {
        switch (view.getId()) {
            case R.id.share_wx:
                shareWeb(WXHH, url, title, description);
                break;
            case R.id.share_pyq:
                shareWeb(WXPYQ, url, title, description);
                break;
            case R.id.share_qq:
                ToastUtil.makeText(mContext, "尚未开发");
                break;
            case R.id.share_wb:
                ToastUtil.makeText(mContext, "尚未开发");
                break;
            case R.id.share_pop_exte:
                dismiss();
                break;


        }
    }


    //微信-------------------------------------------------
    IWXAPI iwxapi;
    int WXPYQ = SendMessageToWX.Req.WXSceneTimeline;//分享到朋友圈
    int WXHH = SendMessageToWX.Req.WXSceneSession;//分享到微信

    private void shareWeb(int WXType, String url, String title, String description) {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = description;
        Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.taxmagread);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 150, 150, true);
        bmp.recycle();
        msg.thumbData = bmpToByteArray(thumbBmp, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = WXType;
        iwxapi.sendReq(req);

    }

    private static String buildTransaction(String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : (type + System.currentTimeMillis());
    }

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private void initView() {
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

}
