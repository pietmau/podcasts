package com.pietrantuono.podcasts.singlepodcast;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.pietrantuono.podcasts.R;

import butterknife.BindView;

public class SinglePodcastActivity extends AppCompatActivity implements SinglePodcastView {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.image) ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podcast);
        setSupportActionBar(toolbar);
    }
}
