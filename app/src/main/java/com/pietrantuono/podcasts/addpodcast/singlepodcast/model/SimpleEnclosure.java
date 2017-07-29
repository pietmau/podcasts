package com.pietrantuono.podcasts.addpodcast.singlepodcast.model;

import com.rometools.rome.feed.CopyFrom;
import com.rometools.rome.feed.synd.SyndEnclosure;

import io.realm.RealmObject;

public class SimpleEnclosure extends RealmObject implements SyndEnclosure {
    private String url;
    private long length;
    private String type;

    public SimpleEnclosure(SyndEnclosure syndEnclosure) {
        setLength(syndEnclosure.getLength());
        setUrl(syndEnclosure.getUrl());
        setType(syndEnclosure.getType());
    }

    public SimpleEnclosure() {
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public long getLength() {
        return length;
    }

    @Override
    public void setLength(long length) {
        this.length = length;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public Class<? extends CopyFrom> getInterface() {
        return null;
    }

    @Override
    public void copyFrom(CopyFrom obj) {

    }
}
