package com.pietrantuono.podcasts.singlepodcast.viewmodel;


import android.view.View;

public enum VisibilityWrapper {
    VISIBLE(View.VISIBLE),
    GONE(View.GONE);

    private int visibility;

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
