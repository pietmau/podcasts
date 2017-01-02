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

public class ROMEPodcastEpisode extends BaseObservable implements PodcastEpisode  {
    private final SyndEntry entry;
    private final EntryInformation itunes;
    private final MediaEntryModule mediaRSS;
    private final CrashlyticsWrapper crashlyticsWrapper;
    private final Context context;

    public ROMEPodcastEpisode(SyndEntry entry, CrashlyticsWrapper crashlyticsWrapper, Context context) {
        this.entry = entry;
        this.crashlyticsWrapper = crashlyticsWrapper;
        itunes = (EntryInformation) entry.getModule(Constants.ITUNES_URI);
        mediaRSS = (MediaEntryModule) entry.getModule(MediaModule.URI);
        this.context = context;
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
        return Html.fromHtml(entry.getDescription().getValue()).toString();
    }

    private String formatDate(Date publishedDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d MMM yyyy");
        return simpleDateFormat.format(publishedDate);
    }

    public Drawable getMediaTypeImage() {
        try {
            String type = entry.getEnclosures().get(0).getType().toLowerCase();
            return getImageResouce(type);
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            crashlyticsWrapper.logException(e);
            return null;
        }
    }

    public String getMediaTypeText() {
        try {
            String type = entry.getEnclosures().get(0).getType().toLowerCase();
            return getStrinResource(type);
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            crashlyticsWrapper.logException(e);
            return null;
        }
    }

    private String getStrinResource(String type) {
        if (type.contains(Constants.AUDIO)) {
            return context.getString(R.string.audio);
        }
        if (type.contains(Constants.VIDEO)) {
            return context.getString(R.string.video);
        }
        return null;
    }

    private Drawable getImageResouce(String type) {
        if (type.contains(Constants.AUDIO)) {
            return ContextCompat.getDrawable(context, R.drawable.ic_audio_icon);
        }
        if (type.contains(Constants.VIDEO)) {
            return ContextCompat.getDrawable(context, R.drawable.ic_video_icon);
        }
        return null;
    }
}
