package com.pietrantuono.podcasts.addpodcast.customviews;


import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast;
import com.pietrantuono.podcasts.application.App;

import java.util.List;

import javax.inject.Inject;

public class PodcastsRecycler extends RecyclerView {
    @Inject PodcastsAdapter adapter;

    public PodcastsRecycler(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        ((App)getContext().getApplicationContext()).getApplicationComponent().inject(this);
        setLayoutManager(createLayoutManager());
        setAdapter(adapter);
    }

    private LayoutManager createLayoutManager() {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return new GridLayoutManager(getContext(), 4);
        } else {
            return new GridLayoutManager(getContext(), 3);
        }
    }

    public void setItems(List<Podcast> items) {
        adapter.setItems(items);
    }

    private void setOnItemClickListener(PodcastsAdapter.OnItemClickedClickedListener onItemClickedClickedListener) {
        adapter.setOnItemClickListener(onItemClickedClickedListener);
    }

    public void setListeners(PodcastsAdapter.OnItemClickedClickedListener addPodcastPresenter) {
        setOnItemClickListener(addPodcastPresenter);
    }

    public boolean isPartiallyHidden(int position) {
        return ((LinearLayoutManager) getLayoutManager()).findLastCompletelyVisibleItemPosition() < position;
    }
}
