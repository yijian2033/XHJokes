package com.yijian2033.xhjokes;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yijian2033.xhjokes.adapter.ListJokeAdapter;
import com.yijian2033.xhjokes.http.ApiMethods;
import com.yijian2033.xhjokes.mode.Data;
import com.yijian2033.xhjokes.mode.JokeBean;
import com.yijian2033.xhjokes.observer.ListObserver;
import com.yijian2033.xhjokes.observer.ListObserverOnListener;

import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity {


    private RecyclerView rvListJoke;
    private ListJokeAdapter listJokeAdapter;
    private SmartRefreshLayout refreshLayout;

    private int page = 2;
    private boolean isRefresh = false;
    private String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initData();
    }


    private void init() {
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.smartRefresh);
        //设置 Header 为 贝塞尔雷达 样式
        refreshLayout.setRefreshHeader(new BezierRadarHeader(this).setEnableHorizontalDrag(true));
        //设置 Footer 为 球脉冲 样式
        refreshLayout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));

        rvListJoke = (RecyclerView) findViewById(R.id.rv_joke);
        rvListJoke.setLayoutManager(new LinearLayoutManager(this));
        rvListJoke.setItemAnimator(new DefaultItemAnimator());
        rvListJoke.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        listJokeAdapter = new ListJokeAdapter(this);
        rvListJoke.setAdapter(listJokeAdapter);
    }

    private void initData() {
        time = getTime(0);
        ListObserverOnListener<JokeBean> listener = new ListObserverOnListener<JokeBean>() {
            @Override
            public void onNextListener(JokeBean jokeBean) {
                refreshLayout.finishLoadMore();
                refreshLayout.finishRefresh();

                ArrayList<Data> data = jokeBean.getResult().getData();
                listJokeAdapter.setDataSource(data, isRefresh);
            }
        };
        final ListObserver listObserver = new ListObserver(this, listener);
        ApiMethods.getListJoke(listObserver, page, 20, time);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                isRefresh = true;
                page = 2;
                time = getTime(86400);
                ApiMethods.getListJoke(listObserver, page, 20, time);
                Toast.makeText(MainActivity.this, "下拉刷新...", Toast.LENGTH_SHORT).show();
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                isRefresh = false;
                page++;
                ApiMethods.getListJoke(listObserver, page, 20, time);
                Toast.makeText(MainActivity.this, "加载更多...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getTime(long oneDay) {
        long time = System.currentTimeMillis() / 1000 - 105311838 + oneDay;//获取系统时间的10位的时间戳
        String str = String.valueOf(time);
        return str;
    }

}
