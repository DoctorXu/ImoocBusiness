package com.doctor.libnetwork.okhttp.exception;

/**
 * Created by Doctor on 2021/1/14.
 */

public class OkHttpException extends Exception {
    private static final long serialVersionID = 1L;

    /**
     * the server return code
     */
    private int ecode;

    /**
     * the server return error message
     */
    private Object emsg;

    public OkHttpException(int ecode, Object emsg) {
        this.ecode = ecode;
        this.emsg = emsg;
    }

    public int getEcode() {
        return ecode;
    }

    public Object getEmsg() {
        return emsg;
    }
}
