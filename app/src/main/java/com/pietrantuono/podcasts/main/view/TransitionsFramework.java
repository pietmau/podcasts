package com.pietrantuono.podcasts.main.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;

public interface TransitionsFramework {
    void initMainActivityTransitions(Activity activity);

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    void initDetailTransitions(Activity activity);
}
