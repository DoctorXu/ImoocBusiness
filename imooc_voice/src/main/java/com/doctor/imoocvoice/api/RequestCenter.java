package com.doctor.imoocvoice.api;

/**
 * Created by Doctor on 2021/1/15.
 */

public class RequestCenter {
    private static final String ROOT_URL = "http://imooc.com/api";
    //private static final String ROOT_URL = "http://39.97.122.129";

    /**
     * 首页请求接口
     */
    private static String HOME_RECOMMAND = ROOT_URL + "/module_voice/home_recommand";

    private static String HOME_RECOMMAND_MORE = ROOT_URL + "/module_voice/home_recommand_more";

    private static String HOME_FRIEND = ROOT_URL + "/module_voice/home_friend";

    /**
     * 登陆接口
     */
    public static String LOGIN = ROOT_URL + "/module_voice/login_phone";
}
