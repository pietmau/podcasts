package com.pietrantuono.podcasts;


import com.crashlytics.android.Crashlytics;

import javax.inject.Inject;

public class CrashlyticsWrapper {

    @Inject
    public CrashlyticsWrapper() {
    }

    public void logException(Throwable e){
        Crashlytics.logException(e);
    }

}
