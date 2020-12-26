package io.dcloud.H5E4CA812.view.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.dcloud.H5E4CA812.GlobleApplication;
import io.dcloud.H5E4CA812.R;
import io.dcloud.H5E4CA812.base.BaseActivity;
import io.dcloud.H5E4CA812.constant.bean.ItmeHomepagerJournalDetailsReadAdpterListbean;
import io.dcloud.H5E4CA812.protocol.ApiType;
import io.dcloud.H5E4CA812.protocol.Request;
import io.dcloud.H5E4CA812.util.ImageViewUtil;
import io.dcloud.H5E4CA812.util.LogUtil;
import io.dcloud.H5E4CA812.util.StringUtil;
import io.dcloud.H5E4CA812.util.ToastUtil;
import io.dcloud.H5E4CA812.view.adapter.HomePagerJournalDetailsReadAdapter;
import io.dcloud.H5E4CA812.view.widget.DingyuePhotoPopWin;
import io.dcloud.H5E4CA812.view.widget.RoundCornerDialog;
import io.dcloud.H5E4CA812.view.widget.SharePhotoPopWin;

/**
 * 期刊详情
 */
public class HomepagerJournalDetails extends BaseActivity {
    @BindView(R.id.journal_details_title_layout)
    RelativeLayout journal_details_title_layout;
    @BindView(R.id.journal_details_title_image)
    ImageView journal_details_title_image;
    @BindView(R.id.journal_details_layout)
    LinearLayout journal_details_layout;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.unname)
    TextView unname;
    @BindView(R.id.num)
    TextView num;
    @BindView(R.id.zbdw)
    TextView zbdw;
    @BindView(R.id.cbzq)
    TextView cbzq;
    @BindView(R.id.issn)
    TextView issn;
    @BindView(R.id.cn)
    TextView cn;
    @BindView(R.id.cbd)
    TextView cbd;
    @BindView(R.id.yz)
    TextView yz;
    @BindView(R.id.kb)
    TextView kb;
    @BindView(R.id.yfdh)
    TextView yfdh;
    @BindView(R.id.cksj)
    TextView cksj;
    @BindView(R.id.sc_button_layout)
    LinearLayout sc_button_layout;
    @BindView(R.id.scimage)
    ImageView scimage;
    @BindView(R.id.sctext)
    TextView sctext;
    @BindView(R.id.dy_button_layout)
    LinearLayout dy_button_layout;
    @BindView(R.id.recyclerview_homepager_journal_detals_read)
    RecyclerView mRecyclerView;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.baifenbi_layout)
    LinearLayout baifenbi_layout;
    @BindView(R.id.baifenbi)
    TextView baifenbi;
    @BindView(R.id.downbutton)
    ImageView downbutton;
    @BindView(R.id.downtext)
    TextView downtext;
    @BindView(R.id.yuedu_text)
    TextView yuedu_text;
    @BindView(R.id.dy_button_image)
    ImageView dy_button_image;
    @BindView(R.id.dy_button_text)
    TextView dy_button_text;

    private HomePagerJournalDetailsReadAdapter commonAdapter;
    private List<ItmeHomepagerJournalDetailsReadAdpterListbean.PubIssueCataBean> mDataList;
    ItmeHomepagerJournalDetailsReadAdpterListbean adpterListbean;
    String ReadSourceID = "";
    String TAG = "HomepagerJournalDetails";

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_details);
        ButterKnife.bind(this);
        initTitle();
        ReadSourceID = getIntent().getStringExtra("ReadSourceID");
        initView();
    }

    Map map = new HashMap();

    private void initView() {
        map.clear();
        map.put("PubIssueID", ReadSourceID);
        execApi(ApiType.GetProductDetail, map);

        commonAdapter = new HomePagerJournalDetailsReadAdapter(mDataList, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(commonAdapter);
    }

    private void initdetails() {
        name.setText(adpterListbean.getPubIssueName());
        unname.setText("null");//
        num.setText(adpterListbean.getPubIssueYear() + "年 第" + adpterListbean.getPubIssueNum() + "期 总第" + adpterListbean.getPubIssueSumNum() + "期");
        zbdw.setText("主办单位：" + adpterListbean.getPubIssueExeUnit());
        cbzq.setText("出版周期：" + adpterListbean.getPubCycle());//
        issn.setText("ISSN：" + adpterListbean.getPubIssueISSN());
        cn.setText("CN：" + adpterListbean.getPubIssueCN());
        cbd.setText("出版地：" + adpterListbean.getPubPlace());//
        yz.setText("语种：" + adpterListbean.getPubLanguage());//
        kb.setText("开本：" + adpterListbean.getPubSize());//
        yfdh.setText("邮发代号：" + "null");//
        cksj.setText("创刊时间：" + adpterListbean.getPubStartBuildYear() + "年");//
        downUrl = adpterListbean.getPubIssuePDF();
        if (adpterListbean.isAllowBuy()) {
            dy_button_layout.setVisibility(View.VISIBLE);
        } else {
            dy_button_layout.setVisibility(View.GONE);
        }
        if (adpterListbean.isIsFavorite()) {
            scimage.setBackground(this.getDrawable(R.drawable.qksc02));
            sctext.setTextColor(this.getResources().getColor(R.color.CollectFragment_buttont_Text));
        } else {
            scimage.setBackground(this.getDrawable(R.drawable.qksc01));
            sctext.setTextColor(this.getResources().getColor(R.color.black));
        }
        if (adpterListbean.isIsSubscribe()) {
            yuedu_text.setText("阅读");
//            dy_button_image.setBackground(this.getDrawable(R.drawable.qkdy02));
            dy_button_text.setTextColor(this.getResources().getColor(R.color.CollectFragment_buttont_Text));
        } else {
            yuedu_text.setText("试读");
//            dy_button_image.setBackground(this.getDrawable(R.drawable.qkdy01));
            dy_button_text.setTextColor(this.getResources().getColor(R.color.black));

        }

        StringUtil.isExist("/sdcard/shuiwuyuedu");
        StringUtil.isExist("/sdcard/shuiwuyuedu/files");
        String fileName[] = downUrl.split("/");
        fileNamestr = fileName[fileName.length - 1];
        downUrl = "http://" + ApiType.localDefValue + downUrl;
        file = new File(path + "/" + fileNamestr);

//        if (file.exists()) {
//            downbutton.setBackground(getResources().getDrawable(R.drawable.qkxz02));
//            downtext.setTextColor(this.getResources().getColor(R.color.CollectFragment_buttont_Text));
//        } else {
//            downbutton.setBackground(this.getDrawable(R.drawable.qkxz01));
//            downtext.setTextColor(this.getResources().getColor(R.color.black));
//        }
        ImageViewUtil.setImage(this, adpterListbean.getPubIssueCoverPath(), image);


    }

    File file;

    private void initTitle() {
        IsBackBut(true);
        IsTitleEdittext(false);
        IsTitleName(true);
        setTitleName("期刊详情");
    }

    String downUrl = "";
    String path = "/sdcard/shuiwuyuedu/files";
    int downType = 0;//为了确定是否是下载状态
    String fileNamestr;

    @OnClick({R.id.back_but, R.id.journal_details_title_layout, R.id.sc_button_layout, R.id.dy_button_layout,
            R.id.down_layout, R.id.yuedu_layout})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.back_but:
                HomepagerJournalDetails.this.onBackPressed();
                break;
            case R.id.journal_details_title_layout:
                Intent intent = new Intent(this, QKDetailsSearchActivity.class);
                intent.putExtra("PubID", adpterListbean.getPubID());
                intent.putExtra("name", adpterListbean.getPubIssueName());
                startActivityForResult(intent, 100);
                break;
            case R.id.sc_button_layout:
                if (StringUtil.isEmpty(GlobleApplication.authorization)) {
                    StringUtil.showLoginDialog(HomepagerJournalDetails.this);
                    return;
                }
                map.clear();
                if (adpterListbean != null) {
                    if (adpterListbean.isIsFavorite()) {
                        map.put("ReadSourceID", ReadSourceID);
                        map.put("ReadFavoriteType", "20");
                        execApi(ApiType.GetFavorCancel, map);
                    } else {
                        map.put("ReadSourceID", ReadSourceID);
                        map.put("ReadParentID", ReadSourceID);
                        map.put("ReadFavoriteType", "20");
                        execApi(ApiType.GetFavorAdd, map);
                    }
                }
                break;
            case R.id.dy_button_layout:
                if (adpterListbean.isIsSubscribe()) {
                    showdingyuetishiDialog(HomepagerJournalDetails.this);
                    return;
                }
                if (StringUtil.isEmpty(GlobleApplication.authorization)) {
                    StringUtil.showLoginDialog(HomepagerJournalDetails.this);
                    return;
                }
                showPop(adpterListbean.getProdID());
                break;
            case R.id.yuedu_layout:
                Intent intent1 = new Intent(HomepagerJournalDetails.this, ReadPDFActivity.class);
                intent1.putExtra("downUrl", downUrl);
                intent1.putExtra("path", path);
                intent1.putExtra("fileNamestr", fileNamestr);
                startActivity(intent1);
                break;
            case R.id.down_layout:

                SharePhotoPopWin sharePhotoPopWin = new SharePhotoPopWin(HomepagerJournalDetails.this, adpterListbean.getSourceUrl(), adpterListbean.getPubIssueName(), adpterListbean.getPubIssueExeUnit());
                sharePhotoPopWin.showAtLocation(findViewById(R.id.journal_details_title_layout), Gravity.CENTER, 0, 0);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && data != null) {
            ReadSourceID = data.getStringExtra("ReadSourceID");
            initView();
        }
    }

    Gson gson = new Gson();

    @Override
    public void onResponsed(Request req) {
        if (req.getData() != null) {
            try {
                if (req.getData().getUrl().contains("/Product/Detail")) {
                    JSONObject jsonObject = new JSONObject(gson.toJson(req.getData().getData()));
                    adpterListbean = gson.fromJson(jsonObject.toString(), ItmeHomepagerJournalDetailsReadAdpterListbean.class);
                    commonAdapter.setmDataList(adpterListbean.getPubIssueCata());
                    initdetails();
                }
                if (req.getData().getUrl().contains("/Favor/Add")) {
                    if (req.getData().getRet()) {
                        adpterListbean.setIsFavorite(true);
                        scimage.setBackground(this.getDrawable(R.drawable.qksc02));
                        sctext.setTextColor(this.getResources().getColor(R.color.CollectFragment_buttont_Text));
                    }
                }
                if (req.getData().getUrl().contains("/Cart/Add") || req.getData().getUrl().contains("/Favor/Cancel")) {
                    if (req.getData().getRet()) {
                        ToastUtil.makeText(this, req.getData().getMsg());
                    }
                }

            } catch (Exception e) {
                LogUtil.i("Exception", e.toString());
            }
        } else {
            if (req.getErrorMsg().equals("authorization参数不能为空")) {
                StringUtil.showLoginDialog(HomepagerJournalDetails.this);
            }
        }
    }


    @Override
    public void onResponsedError(Request req) {
        super.onResponsedError(req);
        LogUtil.i("onResponsedError", req.toString());
    }


    public void showPop(long rid) {
        if (adpterListbean != null) {
            DingyuePhotoPopWin takePhotoPopWin = new DingyuePhotoPopWin(HomepagerJournalDetails.this, rid, adpterListbean.getPubIssueID(), adpterListbean.getPubID(), adpterListbean.getPubIssueYear());
            takePhotoPopWin.showAtLocation(findViewById(R.id.name), Gravity.CENTER, 0, 0);
        }
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    Toast.makeText(getApplicationContext(), "下载成功：" + msg.obj, Toast.LENGTH_SHORT).show();
                    baifenbi_layout.setVisibility(View.GONE);
                    downType = 0;
                    downbutton.setBackground(getResources().getDrawable(R.drawable.qkxz02));
                    downtext.setTextColor(HomepagerJournalDetails.this.getResources().getColor(R.color.CollectFragment_buttont_Text));
                    break;
                case 200:
                    downType = 0;
                    LogUtil.i("Message", msg.obj + "");
                    Toast.makeText(getApplicationContext(), "下载失败：" + msg.obj, Toast.LENGTH_SHORT).show();
                    baifenbi_layout.setVisibility(View.GONE);
                    break;
                case 300:
                    baifenbi.setText(msg.obj + "%");
                    break;
            }
        }
    };

    public void showdingyuetishiDialog(Context context) {
        View view = View.inflate(context, R.layout.login_go_btn, null);
        RoundCornerDialog roundCornerDialog = new RoundCornerDialog(context, 0, 0, view, R.style.RoundCornerDialog);
        roundCornerDialog.show();
        roundCornerDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        roundCornerDialog.setOnKeyListener(keylistener);//设置点击返回键Dialog不消失

        TextView tv_message = (TextView) view.findViewById(R.id.tv_message);
        TextView tv_logout_confirm = (TextView) view.findViewById(R.id.tv_logout_confirm);
        TextView tv_logout_cancel = (TextView) view.findViewById(R.id.tv_logout_cancel);
        tv_logout_cancel.setVisibility(View.GONE);
        tv_message.setText("你已经拥有该文章刊阅览权限，如继续购买时，系统自动分配阅读码，阅读码可以转移给其他用户使用。");

        //确定
        tv_logout_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roundCornerDialog.dismiss();
                showPop(adpterListbean.getProdID());
            }
        });
        //取消
        tv_logout_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roundCornerDialog.dismiss();
            }
        });
    }
    public DialogInterface.OnKeyListener keylistener = new DialogInterface.OnKeyListener() {
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                return true;
            } else {
                return false;
            }
        }
    };

}
