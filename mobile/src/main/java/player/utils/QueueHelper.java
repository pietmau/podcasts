package player.utils;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.session.MediaSessionCompat;

import java.util.List;

import player.model.MusicProvider;

public interface QueueHelper {
    List<MediaSessionCompat.QueueItem> getPlayingQueue(String mediaId,
                                                       MusicProvider musicProvider);

    List<MediaSessionCompat.QueueItem> getPlayingQueueFromSearch(String query,
                                                                 Bundle queryParams, MusicProvider musicProvider);

    int getMusicIndexOnQueue(Iterable<MediaSessionCompat.QueueItem> queue,
                             String mediaId);

    int getMusicIndexOnQueue(Iterable<MediaSessionCompat.QueueItem> queue,
                             long queueId);

    List<MediaSessionCompat.QueueItem> getRandomQueue(MusicProvider musicProvider);

    boolean isIndexPlayable(int index, List<MediaSessionCompat.QueueItem> queue);

    boolean equals(List<MediaSessionCompat.QueueItem> list1,
                   List<MediaSessionCompat.QueueItem> list2);

    boolean isQueueItemPlaying(Context context,
                               MediaSessionCompat.QueueItem queueItem);

    List<MediaBrowserCompat.MediaItem> getPlaylist();
}
