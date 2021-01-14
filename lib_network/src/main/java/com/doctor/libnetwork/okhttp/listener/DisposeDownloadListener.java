package com.doctor.libnetwork.okhttp.listener;

/**
 * Created by Doctor on 2021/1/14.
 */

public interface DisposeDownloadListener extends DisposeDataListener{

    void onProgress(int progress);
}
