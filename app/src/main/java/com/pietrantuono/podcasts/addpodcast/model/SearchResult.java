package com.pietrantuono.podcasts.addpodcast.model;

import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast;

import java.util.List;

public class SearchResult {
    private final List<Podcast> list;
    private final String query;

    public SearchResult(List<Podcast> list, String query) {
        this.list = list;
        this.query = query;
    }

    public List<Podcast> getList() {
        return list;
    }

    public String getQuery() {
        return query;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SearchResult that = (SearchResult) o;

        return query != null ? query.equals(that.query) : that.query == null;

    }

    @Override
    public int hashCode() {
        return query != null ? query.hashCode() : 0;
    }
}
