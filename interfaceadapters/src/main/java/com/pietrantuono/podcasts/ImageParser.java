package com.pietrantuono.podcasts;

import android.support.annotation.Nullable;

import com.pietrantuono.CrashlyticsWrapper;
import com.rometools.modules.itunes.EntryInformation;
import com.rometools.modules.mediarss.MediaEntryModule;

import java.net.URL;

import javax.inject.Inject;

public class ImageParser {
    private final CrashlyticsWrapper crashlyticsWrapper;

    @Inject
    public ImageParser(CrashlyticsWrapper crashlyticsWrapper) {
        this.crashlyticsWrapper = crashlyticsWrapper;
    }

    @Nullable
    public String parseImage(EntryInformation itunesEntryInformation, MediaEntryModule mediaEntryModule) {
        if (itunesEntryInformation == null && mediaEntryModule == null) {
            return null;
        }
        if (itunesEntryInformation != null && itunesEntryInformation.getImage() != null) {
            return itunesEntryInformation.getImage().toString();
        }
        try {
            return mediaEntryModule.getMetadata().getThumbnail()[0].getUrl().toString();
        } catch (NullPointerException | IndexOutOfBoundsException e) {
        }
        return null;
    }
}
