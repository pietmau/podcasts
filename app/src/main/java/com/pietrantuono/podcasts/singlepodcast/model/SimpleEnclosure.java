package com.pietrantuono.podcasts.singlepodcast.model;

import com.rometools.rome.feed.CopyFrom;
import com.rometools.rome.feed.synd.SyndEnclosure;

import io.realm.RealmObject;

public class SimpleEnclosure extends RealmObject implements SyndEnclosure  {
    private int unused;

    public SimpleEnclosure(SyndEnclosure syndEnclosure) {

    }

    public SimpleEnclosure() {
    }

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public void setUrl(String url) {

    }

    @Override
    public long getLength() {
        return 0;
    }

    @Override
    public void setLength(long length) {

    }

    @Override
    public String getType() {
        return null;
    }

    @Override
    public void setType(String type) {

    }

    @Override
    public Class<? extends CopyFrom> getInterface() {
        return null;
    }

    @Override
    public void copyFrom(CopyFrom obj) {

    }
}
