package com.pietrantuono.podcasts.addpodcast.customviews;

import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;

import com.pietrantuono.podcasts.R;
import com.pietrantuono.podcasts.addpodcast.model.pojos.PodcastSearchResult;

class SimplePopUpMenu extends PopupMenu {
    private final PodcastSearchResult podcastSearchResult;
    private final Listener listener;

    public SimplePopUpMenu(@NonNull View anchor, final PodcastSearchResult podcastSearchResult , final Listener listener) {
        super(anchor.getContext(), anchor);
        this.podcastSearchResult = podcastSearchResult;
        this.listener = listener;
        inflate(R.menu.overflow);
        setOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                listener.onSunscribeClicked(podcastSearchResult);
                return false;
            }
        });
    }

    public interface Listener {
        void onSunscribeClicked(PodcastSearchResult podcastSearchResult);
    }
}
