package com.pietrantuono.podcasts.apis;

import com.rometools.rome.feed.CopyFrom;
import com.rometools.rome.feed.synd.SyndEnclosure;

public class SimpleEnclosure implements SyndEnclosure {

    public SimpleEnclosure(SyndEnclosure syndEnclosure) {

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
