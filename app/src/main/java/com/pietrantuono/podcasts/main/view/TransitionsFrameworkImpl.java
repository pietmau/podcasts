package com.pietrantuono.podcasts.main.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pietrantuono.podcasts.R;
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
    public void initMainActivityTransitions(AppCompatActivity activity) {
        if (!apiLevelChecker.isLollipopOrHigher()) {
            return;
        }
        Window window = activity.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initDetailTransitions(AppCompatActivity activity) {
        if (!apiLevelChecker.isLollipopOrHigher()) {
            return;
        }
        Window window = activity.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        activity.postponeEnterTransition();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void startPostponedEnterTransition(AppCompatActivity activity) {
        if (!apiLevelChecker.isLollipopOrHigher()) {
            return;
        }
        activity.startPostponedEnterTransition();
    }


    @Override
    public Pair[] getPairs(ImageView imageView, Activity activity, LinearLayout titleContainer) {
        if (!apiLevelChecker.isLollipopOrHigher()) {
            return new Pair[0];
        }
        View decor = activity.getWindow().getDecorView();
        View navBar = decor.findViewById(android.R.id.navigationBarBackground);
        Pair[] pairs;
        if (navBar != null) {
            pairs = new Pair[3];
            pairs[2] = new Pair(navBar, "transition");
        } else {
            pairs = new Pair[2];
        }
        pairs[0] = new Pair(imageView, activity.getString(R.string.detail_transition_image));
        pairs[1] = new Pair(titleContainer, activity.getString(R.string.detail_transition_toolbar));
        return pairs;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private Slide createSlide(long duration, int edge) {
        Slide slide = new Slide();
        slide.setDuration(duration);
        slide.setSlideEdge(edge);
        slide.excludeTarget(android.R.id.statusBarBackground, true);
        slide.excludeTarget(android.R.id.navigationBarBackground, true);
        return slide;
    }
}
