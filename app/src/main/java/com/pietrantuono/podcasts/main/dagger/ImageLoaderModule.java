package com.pietrantuono.podcasts.main.dagger;

import android.content.Context;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.pietrantuono.podcasts.addpodcast.customviews.PodcastsAdapter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ImageLoaderModule {
    private DisplayImageOptions options;

    public ImageLoaderModule(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).build();
        ImageLoader.getInstance().init(config);
        initOptions();
    }

    @Provides
    @Singleton
    ImageLoader provideImageLoader() {
        return ImageLoader.getInstance();
    }

    @Provides
    PodcastsAdapter providePodcastsAdapter(ImageLoader imageLoader) {
        return new PodcastsAdapter(imageLoader);
    }

    @Provides
    DisplayImageOptions provideDisplayImageOptions() {
        return options;
    }

    private void initOptions() {
        options = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .build();
    }
}
