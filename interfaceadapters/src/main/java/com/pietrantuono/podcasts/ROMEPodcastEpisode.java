package com.pietrantuono.podcasts;


import android.content.Context;
import android.databinding.BaseObservable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.text.Html;

import com.pietrantuono.Constants;
import com.pietrantuono.CrashlyticsWrapper;
import com.pietrantuono.interfaceadapters.R;
import com.pietrantuono.podcasts.apis.PodcastEpisode;
import com.rometools.modules.itunes.EntryInformation;
import com.rometools.modules.mediarss.MediaEntryModule;
import com.rometools.modules.mediarss.MediaModule;
import com.rometools.rome.feed.synd.SyndEntry;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

public class ROMEPodcastEpisode implements PodcastEpisode {
    private final SyndEntry entry;
    private final EntryInformation itunes;
    private final MediaEntryModule mediaRSS;
    private final CrashlyticsWrapper crashlyticsWrapper;
    private final String duration;
    private final String author;
    private final boolean isExplicit;
    private final String image;
    private final List<String> keywords;
    private final String subtitle;
    private final String summary;
    private final Date pubDate;
    private final String title;
    private final String description;

    public ROMEPodcastEpisode(SyndEntry entry, CrashlyticsWrapper crashlyticsWrapper, Context context) {
        this.entry = entry;
        this.crashlyticsWrapper = crashlyticsWrapper;
        itunes = (EntryInformation) entry.getModule(Constants.ITUNES_URI);
        mediaRSS = (MediaEntryModule) entry.getModule(MediaModule.URI);

        if (itunes != null && itunes.getDuration() != null) {
            duration = itunes.getDuration().toString();
        } else {
            duration = null;
        }
        if (itunes != null) {
            author = itunes.getAuthor();
        } else {
            author = null;
        }
        if (itunes != null) {
            isExplicit = itunes.getExplicit();
        } else {
            isExplicit = false;
        }
        if (itunes != null || itunes.getImage() != null) {
            image = itunes.getImage().toString();
        } else {
            image = null;
        }
        if (itunes == null || itunes.getKeywords() == null) {
            keywords = null;
        } else {
            keywords = Arrays.asList(itunes.getKeywords());
        }
        if (itunes == null) {
            subtitle = null;
        } else {
            subtitle = itunes.getSubtitle();
        }
        if (itunes == null) {
            summary = null;
        } else {
            summary = itunes.getSubtitle();
        }
        if (entry == null) {
            pubDate = null;
        } else {
            pubDate = entry.getPublishedDate();
        }
        if (entry == null) {
            title = null;
        } else {
            title = entry.getTitle();
        }
        if (entry == null || entry.getDescription() == null) {
            description = null;
        } else {
            description = entry.getDescription().getValue();
        }
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
    public boolean explicit() {
        return isExplicit;
    }

    @Override
    public String getImage() {
        return image;
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
}
