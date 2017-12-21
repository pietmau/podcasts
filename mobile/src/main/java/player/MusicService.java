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

package player;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.media.MediaBrowserCompat.MediaItem;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import player.playback.PlaybackServiceCallback;

import static player.utils.MediaIDHelper.MEDIA_ID_EMPTY_ROOT;

public class MusicService extends MediaBrowserServiceCompat implements CustomMediaService,
        PlaybackServiceCallback {
    @Inject MusicServicePresenter musicServicePresenter;

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerMusicServiceComponent.builder().musicServiceModule(new MusicServiceModule(this)).build().inject(this);
        musicServicePresenter.setService(this);
        musicServicePresenter.onCreate();
    }

    @Override
    public int onStartCommand(Intent startIntent, int flags, int startId) {
        return musicServicePresenter.onStartCommand(startIntent, startId);
    }

    @Override
    public void onDestroy() {
        musicServicePresenter.onDestroy();
    }

    @Override
    public BrowserRoot onGetRoot(@NonNull String clientPackageName, int clientUid,
                                 Bundle rootHints) {
        return new BrowserRoot(MEDIA_ID_EMPTY_ROOT, null);
    }

    @Override
    public void onLoadChildren(@NonNull final String parentMediaId,
                               @NonNull final Result<List<MediaItem>> result) {
        result.sendResult(musicServicePresenter.getPlaylist());
    }

    @Override
    public void onPlaybackStart() {
        musicServicePresenter.onPlayBackStart();
    }

    @Override
    public void onPlaybackStop() {
        musicServicePresenter.onPlaybackStop();
    }

    @Override
    public void onNotificationRequired() {
        musicServicePresenter.onNotificationRequired();
    }

    @Override
    public void onPlaybackStateUpdated(PlaybackStateCompat newState) {
        musicServicePresenter.onPlaybackStateUpdated(newState);
    }

    @Override
    public void downloadAndAddToQueue(@NotNull String uri) {
        musicServicePresenter.downloadAndAddToQueue(uri);
    }
}
