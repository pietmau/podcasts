package com.pietrantuono.podcasts.addpodcast.customviews;

import android.widget.ImageView;

import com.pietrantuono.podcasts.imageloader.SimpleImageLoader;

public class BindingAdapter {
    private static final SimpleImageLoader simpleImageLoader = new SimpleImageLoader();
    /**
     * This needs to be static https://developer.android.com/reference/android/databinding/BindingAdapter.html
     */
    @android.databinding.BindingAdapter({"bind:image"})
    public static void loadImage(ImageView view, String url) {
        simpleImageLoader.displayImage(url, view);
    }
}
