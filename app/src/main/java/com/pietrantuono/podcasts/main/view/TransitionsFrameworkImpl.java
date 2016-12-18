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

    public TransitionsFrameworkImpl(ApiLevelChecker apiLevelChecker) {
        this.apiLevelChecker = apiLevelChecker;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initMainActivityTransitions(Activity activity) {
        if (!apiLevelChecker.isLollipopOrHigher()) {
            return;
        }
        Slide slide = new Slide();
        slide.setDuration(500);
        slide.setSlideEdge(Gravity.LEFT);
        slide.excludeTarget(android.R.id.statusBarBackground, true);
        slide.excludeTarget(android.R.id.navigationBarBackground, true);
        Window window = activity.getWindow();
        window.setEnterTransition(slide);
        window.setExitTransition(slide);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initDetailTransitions(Activity activity) {
        if (!apiLevelChecker.isLollipopOrHigher()) {
            return;
        }
        Slide slide = new Slide();
        slide.setDuration(100);
        slide.setSlideEdge(Gravity.RIGHT);
        slide.excludeTarget(android.R.id.statusBarBackground, true);
        slide.excludeTarget(android.R.id.navigationBarBackground, true);
        Window window = activity.getWindow();
        window.setEnterTransition(slide);
        window.setExitTransition(slide);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

}
