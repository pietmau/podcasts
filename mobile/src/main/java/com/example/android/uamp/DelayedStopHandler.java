package com.example.android.uamp;


import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

public class DelayedStopHandler extends Handler {
    private WeakReference<MusicServicePresenter> mWeakReference;

    @Override
    public void handleMessage(Message msg) {
        MusicServicePresenter service = mWeakReference.get();
        if (service != null && service.mPlaybackManager.getPlayback() != null) {
            if (service.mPlaybackManager.getPlayback().isPlaying()) {
                return;
            }
            service.stopSelf();
        }
    }

    public void setPresenter(MusicServicePresenter musicServicePresenter) {
        mWeakReference = new WeakReference<MusicServicePresenter>(musicServicePresenter);
    }
}
