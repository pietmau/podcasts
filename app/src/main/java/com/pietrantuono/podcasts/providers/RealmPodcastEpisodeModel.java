package com.pietrantuono.podcasts.providers;

import com.pietrantuono.podcasts.addpodcast.singlepodcast.model.SimpleEnclosure;
import com.pietrantuono.podcasts.apis.PodcastEpisodeModel;
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

    RealmPodcastEpisodeModel(Builder builder) {
        duration = builder.duration;
        author = builder.author;
        isExplicit = builder.isExplicit;
        imageUrl = builder.imageUrl;
        RealmList<RealmString> keywords = builder.keywords;
        subtitle = builder.subtitle;
        summary = builder.summary;
        pubDate = builder.pubDate;
        title = builder.title;
        description = builder.description;
        RealmList<SimpleEnclosure> syndEnclosures = builder.syndEnclosures;
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


    public static class Builder {
        String duration;
        String author;
        boolean isExplicit;
        String imageUrl;
        RealmList<RealmString> keywords;
        String subtitle;
        String summary;
        Date pubDate;
        String title;
        String description;
        RealmList<SimpleEnclosure> syndEnclosures;

        public Builder() {
        }

        public Builder setDuration(String duration) {
            this.duration = duration;
            return this;
        }

        public Builder setAuthor(String author) {
            this.author = author;
            return this;
        }

        public Builder setExplicit(boolean explicit) {
            isExplicit = explicit;
            return this;
        }

        public Builder setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder setKeywords(List<String> keywords) {
            this.keywords = new RealmList<>();
            for (String string : keywords) {
                this.keywords.add(new RealmString(string));
            }
            return this;
        }

        public Builder setSubtitle(String subtitle) {
            this.subtitle = subtitle;
            return this;
        }

        public Builder setSummary(String summary) {
            this.summary = summary;
            return this;
        }

        public Builder setPubDate(Date pubDate) {
            this.pubDate = pubDate;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setSyndEnclosures(RealmList<SimpleEnclosure> syndEnclosures) {
            this.syndEnclosures = syndEnclosures;
            return this;
        }
    }
}