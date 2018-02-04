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
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class PlaybackManager extends MediaSessionCompat.Callback implements Playback.Callback {
    private final QueueManager queueManager;
    private final CustomActionResolver customActionResolver;
    private Playback playback;
    private final PlaybackServiceCallback serviceCallback;
    private final PlaybackStateUpdater playbackStateUpdater;

    public PlaybackManager(PlaybackServiceCallback serviceCallback,
                           QueueManager queueManager,
                           Playback playback,
                           PlaybackStateUpdater playbackStateUpdater,
                           CustomActionResolver customActionResolver) {
        this.serviceCallback = serviceCallback;
        this.queueManager = queueManager;
        this.playbackStateUpdater = playbackStateUpdater;
        this.playback = playback;
        this.playback.setCallback(this);
        this.customActionResolver = customActionResolver;
    }

    public void handlePlayRequest() {
        MediaSessionCompat.QueueItem currentMusic = queueManager.getCurrentMusic();
        if (currentMusic != null) {
            serviceCallback.onPlaybackStart();
            playback.play(currentMusic);
        }
    }

    public void handlePauseRequest() {
        if (playback.isPlaying()) {
            playback.pause();
        }
    }

    public void handleStopRequest(String withError) {
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
        queueManager.setQueueFromMusic(mediaId);
    }

    public void switchToPlayback(Playback playback, boolean resumePlaying) {
        throw new UnsupportedOperationException("Unsupported");
    }

    public void onNewItemEnqueued(String uri) {
        if (queueManager.getCurrentQueueSize() <= 0) {
            playFromMediaId(uri, null);
            return;
        }
        queueManager.addToQueue(uri);
        if (shouldSkipToNext(playback.getState())) {
            skipToNext();
            return;
        }
    }

    private boolean shouldSkipToNext(int state) {
        return (state == PlaybackStateCompat.STATE_STOPPED || state == PlaybackStateCompat.STATE_PAUSED
                || state == PlaybackStateCompat.STATE_NONE || state == PlaybackStateCompat.STATE_ERROR);
    }

    public List<MediaBrowserCompat.MediaItem> getPlaylist() {
        return queueManager.getPlaylist();
    }


    @Override
    public void onPlay() {
        if (queueManager.getCurrentMusic() == null) {
            queueManager.setRandomQueue();
        }
        handlePlayRequest();
    }

    @Override
    public void onSkipToQueueItem(long queueId) {
        queueManager.setCurrentQueueItem(queueId);
        queueManager.updateMetadata();
    }

    @Override
    public void onSeekTo(long position) {
        playback.seekTo((int) position);
    }

    @Override
    public void onPlayFromMediaId(String mediaId, Bundle extras) {
        playFromMediaId(mediaId, extras);
    }

    @Override
    public void onPause() {
        handlePauseRequest();
    }

    @Override
    public void onStop() {
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
        customActionResolver.onCustomAction(action, extras, PlaybackManager.this);
    }

    @Override
    public void onPlayFromSearch(final String query, final Bundle extras) {
        playback.setState(PlaybackStateCompat.STATE_CONNECTING);
        boolean successSearch = queueManager.setQueueFromSearch(query, extras);
        if (successSearch) {
            handlePlayRequest();
            queueManager.updateMetadata();
        } else {
            updatePlaybackState("Could not find music");
        }
    }

    void playFromMediaId(String mediaId, Bundle extras) {
        queueManager.setQueueFromMusic(mediaId);
        handlePlayRequest();
    }

    private void skipToNext() {
        if (queueManager.skipQueuePosition(1)) {
            handlePlayRequest();
        } else {
            handleStopRequest("Cannot skip");
        }
        queueManager.updateMetadata();
    }

    public void downloadAndAddToQueue(@NotNull String uri) {
        serviceCallback.downloadAndAddToQueue(uri);
    }

}
