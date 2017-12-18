/*
* Copyright (C) 2014 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package player.playback;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import java.util.List;

import player.model.MusicProvider;
import player.utils.LogHelper;
import player.utils.MediaIDHelper;

public class PlaybackManager implements Playback.Callback {
    public static String CUSTOM_ACTION_ADD_TO_QUEUE = "custom_action_add_to_queue";
    public static String EXTRA_EPISODE_URI = "episode_uri";
    private static final String TAG = LogHelper.makeLogTag(PlaybackManager.class);
    static final String CUSTOM_ACTION_THUMBS_UP = "com.example.android.uamp.THUMBS_UP";
    private final MusicProvider musicProvider;
    private final QueueManager queueManager;
    private Playback playback;
    private final PlaybackServiceCallback serviceCallback;
    private final MediaSessionCallback mMediaSessionCallback;
    private final PlaybackStateUpdater playbackStateUpdater;

    public PlaybackManager(PlaybackServiceCallback serviceCallback,
                           MusicProvider musicProvider,
                           QueueManager queueManager,
                           Playback playback,
                           PlaybackStateUpdater playbackStateUpdater) {
        this.musicProvider = musicProvider;
        this.serviceCallback = serviceCallback;
        this.queueManager = queueManager;
        this.playbackStateUpdater = playbackStateUpdater;
        mMediaSessionCallback = new MediaSessionCallback();
        this.playback = playback;
        this.playback.setCallback(this);
    }

    public Playback getPlayback() {
        return playback;
    }

    public MediaSessionCompat.Callback getMediaSessionCallback() {
        return mMediaSessionCallback;
    }

    public void handlePlayRequest() {
        LogHelper.d(TAG, "handlePlayRequest: mState=" + playback.getState());
        MediaSessionCompat.QueueItem currentMusic = queueManager.getCurrentMusic();
        if (currentMusic != null) {
            serviceCallback.onPlaybackStart();
            playback.play(currentMusic);
        }
    }

    public void handlePauseRequest() {
        LogHelper.d(TAG, "handlePauseRequest: mState=" + playback.getState());
        if (playback.isPlaying()) {
            playback.pause();
        }
    }

    public void handleStopRequest(String withError) {
        LogHelper.d(TAG, "handleStopRequest: mState=" + playback.getState() + " error=", withError);
        playback.stop(true);
        serviceCallback.onPlaybackStop();
        updatePlaybackState(withError);
    }

    public void updatePlaybackState(String error) {
        playbackStateUpdater.updatePlaybackState(error, queueManager);
    }

    @Override
    public void onCompletion() {
        if (queueManager.skipQueuePosition(1)) {
            handlePlayRequest();
            queueManager.updateMetadata();
        } else {
            handleStopRequest(null);
        }
    }

    @Override
    public void onPlaybackStatusChanged(int state) {
        updatePlaybackState(null);
    }

    @Override
    public void onError(String error) {
        updatePlaybackState(error);
    }

    @Override
    public void setCurrentMediaId(String mediaId) {
        LogHelper.d(TAG, "setCurrentMediaId", mediaId);
        queueManager.setQueueFromMusic(mediaId);
    }


    /**
     * Switch to a different Playback instance, maintaining all playback state, if possible.
     *
     * @param playback switch to this playback
     */
    public void switchToPlayback(Playback playback, boolean resumePlaying) {
        if (playback == null) {
            throw new IllegalArgumentException("Playback cannot be null");
        }
        // Suspends current state.
        int oldState = this.playback.getState();
        long pos = this.playback.getCurrentStreamPosition();
        String currentMediaId = this.playback.getCurrentMediaId();
        this.playback.stop(false);
        playback.setCallback(this);
        playback.setCurrentMediaId(currentMediaId);
        playback.seekTo(pos < 0 ? 0 : pos);
        playback.start();
        // Swaps instance.
        this.playback = playback;
        switch (oldState) {
            case PlaybackStateCompat.STATE_BUFFERING:
            case PlaybackStateCompat.STATE_CONNECTING:
            case PlaybackStateCompat.STATE_PAUSED:
                this.playback.pause();
                break;
            case PlaybackStateCompat.STATE_PLAYING:
                MediaSessionCompat.QueueItem currentMusic = queueManager.getCurrentMusic();
                if (resumePlaying && currentMusic != null) {
                    this.playback.play(currentMusic);
                } else if (!resumePlaying) {
                    this.playback.pause();
                } else {
                    this.playback.stop(true);
                }
                break;
            case PlaybackStateCompat.STATE_NONE:
                break;
            default:
                LogHelper.d(TAG, "Default called. Old state is ", oldState);
        }
    }

    public void onNewItemEnqueued(String uri) {
        int size = queueManager.getCurrentQueueSize();
        if (size == 1) {
            playFromMediaId(uri, null);
            return;
        }
        queueManager.addToQueue(uri);
        int state = playback.getState();
        if (state == PlaybackStateCompat.STATE_STOPPED) {
            skipToNext();
            return;
        }
        if (state == PlaybackStateCompat.STATE_PAUSED) {
            skipToNext();
            return;
        }
        if (state == PlaybackStateCompat.STATE_NONE) {
            skipToNext();
            return;
        }
        if (state == PlaybackStateCompat.STATE_ERROR) {
            skipToNext();
            return;
        }
    }

    public List<MediaBrowserCompat.MediaItem> getPlaylist() {
        return queueManager.getPlaylist();
    }


    private class MediaSessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            LogHelper.d(TAG, "play");
            if (queueManager.getCurrentMusic() == null) {
                queueManager.setRandomQueue();
            }
            handlePlayRequest();
        }

        @Override
        public void onSkipToQueueItem(long queueId) {
            LogHelper.d(TAG, "OnSkipToQueueItem:" + queueId);
            queueManager.setCurrentQueueItem(queueId);
            queueManager.updateMetadata();
        }

        @Override
        public void onSeekTo(long position) {
            LogHelper.d(TAG, "onSeekTo:", position);
            playback.seekTo((int) position);
        }

        @Override
        public void onPlayFromMediaId(String mediaId, Bundle extras) {
            playFromMediaId(mediaId, extras);
        }

        @Override
        public void onPause() {
            LogHelper.d(TAG, "pause. current state=" + playback.getState());
            handlePauseRequest();
        }

        @Override
        public void onStop() {
            LogHelper.d(TAG, "stop. current state=" + playback.getState());
            handleStopRequest(null);
        }

        @Override
        public void onSkipToNext() {
            PlaybackManager.this.skipToNext();
        }

        @Override
        public void onSkipToPrevious() {
            if (queueManager.skipQueuePosition(-1)) {
                handlePlayRequest();
            } else {
                handleStopRequest("Cannot skip");
            }
            queueManager.updateMetadata();
        }

        @Override
        public void onCustomAction(@NonNull String action, Bundle extras) {
            if (CUSTOM_ACTION_THUMBS_UP.equals(action)) {
                LogHelper.i(TAG, "onCustomAction: favorite for current track");
                MediaSessionCompat.QueueItem currentMusic = queueManager.getCurrentMusic();
                if (currentMusic != null) {
                    String mediaId = currentMusic.getDescription().getMediaId();
                    if (mediaId != null) {
                        String musicId = MediaIDHelper.extractMusicIDFromMediaID(mediaId);
                        musicProvider.setFavorite(musicId, !musicProvider.isFavorite(musicId));
                    }
                }
                updatePlaybackState(null);
                return;
            }
            if (CUSTOM_ACTION_ADD_TO_QUEUE.equals(action)) {
                if (extras == null || extras.getString(EXTRA_EPISODE_URI) == null) {
                    return;
                }
                String uri = extras.getString(EXTRA_EPISODE_URI);
                onNewItemEnqueued(uri);
            }
        }

        @Override
        public void onPlayFromSearch(final String query, final Bundle extras) {
            LogHelper.d(TAG, "playFromSearch  query=", query, " extras=", extras);
            playback.setState(PlaybackStateCompat.STATE_CONNECTING);
            boolean successSearch = queueManager.setQueueFromSearch(query, extras);
            if (successSearch) {
                handlePlayRequest();
                queueManager.updateMetadata();
            } else {
                updatePlaybackState("Could not find music");
            }
        }
    }

    private void playFromMediaId(String mediaId, Bundle extras) {
        LogHelper.d(TAG, "playFromMediaId mediaId:", mediaId, "  extras=", extras);
        queueManager.setQueueFromMusic(mediaId);
        handlePlayRequest();
    }

    private void skipToNext() {
        LogHelper.d(TAG, "skipToNext");
        if (queueManager.skipQueuePosition(1)) {
            handlePlayRequest();
        } else {
            handleStopRequest("Cannot skip");
        }
        queueManager.updateMetadata();
    }

    public interface PlaybackServiceCallback {
        void onPlaybackStart();

        void onNotificationRequired();

        void onPlaybackStop();

        void onPlaybackStateUpdated(PlaybackStateCompat newState);
    }
}
