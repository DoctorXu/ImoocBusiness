package com.doctor.libnetwork.okhttp.response;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.doctor.libnetwork.okhttp.exception.OkHttpException;
import com.doctor.libnetwork.okhttp.listener.DisposeDataHandler;
import com.doctor.libnetwork.okhttp.listener.DisposeDataListener;
import com.doctor.libnetwork.okhttp.listener.DisposeDownloadListener;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.EnumMap;

import androidx.annotation.NonNull;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Doctor on 2021/1/14.
 */

public class CommonFileCallback implements Callback {
    protected final String EMPTY_MSG = "";

    protected final int NETWORK_ERROR = -1;
    protected final int IO_ERROR = -2;
    protected final int OTHER_ERROR = -3;

    /**
     * 将其他线程的数据转发到UI线程
     */
    private static final int PROGRESS_MESSAGE = 0x01;
    private Handler mDeliveryHandler;
    private DisposeDownloadListener mListener;
    private String mFilePath;
    private int mProgress;

    public CommonFileCallback(DisposeDataHandler handler) {
        this.mListener = (DisposeDownloadListener) handler.mListener;
        this.mFilePath = handler.mSource;
        this.mDeliveryHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case PROGRESS_MESSAGE:
                        mListener.onProgress((int) msg.obj);
                        break;

                    default:
                        break;
                }
            }
        };

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
        File file = handleResponse(response);
        mDeliveryHandler.post(new Runnable() {
            @Override
            public void run() {
                if (file != null) {
                    mListener.onSuccess(file);
                } else {
                    mListener.onFailure(new OkHttpException(IO_ERROR, EMPTY_MSG));
                }
            }
        });
    }

    private File handleResponse(Response response) {
        if (response == null) {
            return null;
        }

        InputStream inputStream = null;
        File file = null;
        FileOutputStream fileOutputStream = null;
        byte[] buffer = new byte[2048];
        int length;
        double sumLength = 0;
        double currentLength = 0;
        try {
            file = new File(mFilePath);
            fileOutputStream = new FileOutputStream(file);
            inputStream = response.body().byteStream();
            sumLength = response.body().contentLength();
            while ((length = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, buffer.length);
                currentLength += length;
                mProgress = (int) ((currentLength / sumLength) * 100);
                mDeliveryHandler.obtainMessage(PROGRESS_MESSAGE, mProgress).sendToTarget();
                fileOutputStream.flush();
            }
        } catch (Exception e) {
            file = null;
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                file = null;
            }
        }
        return file;
    }
}
