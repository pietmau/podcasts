package com.pietrantuono.podcasts.singlepodcast.viewmodel;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.pietrantuono.Constants;
import com.pietrantuono.podcasts.R;
import com.pietrantuono.podcasts.apis.PodcastEpisodeModel;
import com.pietrantuono.podcasts.imageloader.SimpleImageLoader;

import java.text.SimpleDateFormat;

public class PodcastEpisodeViewModel extends BasePodcastEpisodeViewModel {
    private static SimpleImageLoader simpleImageLoader;
    private final ResourcesProvider resourcesProvider;

    public PodcastEpisodeViewModel(PodcastEpisodeModel podcastEpisodeModel, SimpleImageLoader simpleImageLoader, ResourcesProvider resourcesProvider) {
        super(podcastEpisodeModel);
        this.simpleImageLoader = simpleImageLoader;
        this.resourcesProvider = resourcesProvider;
    }

    /**
     * This needs to be static https://developer.android.com/reference/android/databinding/BindingAdapter.html
     */
    @BindingAdapter({"bind:image"})
    public static void loadImage(ImageView view, String url) {
        simpleImageLoader.displayImage(url, view);
    }

    @Nullable
    public Drawable getMediaTypeImage() {
        try {
            String type = getEnclosures().get(0).getType().toLowerCase();
            return getImageResouce(type);
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            return null;
        }
    }

    @Nullable
    private Drawable getImageResouce(String type) {
        if (type.contains(Constants.AUDIO)) {
            return resourcesProvider.ContextCompatgetDrawable(R.drawable.ic_audio_icon);
        }
        if (type.contains(Constants.VIDEO)) {
            resourcesProvider.ContextCompatgetDrawable(R.drawable.ic_video_icon);
        }
        return null;
    }

    @Nullable
    public String getMediaTypeText() {
        try {
            String type = getEnclosures().get(0).getType().toLowerCase();
            return getStringResource(type);
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            return null;
        }
    }

    @Nullable
    private String getStringResource(String type) {
        if (type.contains(Constants.AUDIO)) {
            return resourcesProvider.getString(R.string.audio);
        }
        if (type.contains(Constants.VIDEO)) {
            return resourcesProvider.getString(R.string.video);
        }
        return null;
    }
}
