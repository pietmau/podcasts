package com.pietrantuono.podcasts.main.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;


import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.view.Gravity;
import android.view.Window;

import com.pietrantuono.podcasts.addpodcast.view.ApiLevelChecker;

public class TransitionsFrameworkImpl implements TransitionsFramework {
    private final ApiLevelChecker apiLevelChecker;
    private static final long SHORT = 400;
    private static final long LONG = 800;

    public TransitionsFrameworkImpl(ApiLevelChecker apiLevelChecker) {
        this.apiLevelChecker = apiLevelChecker;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initMainActivityTransitions(Activity activity) {
        if (!apiLevelChecker.isLollipopOrHigher()) {
            return;
        }
        Slide shortSlide = createSlide(SHORT, Gravity.LEFT);
        Slide longSlide = createSlide(LONG, Gravity.LEFT);
        Window window = activity.getWindow();
        window.setEnterTransition(shortSlide);
        window.setExitTransition(longSlide);
        window.setReenterTransition(shortSlide);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initDetailTransitions(Activity activity) {
        if (!apiLevelChecker.isLollipopOrHigher()) {
            return;
        }
        Slide shortSlide = createSlide(SHORT, Gravity.RIGHT);
        Slide longSlide = createSlide(LONG, Gravity.RIGHT);
        Window window = activity.getWindow();
        window.setEnterTransition(shortSlide);
        window.setExitTransition(shortSlide);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private Slide createSlide(long duration, int edge){
        Slide slide = new Slide();
        slide.setDuration(duration);
        slide.setSlideEdge(edge);
        slide.excludeTarget(android.R.id.statusBarBackground, true);
        slide.excludeTarget(android.R.id.navigationBarBackground, true);
        return slide;
    }
}
