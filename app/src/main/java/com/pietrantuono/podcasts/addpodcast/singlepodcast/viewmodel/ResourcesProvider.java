package com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;

import javax.inject.Inject;

public class ResourcesProvider {
    private final Context context;

    @Inject
    public ResourcesProvider(Context context) {
        this.context = context;
    }

    public Drawable ContextCompatgetDrawable(@DrawableRes int drawableResource) {
        return ContextCompat.getDrawable(context, drawableResource);
    }

    public String getString(@StringRes int stringRes) {
        return context.getString(stringRes);
    }

    public int getColor(int color) {
        return ContextCompat.getColor(context, color);
    }
}
