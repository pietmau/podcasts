package com.pietrantuono.podcasts.main.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public interface TransitionsFramework {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    void initMainActivityTransitions(AppCompatActivity activity);

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    void initDetailTransitions(AppCompatActivity activity);

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    void startPostponedEnterTransition(AppCompatActivity activity);

    Pair<View, String>[] getPairs(ImageView imageView, Activity activity, LinearLayout titleContainer);
}
