package com.example.dungpham.tuneintest.service;

import android.widget.Toast;

import com.example.dungpham.tuneintest.BuildConfig;
import com.example.dungpham.tuneintest.TuneinApplication;
import com.example.dungpham.tuneintest.model.ApiException;
import com.example.dungpham.tuneintest.model.BaseElement;
import com.example.dungpham.tuneintest.model.ElementDeserializer;
import com.example.dungpham.tuneintest.model.ElementWrapper;
import com.example.dungpham.tuneintest.model.ErrorFuc1Handler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.android.AndroidLog;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by dungpham on 4/5/16.
 */
public class ReactiveApiService extends TuneinRequestInterceptor{
    private static final String mBaseUrl = "http://opml.radiotime.com";
    public static final String TAG = "TUNEIN_SERVICE";
    private Api mApi;
    private static volatile ReactiveApiService mInstance = null;

    public static ReactiveApiService getInstance() {
        if (mInstance == null) {
            synchronized (ReactiveApiService.class) {
                if (mInstance == null) {
                    mInstance = new ReactiveApiService();
                }
            }
        }
        return mInstance;
    }

    private ReactiveApiService() {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(30, TimeUnit.SECONDS);
        client.setReadTimeout(3, TimeUnit.MINUTES);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(BaseElement.class, new ElementDeserializer())
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLog(new AndroidLog(TAG))
                .setEndpoint(mBaseUrl)
                .setRequestInterceptor(this)
                .setConverter(new GsonConverter(gson))
                .setClient(new OkClient(client))
                .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                .build();

        mApi = restAdapter.create(Api.class);
    }

    public Observable<List<BaseElement>> getDirectory() {
        return mApi.getDirectory()
                .flatMap(new ErrorFuc1Handler())
                .map(new Func1<ElementWrapper, List<BaseElement>>() {
                    @Override
                    public List<BaseElement> call(ElementWrapper elementWrapper) {
                        return elementWrapper.getBody();
                    }
                })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (throwable instanceof ApiException) {
                            TuneinApplication.getInstance().getMainThreadHandler().post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(TuneinApplication.getInstance(), "Api call is wrong!", Toast.LENGTH_SHORT);
                                }
                            });
                        } else if (throwable instanceof RetrofitError) {
                            TuneinApplication.getInstance().getMainThreadHandler().post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(TuneinApplication.getInstance(), "An internal server error has occured", Toast.LENGTH_SHORT);
                                }
                            });
                        }
                    }
                });
    }

    public Observable<List<BaseElement>> getLink(String link) {
        return mApi.getLink(link)
                .flatMap(new ErrorFuc1Handler())
                .map(new Func1<ElementWrapper, List<BaseElement>>() {
                    @Override
                    public List<BaseElement> call(ElementWrapper elementWrapper) {
                        return elementWrapper.getBody();
                    }
                })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (throwable instanceof ApiException) {
                            TuneinApplication.getInstance().getMainThreadHandler().post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(TuneinApplication.getInstance(), "Api call is wrong!", Toast.LENGTH_SHORT);
                                }
                            });
                        } else if (throwable instanceof RetrofitError) {
                            TuneinApplication.getInstance().getMainThreadHandler().post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(TuneinApplication.getInstance(), "An internal server error has occured", Toast.LENGTH_SHORT);
                                }
                            });
                        }
                    }
                });
    }

    public interface Api {
        @GET("/Browse.ashx?render=json")
        Observable<ElementWrapper> getDirectory();

        @GET("/{path}")
        Observable<ElementWrapper> getLink(@Path(value = "path", encode = false) String path);
    }
}
