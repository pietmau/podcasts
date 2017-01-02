package com.pietrantuono.podcasts;


import android.databinding.BaseObservable;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.pietrantuono.Constants;
import com.pietrantuono.CrashlyticsWrapper;
import com.pietrantuono.interfaceadapters.R;
import com.pietrantuono.podcasts.apis.PodcastEpisode;
import com.rometools.modules.itunes.EntryInformation;
import com.rometools.modules.mediarss.MediaModule;
import com.rometools.rome.feed.synd.SyndEntry;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

public class ROMEPodcastEpisode extends BaseObservable implements PodcastEpisode  {
    private final SyndEntry entry;
    private final EntryInformation itunes;
    private final EntryInformation mediaRSS;
    @Inject CrashlyticsWrapper crashlyticsWrapper;

    public ROMEPodcastEpisode(SyndEntry entry) {
        this.entry = entry;
        itunes = (EntryInformation) entry.getModule(Constants.ITUNES_URI);
        mediaRSS = (EntryInformation) entry.getModule(MediaModule.URI);
    }

    @Override
    public String getDuration() {
        if (itunes == null || itunes.getDuration() == null) {
            return null;
        }
        return itunes.getDuration().toString();
    }

    @Override
    public String getAuthor() {
        if (itunes == null) {
            return null;
        }
        return itunes.getAuthor();
    }

    @Override
    public boolean explicit() {
        if (itunes == null) {
            return false;
        }
        return itunes.getExplicit();
    }

    @Override
    public String getImage() {
        if (itunes == null || itunes.getImage() == null) {
            return null;
        }
        return itunes.getImage().toString();
    }

    @Override
    public List<String> getKeywords() {
        if (itunes == null || itunes.getKeywords() == null) {
            return null;
        }
        return Arrays.asList(itunes.getKeywords());
    }

    @Override
    public String getSubtitle() {
        if (itunes == null) {
            return null;
        }
        return itunes.getSubtitle();
    }

    @Override
    public String getSummary() {
        if (itunes == null) {
            return null;
        }
        return itunes.getSubtitle();
    }

    @Override
    public String getPubDate() {
        if (entry == null || entry.getPublishedDate() == null) {
            return null;
        }
        return formatDate(entry.getPublishedDate());
    }

    @Override
    public String getTitle() {
        if (entry == null) {
            return null;
        }
        return entry.getTitle();
    }

    @Override
    public String getDescription() {
        if (entry == null || entry.getDescription() == null) {
            return null;
        }
        return entry.getDescription().getValue();
    }

    private String formatDate(Date publishedDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d MMM yyyy");
        return simpleDateFormat.format(publishedDate);
    }

    @Override
    @DrawableRes
    public int getMediaTypeImage() {
        try {
            String type = entry.getEnclosures().get(0).getType().toLowerCase();
            return getImageResouce(type);
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            crashlyticsWrapper.logException(e);
            return -1;
        }
    }

    @Override
    @StringRes
    public int getMediaTypeText() {
        try {
            String type = entry.getEnclosures().get(0).getType().toLowerCase();
            return getStrinResource(type);
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            crashlyticsWrapper.logException(e);
            return -1;
        }
    }

    private int getStrinResource(String type) {
        if (type.contains(Constants.AUDIO)) {
            return R.string.audio;
        }
        if (type.contains(Constants.VIDEO)) {
            return R.string.video;
        }
        return -1;
    }

    private int getImageResouce(String type) {
        if (type.contains(Constants.AUDIO)) {
            return R.drawable.ic_audio_icon;
        }
        if (type.contains(Constants.VIDEO)) {
            return R.drawable.ic_video_icon;
        }
        return -1;
    }
}
