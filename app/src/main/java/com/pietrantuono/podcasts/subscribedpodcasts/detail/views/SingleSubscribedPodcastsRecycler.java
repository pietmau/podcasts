package com.pietrantuono.podcasts.subscribedpodcasts.detail.views;


import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.pietrantuono.podcasts.apis.PodcastEpisode;
import com.pietrantuono.podcasts.application.App;
import com.pietrantuono.podcasts.subscribedpodcasts.detail.di.SingleSubscribedModule;

import java.util.List;

import javax.inject.Inject;

public class SingleSubscribedPodcastsRecycler extends RecyclerView {
    @Inject SingleSubscribedPodcastsAdapter adapter;

    public SingleSubscribedPodcastsRecycler(Context context) {
        super(context);
        init();
    }

    public SingleSubscribedPodcastsRecycler(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SingleSubscribedPodcastsRecycler(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void setItems(List<PodcastEpisode> episodes) {
        adapter.setItems(episodes);
    }

    private void init() {
        ((App) getContext().getApplicationContext()).getApplicationComponent()
                .with(new SingleSubscribedModule((AppCompatActivity) getContext()))
                .inject(this);
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
