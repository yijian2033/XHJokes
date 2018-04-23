package com.yijian2033.xhjokes.app;

import android.app.Application;
import android.content.Context;

/**
 * @author zhangyj
 * @version [XHJokes, 2018-04-18]
 */
public class XHJokesApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
