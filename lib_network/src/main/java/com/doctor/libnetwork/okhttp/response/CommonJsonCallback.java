package com.doctor.libnetwork.okhttp.response;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.doctor.libnetwork.okhttp.exception.OkHttpException;
import com.doctor.libnetwork.okhttp.listener.DisposeDataHandler;
import com.doctor.libnetwork.okhttp.listener.DisposeDataListener;
import com.doctor.libnetwork.okhttp.utils.ResponseEntityToModule;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Doctor on 2021/1/14.
 */

public class CommonJsonCallback implements Callback {

    protected final String EMPTY_MSG = "";

    protected final int NETWORK_ERROR = -1;
    protected final int JSON_ERROR = -2;
    protected final int OTHER_ERROR = -3;

    private DisposeDataListener mListener;
    private Class<?> mClass;
    private Handler mDeliveryHandler;

    public CommonJsonCallback(DisposeDataHandler handler) {
        mListener = handler.mListener;
        mClass = handler.mClass;
        mDeliveryHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void onFailure(@NotNull Call call, @NotNull IOException e) {
        mDeliveryHandler.post(new Runnable() {
            @Override
            public void run() {
                mListener.onFailure(new OkHttpException(NETWORK_ERROR, e.getMessage()));
            }
        });
    }

    @Override
    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        final String result = response.body().toString();
        mDeliveryHandler.post(new Runnable() {
            @Override
            public void run() {
                handleResponse(result);
            }
        });
    }

    private void handleResponse(String result) {
        if (result == null || TextUtils.isEmpty(result)) {
            mListener.onFailure(new OkHttpException(NETWORK_ERROR, EMPTY_MSG));
            return;
        }

        if (mClass == null) {
            // 业务层不希望网络框架解析数据
            mListener.onSuccess(result);
        } else {
            // 业务层希望网络框架帮忙解析数据
            Object object = ResponseEntityToModule.parseJsonToModule(result, mClass);
            if (object != null) {
                mListener.onSuccess(object);
            } else {
                mListener.onFailure(new OkHttpException(JSON_ERROR, EMPTY_MSG));
            }
        }
    }
}
