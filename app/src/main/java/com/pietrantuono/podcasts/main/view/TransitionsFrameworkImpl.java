package com.pietrantuono.podcasts.main.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pietrantuono.podcasts.R;
import com.pietrantuono.podcasts.addpodcast.view.ApiLevelChecker;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TransitionsFrameworkImpl implements TransitionsFramework {
    private final ApiLevelChecker apiLevelChecker;

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

    @NonNull
    @Override
    public Pair[] getPairs(ImageView imageView, Activity activity, LinearLayout titleContainer) {
        if (!apiLevelChecker.isLollipopOrHigher()) {
            return new Pair[0];
        }
        Pair[] pairs = getNavigationBarAndImage(imageView, activity);
        pairs = getLinearLayout(activity, titleContainer, pairs);
        return pairs;
    }

    private Pair[] getNavigationBarAndImage(ImageView imageView, Activity activity) {
        Pair[] pairs = getNavigationBar(activity);
        pairs[0] = new Pair(imageView, activity.getString(R.string.detail_transition_image));
        return pairs;
    }

    private Pair[] getLinearLayout(Activity activity, LinearLayout titleContainer, Pair[] pairs) {
        pairs[1] = new Pair(titleContainer, activity.getString(R.string.detail_transition_toolbar));
        return pairs;
    }

    @NonNull
    private Pair[] getNavigationBar(Activity activity) {
        View decor = activity.getWindow().getDecorView();
        View navBar = decor.findViewById(android.R.id.navigationBarBackground);
        Pair[] pairs;
        if (navBar != null) {
            pairs = new Pair[3];
            pairs[2] = new Pair(navBar, "transition");
        } else {
            pairs = new Pair[2];
        }
        return pairs;
    }

    @NotNull
    @Override
    public Pair<View, String>[] getPairs(@NotNull ImageView imageView, @NotNull Activity activity, @Nullable CardView cardView) {
        if (!apiLevelChecker.isLollipopOrHigher()) {
            return new Pair[0];
        }
        Pair[] pairs = getNavigationBarAndImage(imageView, activity);
        pairs = getCardView(activity, cardView, pairs);
        return pairs;
    }

    private Pair[] getCardView(Activity activity, CardView cardView, Pair[] pairs) {
        pairs[1] = new Pair(cardView, activity.getString(R.string.detail_transition_card));
        return pairs;
    }
}
