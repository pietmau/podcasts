package com.pietrantuono.podcasts.musicservice;


import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

import player.playback.Playback;

public class DelayedStopHandler extends Handler {
    private static final int STOP_DELAY = 30000;
    private WeakReference<MusicServicePresenter> presenterWeakReference;
    private WeakReference<Playback> playbackWeakReference;

    public DelayedStopHandler(Playback playback) {
        super();
        playbackWeakReference = new WeakReference<Playback>(playback);
    }

    @Override
    public void handleMessage(Message msg) {
        MusicServicePresenter service = presenterWeakReference.get();
        Playback playback = playbackWeakReference.get();
        if (service != null && playback != null) {
            if (playback.isPlaying()) {
                return;
            }
            service.stopSelf();
        }
    }

    public void setPresenter(MusicServicePresenter musicServicePresenter) {
        presenterWeakReference = new WeakReference<MusicServicePresenter>(musicServicePresenter);
    }

    public void removeCallbacksAndMessagesAndSendEmptyMessage() {
        removeCallbacksAndMessages(null);
        sendEmptyMessageDelayed(0, STOP_DELAY);

    }
}
