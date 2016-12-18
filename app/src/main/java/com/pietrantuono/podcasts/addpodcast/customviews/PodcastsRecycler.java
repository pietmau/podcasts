package com.pietrantuono.podcasts.addpodcast.customviews;


import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

import com.pietrantuono.podcasts.addpodcast.model.pojos.PodcastSearchResult;
import com.pietrantuono.podcasts.main.dagger.DaggerImageLoaderComponent;
import com.pietrantuono.podcasts.main.dagger.ImageLoaderModule;

import java.util.List;

import javax.inject.Inject;

public class PodcastsRecycler extends RecyclerView {
    @Inject PodcastsAdapter adapter;

    public PodcastsRecycler(Context context) {
        super(context);
        init();
    }

    public PodcastsRecycler(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PodcastsRecycler(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        DaggerImageLoaderComponent.builder().imageLoaderModule(new ImageLoaderModule(getContext())).build().inject(PodcastsRecycler.this);
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

    public void setItems(List<PodcastSearchResult> items) {
        adapter.setItems(items);
    }

    public void onQueryTextChange(String newText) {
        adapter.onQueryTextChange(newText);
    }
}