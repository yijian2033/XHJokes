package com.yijian2033.xhjokes.constant;

/**
 * @author zhangyj
 * @version [XHJokes, 2018-04-19]
 */
public class Constant {

    public static final String BASE_URL = "http://v.juhe.cn/joke/content/";

    public static final String APP_KEY = "c6302f25f6e10716a03bcaaac0c5ea33";
    public static final String SORT_ASC = "asc";
    public static final String SORT_DESC = "desc";

    //读超时长，单位：毫秒
    public static final int READ_TIME_OUT = 7676;
    //连接时长，单位：毫秒
    public static final int CONNECT_TIME_OUT = 7676;
    /**
     * 设缓存有效期为两天
     */
    public static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;

}
