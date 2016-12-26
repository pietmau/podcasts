package com.pietrantuono.podcasts.main.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;

public interface TransitionsFramework {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    void initMainActivityTransitions(AppCompatActivity activity);

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    void initDetailTransitionAndPostponeEnterTransition(AppCompatActivity activity);

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    void startPostponedEnterTransition(AppCompatActivity activity);
}
