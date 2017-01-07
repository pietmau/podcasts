package com.pietrantuono.podcasts;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.pietrantuono.Constants;
import com.pietrantuono.podcasts.apis.PodcastEpisodeModel;
import com.rometools.modules.itunes.EntryInformation;
import com.rometools.modules.mediarss.MediaEntryModule;
import com.rometools.modules.mediarss.MediaModule;
import com.rometools.rome.feed.synd.SyndEnclosure;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class PodcastEpisodeParser {
    private final ImageParser imageParser;

    public PodcastEpisodeParser(ImageParser imageParser) {
        this.imageParser = imageParser;
    }

    @NonNull
    public List<PodcastEpisodeModel> parseFeed(SyndFeed feed) {
        List<PodcastEpisodeModel> episodes = new ArrayList<>();
        for (SyndEntry entry : feed.getEntries()) {
            episodes.add(parseEntry(entry));
        }
        return episodes;
    }

    @NonNull
    private PodcastEpisodeModel parseEntry(SyndEntry syndEntry) {
        EntryInformation entryInformation = (EntryInformation) syndEntry.getModule(Constants.ITUNES_URI);
        MediaEntryModule mediaEntryModule = (MediaEntryModule) syndEntry.getModule(MediaModule.URI);
        ROMEPodcastEpisodeBuilder romePodcastEpisodeBuilder = getRomePodcastEpisodeBuilder(syndEntry, entryInformation, mediaEntryModule);
        return romePodcastEpisodeBuilder.createROMEPodcastEpisode();
    }

    @NonNull
    private ROMEPodcastEpisodeBuilder getRomePodcastEpisodeBuilder(SyndEntry syndEntry, EntryInformation itunesEntryInformation, MediaEntryModule mediaEntryModule) {
        ROMEPodcastEpisodeBuilder romePodcastEpisodeBuilder = new ROMEPodcastEpisodeBuilder();
        romePodcastEpisodeBuilder = parseItunesEntryInformation(itunesEntryInformation, romePodcastEpisodeBuilder);
        romePodcastEpisodeBuilder = parseSyndEntry(syndEntry, romePodcastEpisodeBuilder);
        romePodcastEpisodeBuilder = parseImage(itunesEntryInformation, mediaEntryModule, romePodcastEpisodeBuilder);
        return romePodcastEpisodeBuilder;
    }

    @NonNull
    private ROMEPodcastEpisodeBuilder parseSyndEntry(SyndEntry syndEntry, ROMEPodcastEpisodeBuilder romePodcastEpisodeBuilder) {
        if (syndEntry == null) {
            return romePodcastEpisodeBuilder;
        }
        romePodcastEpisodeBuilder.setPubDate(parsePubDate(syndEntry));
        romePodcastEpisodeBuilder.setDescription(parseDescription(syndEntry));
        romePodcastEpisodeBuilder.setTitle(parseTitle(syndEntry));
        romePodcastEpisodeBuilder.setEnclosures(parseEnclosures(syndEntry));
        return romePodcastEpisodeBuilder;
    }

    @NonNull
    private ROMEPodcastEpisodeBuilder parseItunesEntryInformation(EntryInformation itunesEntryInformation, ROMEPodcastEpisodeBuilder romePodcastEpisodeBuilder) {
        if (itunesEntryInformation == null) {
            return romePodcastEpisodeBuilder;
        }
        romePodcastEpisodeBuilder.setAuthor(parseAuthor(itunesEntryInformation));
        romePodcastEpisodeBuilder.setDuration(parseDuration(itunesEntryInformation));
        romePodcastEpisodeBuilder.setIsExplicit(parseExplicit(itunesEntryInformation));
        romePodcastEpisodeBuilder.setKeywords(parseKeywords(itunesEntryInformation));
        romePodcastEpisodeBuilder.setSubtitle(parseSubtitle(itunesEntryInformation));
        romePodcastEpisodeBuilder.setSummary(parseSummary(itunesEntryInformation));
        return romePodcastEpisodeBuilder;
    }

    @Nullable
    private List<SyndEnclosure> parseEnclosures(SyndEntry entry) {
        if (entry.getEnclosures() == null) {
            return null;
        } else {
            return entry.getEnclosures();
        }
    }

    @Nullable
    private String parseDescription(SyndEntry entry) {
        if (entry.getDescription() == null) {
            return null;
        } else {
            return entry.getDescription().getValue();
        }
    }

    @Nullable
    private String parseTitle(SyndEntry entry) {
        return entry.getTitle();
    }

    @Nullable
    private Date parsePubDate(SyndEntry entry) {
        return entry.getPublishedDate();
    }

    @Nullable
    private String parseSummary(EntryInformation itunesEntryInformation) {
        return itunesEntryInformation.getSummary();
    }

    @Nullable
    private String parseSubtitle(EntryInformation itunesEntryInformation) {
        return itunesEntryInformation.getSubtitle();
    }

    @Nullable
    private List<String> parseKeywords(EntryInformation itunesEntryInformation) {
        return Arrays.asList(itunesEntryInformation.getKeywords());
    }

    private boolean parseExplicit(EntryInformation itunesEntryInformation) {
        return itunesEntryInformation.getExplicit();
    }

    @Nullable
    private String parseAuthor(EntryInformation itunesEntryInformation) {
        return itunesEntryInformation.getAuthor();
    }

    @Nullable
    private String parseDuration(EntryInformation itunesEntryInformation) {
        if (itunesEntryInformation.getDuration() != null) {
            return itunesEntryInformation.getDuration().toString();
        } else {
            return null;
        }
    }

    private ROMEPodcastEpisodeBuilder parseImage(EntryInformation itunesEntryInformation, MediaEntryModule mediaEntryModule, ROMEPodcastEpisodeBuilder romePodcastEpisodeBuilder) {
        romePodcastEpisodeBuilder.setImageUrl(imageParser.parseImage(itunesEntryInformation, mediaEntryModule));
        return romePodcastEpisodeBuilder;
    }
}
