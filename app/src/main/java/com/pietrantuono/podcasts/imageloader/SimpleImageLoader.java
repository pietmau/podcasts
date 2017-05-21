package com.pietrantuono.podcasts.imageloader;


import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.pietrantuono.podcasts.R;
import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast;
import com.pietrantuono.podcasts.singlepodcast.view.PodcastImageLoadingListener;

public class SimpleImageLoader {
    private static final DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.drawable.podcast_grey_icon_very_big)
            .showImageOnFail(R.drawable.podcast_grey_icon_very_big)
            .cacheInMemory(false)
            .cacheOnDisk(true)
            .build();

    public void displayImage(String url, ImageView imageView, ImageLoadingListener podcastImageLoadingListener) {
        ImageLoader.getInstance().displayImage(url, imageView, options, podcastImageLoadingListener);
    }

    public void displayImage(String url, ImageView imageView) {
        ImageLoader.getInstance().displayImage(url, imageView, options);
    }

    public void displayImage(SinglePodcast singlePodcast, ImageView imageView, PodcastImageLoadingListener podcastImageLoadingListener) {
        if (singlePodcast == null) {
            return;
        }
        String url = singlePodcast.getArtworkUrl600();
        ImageLoader.getInstance().displayImage(url, imageView, options, podcastImageLoadingListener);
    }

    public void loadImage(String url) {
        ImageLoader.getInstance().loadImage(url, null);
    }

    public void loadImage(String url, ImageLoadingListener listener) {
        ImageLoader.getInstance().loadImage(url, listener);
    }
}
