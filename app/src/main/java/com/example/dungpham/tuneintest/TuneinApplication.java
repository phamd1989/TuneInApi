package com.example.dungpham.tuneintest;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

/**
 * Created by dungpham on 4/5/16.
 */
public class TuneinApplication extends Application {
    private static Context mContext;
    private static final String mBaseUrl = "http://opml.radiotime.com/Browse.ashx";
    public static TuneinApplication mInstance;
    private Handler mMainThreadHandler;

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
}
