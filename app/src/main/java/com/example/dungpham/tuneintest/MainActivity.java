package com.example.dungpham.tuneintest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.dungpham.tuneintest.activity.LinkActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TuneinApplication.getInstance().getMainThreadHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                LinkActivity.launchLinkActivity(MainActivity.this, "Browse.ashx?render=json", LinkActivity.DEFAULT_TITLE);
            }
        }, 2000);
    }
}
