package com.pietrantuono.podcasts;

import com.pietrantuono.Constants;
import com.pietrantuono.podcasts.apis.PodcastEpisodeModel;
import com.rometools.modules.itunes.EntryInformation;
import com.rometools.rome.feed.synd.SyndEnclosure;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class PodcastEpisodeParser {
    private EntryInformation itunes;
    //private MediaEntryModule mediaRSS;

    private PodcastEpisodeModel parse(SyndEntry entry) {
        this.itunes = (EntryInformation) entry.getModule(Constants.ITUNES_URI);
        //this.mediaRSS = (MediaEntryModule) entry.getModule(MediaModule.URI);
        ROMEPodcastEpisodeBuilder romePodcastEpisodeBuilder = new ROMEPodcastEpisodeBuilder();
        romePodcastEpisodeBuilder.setAuthor(parseAuthor());
        romePodcastEpisodeBuilder.setDescription(parseDescription(entry));
        romePodcastEpisodeBuilder.setDuration(parseDuration());
        romePodcastEpisodeBuilder.setImage(parseImage());
        romePodcastEpisodeBuilder.setIsExplicit(parseExplicit());
        romePodcastEpisodeBuilder.setKeywords(parseKeywords());
        romePodcastEpisodeBuilder.setPubDate(parsePubDate(entry));
        romePodcastEpisodeBuilder.setSubtitle(parseSubtitle());
        romePodcastEpisodeBuilder.setSummary(parseSummary());
        romePodcastEpisodeBuilder.setTitle(parseTitle(entry));
        romePodcastEpisodeBuilder.setEnclosures(parseEnclosures(entry));
        return romePodcastEpisodeBuilder.createROMEPodcastEpisode();
    }

    public List<PodcastEpisodeModel> parse(SyndFeed feed) {
        List<PodcastEpisodeModel> episodes = new ArrayList<>();
        for (SyndEntry entry : feed.getEntries()) {
            episodes.add(parse(entry));
        }
        return episodes;
    }

    private List<SyndEnclosure> parseEnclosures(SyndEntry entry) {
        if (entry == null || entry.getEnclosures() == null) {
            return null;
        } else {
            return entry.getEnclosures();
        }
    }

    private String parseDescription(SyndEntry entry) {
        if (entry == null || entry.getDescription() == null) {
            return null;
        } else {
            return entry.getDescription().getValue();
        }
    }

    private String parseTitle(SyndEntry entry) {
        if (entry == null) {
            return null;
        } else {
            return entry.getTitle();
        }
    }

    private Date parsePubDate(SyndEntry entry) {
        if (entry == null) {
            return null;
        } else {
            return entry.getPublishedDate();
        }
    }

    private String parseSummary() {
        if (itunes == null) {
            return null;
        } else {
            return itunes.getSummary();
        }
    }

    private String parseSubtitle() {
        if (itunes == null) {
            return null;
        } else {
            return itunes.getSubtitle();
        }
    }

    private List<String> parseKeywords() {
        if (itunes == null || itunes.getKeywords() == null) {
            return null;
        } else {
            return Arrays.asList(itunes.getKeywords());
        }
    }

    private URL parseImage() {
        if (itunes != null || itunes.getImage() != null) {
            return itunes.getImage();
        } else {
            return null;
        }
    }

    private boolean parseExplicit() {
        if (itunes != null) {
            return itunes.getExplicit();
        } else {
            return false;
        }
    }

    private String parseAuthor() {
        if (itunes != null) {
            return itunes.getAuthor();
        } else {
            return null;
        }
    }

    private String parseDuration() {
        if (itunes != null && itunes.getDuration() != null) {
            return itunes.getDuration().toString();
        } else {
            return null;
        }
    }
}
