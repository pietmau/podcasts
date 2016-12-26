package com.pietrantuono.podcasts.singlepodcast;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
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
    @Inject ApiLevelChecker apiLevelChecker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podcast);
        ButterKnife.bind(SinglePodcastActivity.this);
        setUpActionBar();
        DaggerSinglePodcastComponent.builder().imageLoaderModule(new ImageLoaderModule(SinglePodcastActivity.this)).build().inject(SinglePodcastActivity.this);
        transitionsFramework.initDetailTransitionAndPostponeEnterTransition(SinglePodcastActivity.this);
        loadImage();
    }

    private void setUpActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
    }

    private void loadImage() {
        try {
            String url = ((PodcastSearchResult) getIntent().getParcelableExtra(SINGLE_PODCAST)).getArtworkUrl600();
            imageLoader.displayImage(url, imageView, displayImageOptions, new PodcastImageLoadingListener(SinglePodcastActivity.this, transitionsFramework));
        } catch (NullPointerException e) {
        }

    }
}
