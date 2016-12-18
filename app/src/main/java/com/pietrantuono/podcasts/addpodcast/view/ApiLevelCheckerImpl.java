package com.pietrantuono.podcasts.addpodcast.view;

import android.content.Context;
import android.os.Build;

public class ApiLevelCheckerImpl implements ApiLevelChecker {

    @Override
    public boolean isLollipopOrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }
}
