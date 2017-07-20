package com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel;

import android.support.annotation.Nullable;

import com.pietrantuono.podcasts.apis.PodcastEpisodeModel;
import com.rometools.rome.feed.synd.SyndEnclosure;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BasePodcastEpisodeViewModel implements PodcastEpisodeModel {
    private final PodcastEpisodeModel podcastEpisodeModel;

    BasePodcastEpisodeViewModel(PodcastEpisodeModel podcastEpisodeModel) {
        this.podcastEpisodeModel = podcastEpisodeModel;
    }
    @Nullable
    @Override
    public String getDuration() {
        return podcastEpisodeModel.getDuration();
    }

    @Nullable
    @Override
    public String getAuthor() {
        return podcastEpisodeModel.getAuthor();
    }


    @Override
    public boolean isExplicit() {
        return podcastEpisodeModel.isExplicit();
    }

    @Nullable
    @Override
    public String getImageUrl() {
        return podcastEpisodeModel.getImageUrl();
    }

    @Nullable
    @Override
    public List<String> getKeywords() {
        return podcastEpisodeModel.getKeywords();
    }

    @Nullable
    @Override
    public String getSubtitle() {
        return podcastEpisodeModel.getSubtitle();
    }

    @Nullable
    @Override
    public String getSummary() {
        return podcastEpisodeModel.getSummary();
    }

    @Nullable
    @Override
    public Date getPubDate() {
        return podcastEpisodeModel.getPubDate();
    }

    @Nullable
    @Override
    public String getTitle() {
        return podcastEpisodeModel.getTitle();
    }

    @Nullable
    @Override
    public String getDescription() {
        return podcastEpisodeModel.getDescription();
    }

    @Nullable
    @Override
    public List<? extends SyndEnclosure> getEnclosures() {
        return podcastEpisodeModel.getEnclosures();
    }

    @Nullable
    public String getDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM d yyyy");
        if (getPubDate() != null) {
            return simpleDateFormat.format(getPubDate());
        }
        return null;
    }
}
