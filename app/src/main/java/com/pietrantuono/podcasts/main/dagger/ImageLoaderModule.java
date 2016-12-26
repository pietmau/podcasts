package com.pietrantuono.podcasts.main.dagger;

import android.content.Context;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.pietrantuono.podcasts.R;
import com.pietrantuono.podcasts.addpodcast.customviews.PodcastsAdapter;
import com.pietrantuono.podcasts.imageloader.SimpleImageLoader;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ImageLoaderModule {

    public ImageLoaderModule(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).build();
        ImageLoader.getInstance().init(config);
    }

    @Provides
    @Singleton
    ImageLoader provideImageLoader() {
        return ImageLoader.getInstance();
    }

    @Provides
    PodcastsAdapter providePodcastsAdapter(SimpleImageLoader imageLoader) {
        return new PodcastsAdapter(imageLoader);
    }

    @Provides
    SimpleImageLoader provideSimpleImageLoader() {
        return new SimpleImageLoader();
    }
}
