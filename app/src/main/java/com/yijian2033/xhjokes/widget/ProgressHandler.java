package com.yijian2033.xhjokes.widget;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

/**
 * @author zhangyj
 * @version [XHJokes, 2018-04-19]
 */
public class ProgressHandler extends Handler {
    public static final int SHOW_PROGRESS = 1;
    public static final int DISMISS_PROGRESS = 2;
    private Context context;
    private boolean isCancel;
    private ProgressCancelListener listener;
    private ProgressDialog progressDialog;

    public ProgressHandler(Context context, boolean isCancel, ProgressCancelListener listener) {
        this.context = context;
        this.isCancel = isCancel;
        this.listener = listener;
    }

    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setCancelable(isCancel);
            progressDialog.setTitle("加载中...");
            progressDialog.show();
        } else {
            if (!progressDialog.isShowing()) {
                progressDialog.show();
            }
        }
    }

    private void dissMissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_PROGRESS:
                showProgressDialog();
                break;
            case DISMISS_PROGRESS:
                dissMissProgressDialog();
                break;
        }
    }
}
