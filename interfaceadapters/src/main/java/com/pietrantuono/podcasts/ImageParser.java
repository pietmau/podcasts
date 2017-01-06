package com.pietrantuono.podcasts;

import android.support.annotation.Nullable;

import com.rometools.modules.itunes.EntryInformation;
import com.rometools.modules.mediarss.MediaEntryModule;

import java.net.URL;

import javax.inject.Inject;

public class ImageParser {

    @Inject
    public ImageParser() {
    }

    @Nullable
    public String parseImage(EntryInformation itunesEntryInformation, MediaEntryModule mediaEntryModule) {
        if (itunesEntryInformation == null && mediaEntryModule == null) {
            return null;
        }
        if (itunesEntryInformation.getImage() != null) {
            return itunesEntryInformation.getImage().toString();
        }
        try {
            return mediaEntryModule.getMetadata().getThumbnail()[0].getUrl().toString();
        } catch (NullPointerException | IndexOutOfBoundsException e) { }
        return null;
    }
}
