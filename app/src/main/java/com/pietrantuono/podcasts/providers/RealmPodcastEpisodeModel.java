package com.pietrantuono.podcasts.providers;

import com.pietrantuono.podcasts.apis.PodcastEpisodeModel;
import com.pietrantuono.podcasts.singlepodcast.model.SimpleEnclosure;
import com.rometools.rome.feed.synd.SyndEnclosure;

import java.util.Date;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

public class RealmPodcastEpisodeModel extends RealmObject implements PodcastEpisodeModel {
    private String duration;
    private String author;
    private boolean isExplicit;
    private String imageUrl;
    private RealmList<RealmString> keywords;
    private String subtitle;
    private String summary;
    private Date pubDate;
    private String title;
    private String description;
    private RealmList<SimpleEnclosure> syndEnclosures;

    public RealmPodcastEpisodeModel() {
    }

    @Override
    public String getDuration() {
        return duration;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public boolean isExplicit() {
        return isExplicit;
    }

    @Override
    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public List<String> getKeywords() {
        return RealmUtlis.toStringList(keywords);
    }

    @Override
    public String getSubtitle() {
        return subtitle;
    }

    @Override
    public String getSummary() {
        return summary;
    }

    @Override
    public Date getPubDate() {
        return pubDate;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public List<? extends SyndEnclosure> getEnclosures() {
        return syndEnclosures;
    }
}
