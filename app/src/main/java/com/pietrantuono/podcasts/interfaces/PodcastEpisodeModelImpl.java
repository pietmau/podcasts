package com.pietrantuono.podcasts.interfaces;


import com.pietrantuono.podcasts.addpodcast.singlepodcast.model.SimpleEnclosure;
import com.pietrantuono.podcasts.apis.PodcastEpisodeModel;
import com.pietrantuono.podcasts.providers.RealmString;
import com.rometools.rome.feed.synd.SyndEnclosure;

import java.util.Date;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

public class PodcastEpisodeModelImpl extends RealmObject implements PodcastEpisodeModel {
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

    public PodcastEpisodeModelImpl(String duration, String author, boolean isExplicit, String imageUrl, List<String> keywords, String subtitle, String summary, Date pubDate, String title, String description, List<SyndEnclosure> syndEnclosures) {
        this.duration = duration;
        this.author = author;
        this.isExplicit = isExplicit;
        this.imageUrl = imageUrl;
        this.keywords = parseKeywords(keywords);
        this.subtitle = subtitle;
        this.summary = summary;
        this.pubDate = pubDate;
        this.title = title;
        this.description = description;
        this.syndEnclosures = parseEnclosures(syndEnclosures);
    }

    private RealmList<RealmString> parseKeywords(List<String> keywords) {
        RealmList<RealmString> realmStrings = new RealmList<>();
        for (String keyword : keywords) {
            realmStrings.add(new RealmString(keyword));
        }
        return realmStrings;
    }

    public PodcastEpisodeModelImpl() {
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
        throw new UnsupportedOperationException("Not implemented");
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
