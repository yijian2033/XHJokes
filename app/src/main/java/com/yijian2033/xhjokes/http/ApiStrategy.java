package com.yijian2033.xhjokes.http;

import android.text.TextUtils;

import com.yijian2033.xhjokes.app.XHJokesApplication;
import com.yijian2033.xhjokes.constant.Constant;
import com.yijian2033.xhjokes.util.NetWorkUtils;

import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.yijian2033.xhjokes.constant.Constant.CACHE_STALE_SEC;
import static com.yijian2033.xhjokes.constant.Constant.READ_TIME_OUT;

/**
 * 设置网络缓存获取请求
 *
 * @author zhangyj
 * @version [XHJokes, 2018-04-19]
 */
public class ApiStrategy {

    private static ApiService apiService;

    public static ApiService getApiService() {
        if (apiService == null) {
            synchronized (ApiService.class) {
                if (apiService == null) {
                    new ApiStrategy();
                }
            }
        }
        return apiService;
    }


    private ApiStrategy() {

        //log打印
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //缓存  100M
        File file = new File(XHJokesApplication.getContext().getCacheDir(), "cache");
        Cache cache = new Cache(file, 1024 * 1024 * 100);
        //头部信息
        Interceptor headInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request build = chain.request().newBuilder()
                        //.addHeader("Content-Type", "application/json")//设置允许请求json数据
                        .build();
                return chain.proceed(build);
            }
        };

        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(READ_TIME_OUT, TimeUnit.MILLISECONDS)
                .connectTimeout(Constant.CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
                .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                .addInterceptor(mRewriteCacheControlInterceptor)
                .addInterceptor(headInterceptor)
                .addInterceptor(loggingInterceptor)
                .cache(cache)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }


    private Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {

            Request request = chain.request();
            String cacheControl = request.cacheControl().toString();
            //网络未连接
            if (!NetWorkUtils.isNetConnected()) {
                //判断cacheControl是否为空，是的话就重新网络获取
                request = request.newBuilder().cacheControl(TextUtils.isEmpty(cacheControl) ?
                        CacheControl.FORCE_NETWORK : CacheControl.FORCE_CACHE).build();
            }
            Response response = chain.proceed(request);

            if (NetWorkUtils.isNetConnected()) {
                return response.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return response.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC)
                        .removeHeader("Pragma")
                        .build();
            }

        }
    };


}
