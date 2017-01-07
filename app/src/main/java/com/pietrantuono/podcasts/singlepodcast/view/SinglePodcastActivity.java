package com.pietrantuono.podcasts.singlepodcast.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.pietrantuono.podcasts.PresenterManager;
import com.pietrantuono.podcasts.R;
import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast;
import com.pietrantuono.podcasts.apis.PodcastEpisodeModel;
import com.pietrantuono.podcasts.imageloader.SimpleImageLoader;
import com.pietrantuono.podcasts.main.dagger.ImageLoaderModule;
import com.pietrantuono.podcasts.main.view.TransitionsFramework;
import com.pietrantuono.podcasts.singlepodcast.customviews.EpisodesRecycler;
import com.pietrantuono.podcasts.singlepodcast.dagger.DaggerSinglePodcastComponent;
import com.pietrantuono.podcasts.singlepodcast.dagger.SinglePodcastModule;
import com.pietrantuono.podcasts.singlepodcast.presenter.SinglePodcastPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SinglePodcastActivity extends AppCompatActivity implements SinglePodcastView {
    public static final String SINGLE_PODCAST = "single_podcast";
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.main_image) ImageView imageView;
    @BindView(R.id.recycler) EpisodesRecycler recyclerView;
    @Inject TransitionsFramework transitionsFramework;
    @Inject SimpleImageLoader imageLoader;
    @Inject SinglePodcastPresenter presenter;
    @Inject PresenterManager presenterManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        inject();
        startTransition();
        loadImage();
        initPresenter();
    }

    private void inject() {
        DaggerSinglePodcastComponent.builder().singlePodcastModule(new SinglePodcastModule(SinglePodcastActivity.this)).imageLoaderModule(new ImageLoaderModule(SinglePodcastActivity.this)).build().inject(SinglePodcastActivity.this);
    }

    private void startTransition() {
        transitionsFramework.initDetailTransitionAndPostponeEnterTransition(SinglePodcastActivity.this);
    }

    private void initViews() {
        setContentView(R.layout.activity_podcast);
        ButterKnife.bind(SinglePodcastActivity.this);
        setUpActionBar();
    }

    private void initPresenter() {
        presenter.bindView(SinglePodcastActivity.this);
        presenter.setPodcast(getIntent().getParcelableExtra(SINGLE_PODCAST));
    }

    private void setUpActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
    }

    private void loadImage() {
        SinglePodcast podcast = getIntent().getParcelableExtra(SINGLE_PODCAST);
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

    @Override
    public void setEpisodes(List<PodcastEpisodeModel> episodes) {
        recyclerView.setItems(episodes);
    }
}
