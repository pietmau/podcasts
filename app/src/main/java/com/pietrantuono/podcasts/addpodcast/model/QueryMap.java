package com.pietrantuono.podcasts.addpodcast.model;

import java.util.HashMap;

class QueryMap extends HashMap<String, String> {
    public QueryMap(String query) {
        put("media", "podcast");
        put("limit", "100");
        put("term", query);
    }
}
