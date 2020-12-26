package io.dcloud.H5E4CA812.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import dcloud.github.barteksc.pdfviewer.PDFView;
import io.dcloud.H5E4CA812.protocol.ApiType;
import io.dcloud.H5E4CA812.protocol.Request;
import io.dcloud.H5E4CA812.R;
import io.dcloud.H5E4CA812.base.BaseActivity;
import io.dcloud.H5E4CA812.util.DownloadUtil;
import io.dcloud.H5E4CA812.util.LogUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReadPDFActivity extends BaseActivity {

    @BindView(R.id.pdfView)
    PDFView pdfView;
    @BindView(R.id.baifenbi)
    TextView baifenbi;
    @BindView(R.id.pb_main_download)
    ProgressBar pb_main_download;
    @BindView(R.id.pb_main_download_layout)
    LinearLayout pb_main_download_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_pdf);
        ButterKnife.bind(this);
        initTitle();
        downPDF();
    }

    String downUrl = "";
    String path = "";
    String fileNamestr = "";

    private void downPDF() {
        downUrl = getIntent().getStringExtra("downUrl");
        path = getIntent().getStringExtra("path");
        fileNamestr = getIntent().getStringExtra("fileNamestr");
        File file = new File(path + "/" + fileNamestr);
        if (file.exists()) {
            baifenbi.setVisibility(View.GONE);
            pb_main_download_layout.setVisibility(View.GONE);
            pdfView.fromFile(file).load();
        } else {
            DownloadUtil.get().download(downUrl, path, fileNamestr, new DownloadUtil.OnDownloadListener() {
                @Override
                public void onDownloadSuccess(File file) {
                }

                @Override
                public void onDownloading(int progress) {
                    LogUtil.i("onDownloading", progress + "");
                    Message msg = new Message();
                    msg.obj = progress;
                    msg.what = 300;
                    handler.sendMessage(msg);

                    if (progress == 100) {
                        Message msgs = new Message();
                        msgs.obj = path + "/" + fileNamestr;
                        msgs.what = 100;
                        handler.sendMessage(msgs);
                    }
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
    }

    @OnClick({R.id.back_but})
    void click(View view) {
        switch (view.getId()) {
            case R.id.back_but:
                this.onBackPressed();
                break;
        }
    }

    @Override
    public void onResponsed(Request req) {

    }

    private void initTitle() {
        IsBackBut(true);
        IsTitleEdittext(false);
        IsTitleName(true);
        setTitleName("阅读原版");
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    File file = new File(msg.obj + "");
                    pdfView.fromFile(file).load();
                    baifenbi.setVisibility(View.GONE);
                    pb_main_download_layout.setVisibility(View.GONE);
                    break;
                case 200:
                    LogUtil.i("Message", msg.obj + "");
                    Toast.makeText(getApplicationContext(), "下载失败：" + msg.obj, Toast.LENGTH_SHORT).show();
                    break;
                case 300:
                    baifenbi.setText(msg.obj + "%");
                    pb_main_download.setProgress((int) msg.obj);
                    break;
            }
        }
    };
}
