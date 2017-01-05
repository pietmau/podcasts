package com.pietrantuono.podcasts.singlepodcast.viewmodel;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;

import com.pietrantuono.Constants;
import com.pietrantuono.CrashlyticsWrapper;
import com.pietrantuono.podcasts.R;
import com.pietrantuono.podcasts.apis.PodcastEpisodeModel;
import com.rometools.rome.feed.synd.SyndEnclosure;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PodcastEpisodeViewModel implements PodcastEpisodeModel {
    private final PodcastEpisodeModel podcastEpisodeModel;
    private final Context context;

    public PodcastEpisodeViewModel(PodcastEpisodeModel podcastEpisodeModel, Context context) {
        this.podcastEpisodeModel = podcastEpisodeModel;
        this.context = context;
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
    public URL getImage() {
        return podcastEpisodeModel.getImage();
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

    public Drawable getMediaTypeImage(){
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
}
