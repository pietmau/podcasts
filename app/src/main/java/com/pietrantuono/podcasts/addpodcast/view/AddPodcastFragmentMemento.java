package com.pietrantuono.podcasts.addpodcast.view;

import android.os.Bundle;

public class AddPodcastFragmentMemento {
    private static final java.lang.String IS_PROGRESS_SHOWING = "progress_showing";
    private final Bundle bundle;
    private boolean isProgressShowing;

    public AddPodcastFragmentMemento(Bundle bundle) {
        this.bundle = bundle;
        if (bundle == null) {
            return;
        }
        isProgressShowing = bundle.getBoolean(IS_PROGRESS_SHOWING);
    }

    public void setProgressShowing(boolean progressShowing) {
        bundle.putBoolean(IS_PROGRESS_SHOWING, progressShowing);
    }

    public boolean isProgressShowing() {
        return isProgressShowing;
    }
}
