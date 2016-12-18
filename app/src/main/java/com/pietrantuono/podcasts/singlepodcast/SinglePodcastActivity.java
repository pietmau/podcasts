package com.pietrantuono.podcasts.singlepodcast;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.pietrantuono.podcasts.R;
import com.pietrantuono.podcasts.addpodcast.view.ApiLevelCheckerImpl;
import com.pietrantuono.podcasts.main.dagger.DaggerMainComponent;
import com.pietrantuono.podcasts.main.dagger.MainModule;
import com.pietrantuono.podcasts.main.view.TransitionsFramework;
import com.pietrantuono.podcasts.main.view.TransitionsFrameworkImpl;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SinglePodcastActivity extends AppCompatActivity implements SinglePodcastView {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.image) ImageView imageView;
    @Inject TransitionsFramework transitionsFramework;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podcast);
        ButterKnife.bind(SinglePodcastActivity.this);
        setUpActionBar();
        DaggerSinglePodcastComponent.builder().build().inject(SinglePodcastActivity.this);
        transitionsFramework.initDetailTransitions(SinglePodcastActivity.this);
    }

    private void setUpActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
    }
}
