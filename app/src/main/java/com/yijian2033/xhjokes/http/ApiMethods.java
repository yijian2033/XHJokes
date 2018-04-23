package com.yijian2033.xhjokes.http;

import android.util.Log;

import com.yijian2033.xhjokes.constant.Constant;
import com.yijian2033.xhjokes.mode.JokeBean;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author zhangyj
 * @version [XHJokes, 2018-04-19]
 */
public class ApiMethods {

    private static int retryTime = 0;
    private static int maxRetryTime = 5;
    private static int time = 0;
    private static final String TAG = ApiMethods.class.getSimpleName();

    public static void apiSubscribe(Observable observable, Observer observer) {
        observable
                //网络错误 尝试请求连接
                .retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {
                        return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(Throwable throwable) throws Exception {
                                Log.d(TAG, "error :" + throwable.toString());
                                if (throwable instanceof IOException) {
                                    Log.d(TAG, "属于IO异常，需重试");
                                    if (retryTime < maxRetryTime) {
                                        retryTime++;
                                        Log.d(TAG, "重试次数 = " + retryTime);
                                        time = 1000 + retryTime * 1000;
                                        return Observable.just(1).delay(time, TimeUnit.MILLISECONDS);
                                    } else {
                                        return Observable.error(new Throwable("超过连接次数！！！" + retryTime));
                                    }
                                } else {
                                    return Observable.error(new Throwable("不是网络错误"));
                                }

                            }
                        });
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


    /**
     * 获取所有更新的
     *
     * @param observer
     * @param page
     * @param pageSize
     */
    public static void getListJoke(Observer observer, int page, int pageSize, String time) {
        ApiService apiService = ApiStrategy.getApiService();
        Observable<JokeBean> allJokes = apiService.getAllJokes(Constant.APP_KEY, page, pageSize, Constant.SORT_ASC, time);
        apiSubscribe(allJokes, observer);
    }

}
