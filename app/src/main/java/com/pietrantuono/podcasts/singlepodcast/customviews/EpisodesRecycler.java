package com.pietrantuono.podcasts.singlepodcast.customviews;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.pietrantuono.podcasts.addpodcast.customviews.PodcastsAdapter;
import com.pietrantuono.podcasts.addpodcast.customviews.PodcastsRecycler;
import com.pietrantuono.podcasts.apis.PodcastEpisode;
import com.pietrantuono.podcasts.main.dagger.DaggerImageLoaderComponent;
import com.pietrantuono.podcasts.main.dagger.ImageLoaderModule;

import java.util.List;

import javax.inject.Inject;

public class EpisodesRecycler extends RecyclerView {
    @Inject  EpisodesAdapter adapter;

    public EpisodesRecycler(Context context) {
        super(context);
        init();
    }

    public EpisodesRecycler(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EpisodesRecycler(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void setItems(List<PodcastEpisode> episodes){
        adapter.setItems(episodes);
    }

    private void init() {
        DaggerImageLoaderComponent.builder().imageLoaderModule(new ImageLoaderModule(getContext())).build().inject(EpisodesRecycler.this);
        setLayoutManager(createLayoutManager());
        setAdapter(adapter);
    }

    private LayoutManager createLayoutManager() {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return new GridLayoutManager(getContext(), 2);
        } else {
            return new LinearLayoutManager(getContext());
        }
    }

}