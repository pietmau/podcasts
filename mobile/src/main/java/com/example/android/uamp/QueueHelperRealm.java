package com.example.android.uamp;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompat;

import com.example.android.uamp.model.MusicProvider;
import com.example.android.uamp.utils.QueueHelper;

import java.util.List;

class QueueHelperRealm implements QueueHelper {
    @Override
    public List<MediaSessionCompat.QueueItem> getPlayingQueue(String mediaId, MusicProvider musicProvider) {
        return null;
    }

    @Override
    public List<MediaSessionCompat.QueueItem> getPlayingQueueFromSearch(String query, Bundle queryParams, MusicProvider musicProvider) {
        return null;
    }

    @Override
    public int getMusicIndexOnQueue(Iterable<MediaSessionCompat.QueueItem> queue, String mediaId) {
        return 0;
    }

    @Override
    public int getMusicIndexOnQueue(Iterable<MediaSessionCompat.QueueItem> queue, long queueId) {
        return 0;
    }

    @Override
    public List<MediaSessionCompat.QueueItem> getRandomQueue(MusicProvider musicProvider) {
        return null;
    }

    @Override
    public boolean isIndexPlayable(int index, List<MediaSessionCompat.QueueItem> queue) {
        return false;
    }

    @Override
    public boolean equals(List<MediaSessionCompat.QueueItem> list1, List<MediaSessionCompat.QueueItem> list2) {
        return false;
    }

    @Override
    public boolean isQueueItemPlaying(Context context, MediaSessionCompat.QueueItem queueItem) {
        return false;
    }
}