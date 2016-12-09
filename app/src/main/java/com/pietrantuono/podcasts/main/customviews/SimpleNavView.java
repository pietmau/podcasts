package com.pietrantuono.podcasts.main.customviews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.util.AttributeSet;
import android.view.MenuItem;

import com.pietrantuono.podcasts.R;
import com.pietrantuono.podcasts.main.presenter.MainPresenter;

public class SimpleNavView extends NavigationView {
    private MainPresenter listener;
    private DrawerLayoutWithToggle drawerLayout;

    public SimpleNavView(Context context) {
        super(context);
        init();
    }

    public SimpleNavView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SimpleNavView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setNavigationItemSelectedListener(new OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawers();
                int id = item.getItemId();
                switch (id) {
                    case R.id.add_podcast:
                        listener.onAddPodcastSelected();
                        break;
                }
                return true;
            }
        });
    }

    public void setMainPresenterListener(MainPresenter listener) {
        this.listener = listener;
    }

    public void setDrawer(DrawerLayoutWithToggle drawerLayout) {
        this.drawerLayout = drawerLayout;
    }
}
