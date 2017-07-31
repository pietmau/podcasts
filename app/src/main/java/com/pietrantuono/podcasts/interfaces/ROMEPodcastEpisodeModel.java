package com.pietrantuono.podcasts.interfaces;


import com.pietrantuono.podcasts.addpodcast.singlepodcast.model.SimpleEnclosure;
import com.pietrantuono.podcasts.apis.PodcastEpisodeModel;
import com.rometools.rome.feed.synd.SyndEnclosure;

import java.util.Date;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

public class ROMEPodcastEpisodeModel extends RealmObject implements PodcastEpisodeModel {
    private final String duration;
    private final String author;
    private final boolean isExplicit;
    private final String imageUrl;
    private final List<String> keywords;
    private final String subtitle;
    private final String summary;
    private final Date pubDate;
    private final String title;
    private final String description;
    private final RealmList<SimpleEnclosure> syndEnclosures;

    public ROMEPodcastEpisodeModel(String duration, String author, boolean isExplicit, String imageUrl, List<String> keywords, String subtitle, String summary, Date pubDate, String title, String description, List<SyndEnclosure> syndEnclosures) {
        this.duration = duration;
        this.author = author;
        this.isExplicit = isExplicit;
        this.imageUrl = imageUrl;
        this.keywords = keywords;
        this.subtitle = subtitle;
        this.summary = summary;
        this.pubDate = pubDate;
        this.title = title;
        this.description = description;
        this.syndEnclosures = parseEnclosures(syndEnclosures);
    }

    private RealmList<SimpleEnclosure> parseEnclosures(List<SyndEnclosure> syndEnclosures) {
        RealmList<SimpleEnclosure> result = new RealmList<>();
        for (SyndEnclosure enc : syndEnclosures) {
            result.add(new SimpleEnclosure(enc));
        }
        return result;
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
        return keywords;
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
