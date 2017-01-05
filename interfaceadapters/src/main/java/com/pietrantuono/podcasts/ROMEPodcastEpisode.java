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

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import retrofit2.http.Url;

public class ROMEPodcastEpisode implements PodcastEpisode {
    private final SyndEntry entry;
    private final EntryInformation itunes;
    private final MediaEntryModule mediaRSS;
    private final CrashlyticsWrapper crashlyticsWrapper;
    private final String duration;
    private final String author;
    private final boolean isExplicit;
    private final URL image;
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

        duration = parseDuration();
        author = parseAuthor();
        isExplicit = parseExplicit();
        image = parseImage();
        keywords = parseKeywords();
        subtitle = parseSubtitle();
        summary = parseSummary();
        pubDate = parsePubDate(entry);
        title = parseTitle(entry);
        description = parseDescription(entry);
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
