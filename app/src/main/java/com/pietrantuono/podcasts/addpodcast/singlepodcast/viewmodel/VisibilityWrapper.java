package com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel;


import android.view.View;

public enum VisibilityWrapper {
    VISIBLE(View.VISIBLE),
    GONE(View.GONE);

    private final int visibility;

    VisibilityWrapper(int visibility) {
        this.visibility = visibility;
    }

    public int getVisibility() {
        return visibility;
    }
    
    public boolean isVisible(){
        return (visibility == View.VISIBLE);
    }
}
