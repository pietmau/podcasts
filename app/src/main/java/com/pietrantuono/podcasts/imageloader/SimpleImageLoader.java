package com.pietrantuono.podcasts.imageloader;


import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pietrantuono.podcasts.R;
import com.pietrantuono.podcasts.singlepodcast.view.PodcastImageLoadingListener;

public class SimpleImageLoader {
    private static DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.drawable.podcast_grey_icon_very_big)
            .showImageOnFail(R.drawable.podcast_grey_icon_very_big)
            .cacheOnDisk(true)
            .build();

    public void displayImage(String url, ImageView imageView, PodcastImageLoadingListener podcastImageLoadingListener) {
        ImageLoader.getInstance().displayImage(url, imageView, options, podcastImageLoadingListener);
    }

    public void displayImage(String url, ImageView imageView) {
        ImageLoader.getInstance().displayImage(url, imageView, options);
    }


    public void loadImage(String url) {
        ImageLoader.getInstance().loadImage(url, null);
    }
}
