package player.playback;


import android.content.res.Resources;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import com.example.android.uamp.R;

import player.model.MusicProvider;
import player.utils.MediaIDHelper;
import player.utils.WearHelper;

import static player.playback.PlaybackManager.CUSTOM_ACTION_THUMBS_UP;


public class PlaybackStateUpdater {
    private final MusicProvider musicProvider;
    private final Resources resources;
    private final Playback playback;
    private final PlaybackManager.PlaybackServiceCallback serviceCallback;

    public PlaybackStateUpdater(MusicProvider musicProvider, Resources resources, Playback playback,
                                PlaybackManager.PlaybackServiceCallback serviceCallback) {
        this.musicProvider = musicProvider;
        this.resources = resources;
        this.playback = playback;
        this.serviceCallback = serviceCallback;
    }

    public void updatePlaybackState(final String error, final QueueManager queueManager) {
        long position = PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN;
        if (playback != null && playback.isConnected()) {
            position = playback.getCurrentStreamPosition();
        }
        PlaybackStateCompat.Builder stateBuilder = new PlaybackStateCompat.Builder()
                .setActions(getAvailableActions(playback));
        setCustomAction(stateBuilder, queueManager, musicProvider, resources);
        int state = playback.getState();
        if (error != null) {
            stateBuilder.setErrorMessage(error);
            state = PlaybackStateCompat.STATE_ERROR;
        }
        stateBuilder.setState(state, position, 1.0f, SystemClock.elapsedRealtime());
        MediaSessionCompat.QueueItem currentMusic = queueManager.getCurrentMusic();
        if (currentMusic != null) {
            stateBuilder.setActiveQueueItemId(currentMusic.getQueueId());
        }
        serviceCallback.onPlaybackStateUpdated(stateBuilder.build());
        if (state == PlaybackStateCompat.STATE_PLAYING ||
                state == PlaybackStateCompat.STATE_PAUSED) {
            serviceCallback.onNotificationRequired();
        }
    }

    private void setCustomAction(final PlaybackStateCompat.Builder stateBuilder,
                                 final QueueManager queueManager, final MusicProvider musicProvider,
                                 final Resources resources) {
        MediaSessionCompat.QueueItem currentMusic = queueManager.getCurrentMusic();
        if (currentMusic == null) {
            return;
        }
        String mediaId = currentMusic.getDescription().getMediaId();
        if (mediaId == null) {
            return;
        }
        String musicId = MediaIDHelper.extractMusicIDFromMediaID(mediaId);

        int favoriteIcon = musicProvider.isFavorite(musicId) ?
                R.drawable.ic_star_on : R.drawable.ic_star_off;
        Bundle customActionExtras = new Bundle();
        WearHelper.setShowCustomActionOnWear(customActionExtras, true);
        stateBuilder.addCustomAction(new PlaybackStateCompat.CustomAction.Builder(
                CUSTOM_ACTION_THUMBS_UP, resources.getString(R.string.favorite), favoriteIcon)
                .setExtras(customActionExtras)
                .build());
    }

    private long getAvailableActions(final Playback playback) {
        long actions =
                PlaybackStateCompat.ACTION_PLAY_PAUSE |
                        PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID |
                        PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH |
                        PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                        PlaybackStateCompat.ACTION_SKIP_TO_NEXT;
        if (playback.isPlaying()) {
            actions |= PlaybackStateCompat.ACTION_PAUSE;
        } else {
            actions |= PlaybackStateCompat.ACTION_PLAY;
        }
        return actions;
    }
}
