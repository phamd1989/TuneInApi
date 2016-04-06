package com.example.dungpham.tuneintest.service;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import retrofit.RequestInterceptor;
import com.example.dungpham.tuneintest.TuneinApplication;

/**
 * Created by dungpham on 4/5/16.
 */
public class TuneinRequestInterceptor implements RequestInterceptor {
    @Override
    public void intercept(RequestFacade request) {
        request.addHeader("Accept", "application/json");

//        try {
//            PackageInfo pInfo = null;
//            pInfo = TuneinApplication.getInstance().getPackageManager().getPackageInfo(TuneinApplication.getInstance().getPackageName(), 0);
//            request.addHeader("X-ANDROID-VERSION", String.valueOf(pInfo.versionCode));
//            if (pInfo.versionName != null) {
//                request.addHeader("User-agent", "Android v" + pInfo.versionName + " - SDK:" + Build.VERSION.SDK_INT);
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
    }
}
