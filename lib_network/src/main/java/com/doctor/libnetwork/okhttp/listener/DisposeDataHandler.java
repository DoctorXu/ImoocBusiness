package com.doctor.libnetwork.okhttp.listener;

/**
 * Created by Doctor on 2021/1/14.
 */

public class DisposeDataHandler {
    public DisposeDataListener mListener;
    public Class<?> mClass;
    /**
     * 文件保存路径
     */
    public String mSource;

    public DisposeDataHandler(DisposeDataListener listener) {
        this.mListener = listener;
    }

    public DisposeDataHandler(DisposeDataListener listener, Class<?> clazz) {
        this.mListener = listener;
        this.mClass = clazz;
    }

    public DisposeDataHandler(DisposeDataListener listener, String source) {
        this.mListener = listener;
        this.mSource = source;
    }
}
