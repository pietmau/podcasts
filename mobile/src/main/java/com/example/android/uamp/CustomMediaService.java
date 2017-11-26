package com.example.android.uamp;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.media.session.MediaSessionCompat;

interface CustomMediaService {
    void setSessionToken(MediaSessionCompat.Token sessionToken);

    Context getApplicationContext();

    ComponentName startService(Intent intent);

    void stopForeground(boolean removeNotification);
}
