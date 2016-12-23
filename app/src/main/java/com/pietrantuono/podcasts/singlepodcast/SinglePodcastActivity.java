package com.pietrantuono.podcasts.singlepodcast;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.*;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.pietrantuono.podcasts.R;
import com.pietrantuono.podcasts.addpodcast.model.pojos.PodcastSearchResult;
import com.pietrantuono.podcasts.addpodcast.view.ApiLevelChecker;
import com.pietrantuono.podcasts.main.dagger.ImageLoaderModule;
import com.pietrantuono.podcasts.main.view.TransitionsFramework;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SinglePodcastActivity extends AppCompatActivity implements SinglePodcastView {
    public static final String SINGLE_PODCAST = "single_podcast";
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.image) ImageView imageView;
    @Inject TransitionsFramework transitionsFramework;
    @Inject ImageLoader imageLoader;
    @Inject DisplayImageOptions displayImageOptions;
    //@Inject ApiLevelChecker apiLevelChecker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podcast);
        ButterKnife.bind(SinglePodcastActivity.this);
        setUpActionBar();
        DaggerSinglePodcastComponent.builder().imageLoaderModule(new ImageLoaderModule(SinglePodcastActivity.this)).build().inject(SinglePodcastActivity.this);
        transitionsFramework.initDetailTransitions(SinglePodcastActivity.this);
        loadImage();
    }

    private void setUpActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
    }

    private void loadImage() {
        if (getIntent() == null || getIntent().getParcelableExtra(SINGLE_PODCAST) == null) {
            return;
        }
        PodcastSearchResult podcastSearchResult = getIntent().getParcelableExtra(SINGLE_PODCAST);
        if (podcastSearchResult.getArtworkUrl600() == null) {
            return;
        }
        imageLoader.displayImage(podcastSearchResult.getArtworkUrl600(), imageView, displayImageOptions, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

            }
        });
    }
}
