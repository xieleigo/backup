package io.dcloud.H5E4CA812.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dcloud.github.barteksc.pdfviewer.PDFView;
import io.dcloud.H5E4CA812.R;
import io.dcloud.H5E4CA812.base.BaseActivity;
import io.dcloud.H5E4CA812.protocol.Request;
import io.dcloud.H5E4CA812.util.DownloadUtil;
import io.dcloud.H5E4CA812.util.ImageViewUtil;
import io.dcloud.H5E4CA812.util.LogUtil;
import io.dcloud.H5E4CA812.util.StringUtil;

/**
 * 查看发票电子版
 */
public class BillingInformationInfoImageActivity extends BaseActivity {

    @BindView(R.id.pdfView)
    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing_information_info_image);
        ButterKnife.bind(this);
        initTitle();
        initData();
    }

    @Override
    public void onResponsed(Request req) {

    }

    String fileNamestr;

    private void initData() {
        String path = getIntent().getStringExtra("URL");
        LogUtil.i("url", path);
        StringUtil.isExist("/sdcard/shuiwuyuedu");
        StringUtil.isExist("/sdcard/shuiwuyuedu/files");
        String[] jianame = path.split("/");
        fileNamestr = jianame[jianame.length - 1] + ".pdf";
        DownloadUtil.get().download(path, "/sdcard/shuiwuyuedu/files", fileNamestr, new DownloadUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess(File file) {
                LogUtil.i("onDownloading", file.getAbsolutePath() + "");
                Message msgs = new Message();
                msgs.obj = "/sdcard/shuiwuyuedu/files/" + fileNamestr;
                msgs.what = 100;
                handler.sendMessage(msgs);
            }

            @Override
            public void onDownloading(int progress) {
            }

            @Override
            public void onDownloadFailed(Exception e) {
                Message msg = new Message();
                msg.obj = e.toString();
                msg.what = 200;
                handler.sendMessage(msg);
            }
        });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    File file = new File(msg.obj + "");
                    pdfView.fromFile(file).load();
                    break;
                case 200:
                    LogUtil.i("Message", msg.obj + "");
                    Toast.makeText(getApplicationContext(), "打开失败" + msg.obj, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private void initTitle() {
        IsBackBut(true);
        IsTitleEdittext(false);
        IsTitleName(true);
        setTitleName("查看发票");
    }

    @OnClick({R.id.back_but})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.back_but:
                this.onBackPressed();
                break;
        }
    }
}
