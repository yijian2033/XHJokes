package com.yijian2033.xhjokes.http;

import com.yijian2033.xhjokes.mode.JokeBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author zhangyj
 * @version [XHJokes, 2018-04-18]
 */
public interface ApiService {

    @GET("list.php")
    Observable<JokeBean> getAllJokes(@Query("key") String key,
                                     @Query("page") int page,
                                     @Query("pagesize") int pagesize,
                                     @Query("sort") String sort,
                                     @Query("time") String time);


}
