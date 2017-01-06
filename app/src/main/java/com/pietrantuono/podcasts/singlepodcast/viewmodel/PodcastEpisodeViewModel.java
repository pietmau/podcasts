package com.pietrantuono.podcasts.singlepodcast.viewmodel;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.pietrantuono.Constants;
import com.pietrantuono.podcasts.R;
import com.pietrantuono.podcasts.apis.PodcastEpisodeModel;
import com.pietrantuono.podcasts.imageloader.SimpleImageLoader;
import com.rometools.rome.feed.synd.SyndEnclosure;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PodcastEpisodeViewModel implements PodcastEpisodeModel {
    private final PodcastEpisodeModel podcastEpisodeModel;
    private final Context context;
    private static SimpleImageLoader simpleImageLoader;

    public PodcastEpisodeViewModel(PodcastEpisodeModel podcastEpisodeModel, Context context, SimpleImageLoader simpleImageLoader) {
        this.podcastEpisodeModel = podcastEpisodeModel;
        this.context = context;
        this.simpleImageLoader = simpleImageLoader;
    }

    @Override
    public String getDuration() {
        return podcastEpisodeModel.getDuration();
    }

    @Override
    public String getAuthor() {
        return podcastEpisodeModel.getAuthor();
    }

    @Override
    public boolean isExplicit() {
        return podcastEpisodeModel.isExplicit();
    }

    @Override
    public String getImageUrl() {
        return podcastEpisodeModel.getImageUrl();
    }

    public String getImageUrlAsString() {
        if (getImageUrl() != null) {
            return getImageUrl().toString();
        } else {
            return null;
        }
    }

    @Override
    public List<String> getKeywords() {
        return podcastEpisodeModel.getKeywords();
    }

    @Override
    public String getSubtitle() {
        return podcastEpisodeModel.getSubtitle();
    }

    @Override
    public String getSummary() {
        return podcastEpisodeModel.getSummary();
    }

    @Override
    public Date getPubDate() {
        return podcastEpisodeModel.getPubDate();
    }

    @Override
    public String getTitle() {
        return podcastEpisodeModel.getTitle();
    }

    @Override
    public String getDescription() {
        return podcastEpisodeModel.getDescription();
    }

    @Override
    public List<SyndEnclosure> getEnclosures() {
        return podcastEpisodeModel.getEnclosures();
    }

    public String getDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM d yyyy");
        if (getPubDate() != null) {
            return simpleDateFormat.format(getPubDate());
        }
        return null;
    }

    public Drawable getMediaTypeImage() {
        try {
            String type = getEnclosures().get(0).getType().toLowerCase();
            return getImageResouce(type);
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            return null;
        }
    }

    private Drawable getImageResouce(String type) {
        if (type.contains(Constants.AUDIO)) {
            return ContextCompat.getDrawable(context, R.drawable.ic_audio_icon);
        }
        if (type.contains(Constants.VIDEO)) {
            return ContextCompat.getDrawable(context, R.drawable.ic_video_icon);
        }
        return null;
    }

    public String getMediaTypeText() {
        try {
            String type = getEnclosures().get(0).getType().toLowerCase();
            return getStrinResource(type);
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            return null;
        }
    }

    private String getStrinResource(String type) {
        if (type.contains(Constants.AUDIO)) {
            return context.getString(R.string.audio);
        }
        if (type.contains(Constants.VIDEO)) {
            return context.getString(R.string.video);
        }
        return null;
    }

    @BindingAdapter({"bind:image"})
    public static void loadImage(ImageView view, String url) {
        simpleImageLoader.displayImage(url, view);
    }
}
