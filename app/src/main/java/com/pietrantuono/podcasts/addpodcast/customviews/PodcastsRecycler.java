package com.pietrantuono.podcasts.addpodcast.customviews;


import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

import com.pietrantuono.podcasts.addpodcast.model.pojos.PodcastSearchResult;

import java.util.List;

public class PodcastsRecycler extends RecyclerView {
    private PodcastsAdapter adapter;

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
        setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        adapter = new PodcastsAdapter();
        addItemDecoration(new PodcastDecoration(getContext()));
        setAdapter(adapter);
    }

    public void setItems(List<PodcastSearchResult> items) {
        adapter.setItems(items);
    }
}
