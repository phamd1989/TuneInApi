package com.example.dungpham.tuneintest.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.example.dungpham.tuneintest.R;
import com.example.dungpham.tuneintest.adapter.ElementAdapter;
import com.example.dungpham.tuneintest.model.BaseElement;
import com.example.dungpham.tuneintest.service.ReactiveApiService;

import java.util.List;

import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LinkActivity extends AppCompatActivity {
    private static final String LINK = "link";
    private static final String TITLE = "title";
    public static final String DEFAULT_TITLE = "TuneIn";
    private ElementAdapter mAdapter;
    private String mLink;
    private String mTitle;

    public static void launchLinkActivity(Context context, String link, String title) {
        Intent intent = new Intent(context, LinkActivity.class);
        intent.putExtra(LINK, link);
        intent.putExtra(TITLE, title);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link);
        ButterKnife.bind(this);

        mLink = getIntent().getStringExtra(LINK);
        mTitle = getIntent().getStringExtra(TITLE);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new ElementAdapter(this);
        recyclerView.setAdapter(mAdapter);

        setupActionBar(mTitle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ReactiveApiService.getInstance().getLink(mLink)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<BaseElement>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(ReactiveApiService.TAG, e.toString());
                    }

                    @Override
                    public void onNext(List<BaseElement> elements) {
                        mAdapter.setData(elements);
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupActionBar(String title) {
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(title != null ? title : "");
            if (title != null && !title.equals(DEFAULT_TITLE)) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            } else if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            }
        }
    }
}
