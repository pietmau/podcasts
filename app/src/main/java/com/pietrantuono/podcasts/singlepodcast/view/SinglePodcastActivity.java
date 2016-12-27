package com.pietrantuono.podcasts.singlepodcast.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.pietrantuono.podcasts.PresenterManager;
import com.pietrantuono.podcasts.R;
import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast;
import com.pietrantuono.podcasts.imageloader.SimpleImageLoader;
import com.pietrantuono.podcasts.main.dagger.ImageLoaderModule;
import com.pietrantuono.podcasts.main.view.TransitionsFramework;
import com.pietrantuono.podcasts.singlepodcast.dagger.DaggerSinglePodcastComponent;
import com.pietrantuono.podcasts.singlepodcast.dagger.SinglePodcastModule;
import com.pietrantuono.podcasts.singlepodcast.presenter.SinglePodcastPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SinglePodcastActivity extends AppCompatActivity implements SinglePodcastView {
    public static final String SINGLE_PODCAST = "single_podcast";
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.image) ImageView imageView;
    @BindView(R.id.recycler) RecyclerView recyclerView;
    @Inject TransitionsFramework transitionsFramework;
    @Inject SimpleImageLoader imageLoader;
    @Inject SinglePodcastPresenter presenter;
    @Inject PresenterManager presenterManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podcast);
        ButterKnife.bind(SinglePodcastActivity.this);
        setUpActionBar();
        DaggerSinglePodcastComponent.builder().singlePodcastModule(new SinglePodcastModule(SinglePodcastActivity.this)).imageLoaderModule(new ImageLoaderModule(SinglePodcastActivity.this)).build().inject(SinglePodcastActivity.this);
        transitionsFramework.initDetailTransitionAndPostponeEnterTransition(SinglePodcastActivity.this);
        SinglePodcast podcast = getIntent().getParcelableExtra(SINGLE_PODCAST);
        loadImage(podcast);
        presenter.bindView(SinglePodcastActivity.this);
        presenter.setPodcast(podcast);
    }

    private void setUpActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
    }

    private void loadImage(SinglePodcast podcast) {
        imageLoader.displayImage(podcast, imageView, new PodcastImageLoadingListener(SinglePodcastActivity.this, transitionsFramework));
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return presenterManager;
    }
}
