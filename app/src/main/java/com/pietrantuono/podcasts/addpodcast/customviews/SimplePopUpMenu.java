package com.pietrantuono.podcasts.addpodcast.customviews;

import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;

import com.pietrantuono.podcasts.R;
import com.pietrantuono.podcasts.addpodcast.model.pojos.PodcastSearchResult;

class SimplePopUpMenu extends PopupMenu {

    public SimplePopUpMenu(@NonNull View anchor, final PodcastSearchResult podcastSearchResult , final PodcastsAdapter.OnSunscribeClickedListener onSunscribeClickedListener) {
        super(anchor.getContext(), anchor);
        inflate(R.menu.overflow);
        setOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                onSunscribeClickedListener.onSunscribeClicked(podcastSearchResult);
                return false;
            }
        });
    }

}