package com.example.dungpham.tuneintest;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.example.dungpham.tuneintest.model.BaseElement;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import java.util.List;

import rx.Observable;

/**
 * Created by dungpham on 4/5/16.
 */
public class TuneinApplication extends Application {
    public static TuneinApplication mInstance;
    private Handler mMainThreadHandler;
    protected static Observable<List<BaseElement>> mListElementObserver;

    public TuneinApplication() {
        super();
        TuneinApplication.mInstance = this;
    }

    public static TuneinApplication getInstance() {return mInstance;}

    @Override
    public void onCreate() {
        super.onCreate();
        mMainThreadHandler = new Handler(this.getMainLooper());
        ImagePipelineConfig ipconfig = ImagePipelineConfig.newBuilder(this)
                .setDownsampleEnabled(true)
                .build();
        Fresco.initialize(this, ipconfig);
    }

    public Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    public Observable<List<BaseElement>> getListElementObserver() {
        return mListElementObserver;
    }

    public void setListElementObserver(Observable<List<BaseElement>> observer) {
        this.mListElementObserver = observer;
    }
}
