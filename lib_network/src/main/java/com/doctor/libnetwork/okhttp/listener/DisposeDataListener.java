package com.doctor.libnetwork.okhttp.listener;

/**
 * Created by Doctor on 2021/1/14.
 */

public interface DisposeDataListener {

    /**
     * 请求成功的回调处理
     * @param responseObj
     */
    void onSuccess(Object responseObj);

    /**
     * 请求失败的回调处理
     * @param responseObj
     */
    void onFailure(Object responseObj);
}
