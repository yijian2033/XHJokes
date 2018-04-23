package com.yijian2033.xhjokes.observer;

import android.content.Context;

import com.yijian2033.xhjokes.widget.ProgressCancelListener;
import com.yijian2033.xhjokes.widget.ProgressHandler;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author zhangyj
 * @version [XHJokes, 2018-04-19]
 */
public class ListObserver<T> implements Observer<T>, ProgressCancelListener {
    private Context context;
    private ListObserverOnListener listener;
    private Disposable disposable;
    private ProgressHandler progressHandler;

    public ListObserver(Context context, ListObserverOnListener listener) {
        this.context = context;
        this.listener = listener;
        progressHandler = new ProgressHandler(context, true, this);
    }

    private void showProgress() {
        if (progressHandler != null) {
            progressHandler.obtainMessage(ProgressHandler.SHOW_PROGRESS).sendToTarget();
        }
    }

    private void dissProgress() {
        if (progressHandler != null) {
            progressHandler.obtainMessage(ProgressHandler.DISMISS_PROGRESS).sendToTarget();
            progressHandler = null;
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.disposable = d;
        showProgress();
    }

    @Override
    public void onNext(T t) {
        listener.onNextListener(t);
    }

    @Override
    public void onError(Throwable e) {
        dissProgress();
    }

    @Override
    public void onComplete() {
        dissProgress();
    }

    @Override
    public void onProgressCancel() {
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
