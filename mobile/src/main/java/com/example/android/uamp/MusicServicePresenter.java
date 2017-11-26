package com.example.android.uamp;


import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import com.example.android.uamp.playback.PlaybackManager;

import static android.app.Service.START_STICKY;

public class MusicServicePresenter {
    private static final int STOP_DELAY = 30000;
    private CustomMediaService service;
    private final MediaSessionCompat mSession;
    private final PlaybackManager mPlaybackManager;
    private final DelayedStopHandler mDelayedStopHandler;
    private MediaNotificationManager mMediaNotificationManager;

    public MusicServicePresenter(MediaSessionCompat mSession, PlaybackManager mPlaybackManager, DelayedStopHandler mDelayedStopHandler) {
        this.mSession = mSession;
        this.mPlaybackManager = mPlaybackManager;
        this.mDelayedStopHandler = mDelayedStopHandler;
    }

    public void setService(CustomMediaService service) {
        this.service = service;
    }

    public void onCreate() {
        service.setSessionToken(mSession.getSessionToken());
        mSession.setCallback(mPlaybackManager.getMediaSessionCallback());
        mSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        //TODO mSession.setSessionActivity(pi);
        mSession.setExtras(new Bundle());
        mPlaybackManager.updatePlaybackState(null);
        try {
            mMediaNotificationManager = new MediaNotificationManager((MusicService) service);
        } catch (RemoteException e) {
            throw new IllegalStateException("Could not create a MediaNotificationManager", e);
        }
    }

    public int onStartCommand(Intent startIntent, int startId) {
        if (startIntent != null) {
            String action = startIntent.getAction();
            String command = startIntent.getStringExtra(Constants.CMD_NAME);
            if (Constants.ACTION_CMD.equals(action)) {
                if (Constants.CMD_PAUSE.equals(command)) {
                    mPlaybackManager.handlePauseRequest();
                }
            }
        }
        mDelayedStopHandler.removeCallbacksAndMessages(null);
        mDelayedStopHandler.sendEmptyMessageDelayed(0, STOP_DELAY);
        return START_STICKY;
    }

    public void onDestroy() {
        mPlaybackManager.handleStopRequest(null);
        mMediaNotificationManager.stopNotification();
        mDelayedStopHandler.removeCallbacksAndMessages(null);
        mSession.release();
    }

    public void onPlayBackStart() {
        mSession.setActive(true);
        mDelayedStopHandler.removeCallbacksAndMessages(null);
        service.startService(new Intent(service.getApplicationContext(), MusicService.class));
    }

    public void onPlaybackStop() {
        mSession.setActive(false);
        mDelayedStopHandler.removeCallbacksAndMessages(null);
        mDelayedStopHandler.sendEmptyMessageDelayed(0, STOP_DELAY);
        service.stopForeground(true);
    }

    public void onNotificationRequired() {
        mMediaNotificationManager.startNotification();
    }

    public void onPlaybackStateUpdated(PlaybackStateCompat newState) {
        mSession.setPlaybackState(newState);
    }
}
