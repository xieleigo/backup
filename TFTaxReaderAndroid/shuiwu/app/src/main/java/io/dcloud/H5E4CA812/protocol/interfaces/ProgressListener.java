package io.dcloud.H5E4CA812.protocol.interfaces;

/**
 * 进度回调接口，比如用于文件上传与下载
 */

public interface ProgressListener {
    void onProgress(long currentBytes, long contentLength, boolean done);
}
