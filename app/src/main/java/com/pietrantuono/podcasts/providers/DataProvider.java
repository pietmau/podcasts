package com.pietrantuono.podcasts.providers;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.provider.LiveFolders;
import android.support.annotation.Nullable;

import java.util.HashMap;

public class DataProvider extends ContentProvider {
    private static final UriMatcher sUriMatcher;
    private static final String AUTHORITY = "com.pietrantuono.podcasts.providers";
    private static final int PODCAST = 0;
    private static final int EPISODE = 1;

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(AUTHORITY, "podcast", PODCAST);
        sUriMatcher.addURI(AUTHORITY, "episode", EPISODE);
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }



}
