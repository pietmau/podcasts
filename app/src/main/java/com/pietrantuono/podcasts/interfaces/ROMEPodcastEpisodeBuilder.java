package com.pietrantuono.podcasts.interfaces;

import com.rometools.rome.feed.synd.SyndEnclosure;

import java.util.Date;
import java.util.List;

public class ROMEPodcastEpisodeBuilder {
    private String duration;
    private String author;
    private boolean isExplicit;
    private String imageUrl;
    private List<String> keywords;
    private String subtitle;
    private String summary;
    private Date pubDate;
    private String title;
    private String description;
    private List<SyndEnclosure> syndEnclosures;

    public ROMEPodcastEpisodeBuilder setDuration(String duration) {
        this.duration = duration;
        return this;
    }

    public ROMEPodcastEpisodeBuilder setAuthor(String author) {
        this.author = author;
        return this;
    }

    public ROMEPodcastEpisodeBuilder setIsExplicit(boolean isExplicit) {
        this.isExplicit = isExplicit;
        return this;
    }

    public ROMEPodcastEpisodeBuilder setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public ROMEPodcastEpisodeBuilder setKeywords(List<String> keywords) {
        this.keywords = keywords;
        return this;
    }

    public ROMEPodcastEpisodeBuilder setSubtitle(String subtitle) {
        this.subtitle = subtitle;
        return this;
    }

    public ROMEPodcastEpisodeBuilder setSummary(String summary) {
        this.summary = summary;
        return this;
    }

    public ROMEPodcastEpisodeBuilder setPubDate(Date pubDate) {
        this.pubDate = pubDate;
        return this;
    }

    public ROMEPodcastEpisodeBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public ROMEPodcastEpisodeBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public ROMEPodcastEpisodeBuilder setEnclosures(List<SyndEnclosure> syndEnclosures) {
        this.syndEnclosures = syndEnclosures;
        return this;
    }

    public PodcastEpisodeImpl createROMEPodcastEpisode() {
        return new PodcastEpisodeImpl(duration, author, isExplicit, imageUrl, keywords, subtitle, summary, pubDate, title, description, syndEnclosures);
    }

}