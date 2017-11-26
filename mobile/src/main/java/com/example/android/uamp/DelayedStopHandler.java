package com.example.android.uamp;


import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

public class DelayedStopHandler extends Handler {
    private final WeakReference<MusicService> mWeakReference;

    DelayedStopHandler(MusicService service) {
        mWeakReference = new WeakReference<>(service);
    }

    @Override
    public void handleMessage(Message msg) {
        MusicService service = mWeakReference.get();
        if (service != null && service.mPlaybackManager.getPlayback() != null) {
            if (service.mPlaybackManager.getPlayback().isPlaying()) {
                return;
            }
            service.stopSelf();
        }
    }
}
