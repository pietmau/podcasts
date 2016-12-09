package com.pietrantuono.podcasts.main.customviews;


import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;

import com.pietrantuono.podcasts.R;
import com.pietrantuono.podcasts.main.view.MainActivity;

public class DrawerLayoutWithToggle extends DrawerLayout {
    public DrawerLayoutWithToggle(Context context) {
        super(context);
    }

    public DrawerLayoutWithToggle(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawerLayoutWithToggle(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void addToolbar(Toolbar toolbar) {
        ActionBarDrawerToggle actionBarDrawerToggle =
                new ActionBarDrawerToggle((Activity) getContext(), DrawerLayoutWithToggle.this, toolbar, R.string.open, R.string.close);
        actionBarDrawerToggle.syncState();
        addDrawerListener(actionBarDrawerToggle);
    }
}
