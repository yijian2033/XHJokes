package com.yijian2033.xhjokes.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.yijian2033.xhjokes.app.XHJokesApplication;

/**
 * @author zhangyj
 * @version [XHJokes, 2018-04-19]
 */
public class NetWorkUtils {

    /**
     * 判断网络是否可用
     *
     * @return
     */
    public static boolean isNetConnected() {
        ConnectivityManager systemService = (ConnectivityManager) XHJokesApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (systemService != null) {
            NetworkInfo activeNetworkInfo = systemService.getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                NetworkInfo.State state = activeNetworkInfo.getState();
                if (state == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

}
