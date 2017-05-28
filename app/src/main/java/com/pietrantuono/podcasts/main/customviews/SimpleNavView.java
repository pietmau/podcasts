package com.pietrantuono.podcasts.main.customviews;

import android.content.Context;
import android.support.design.widget.NavigationView;
import android.util.AttributeSet;

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
        setNavigationItemSelectedListener(item -> {
            drawerLayout.closeDrawers();
            int id = item.getItemId();
            switch (id) {
                case R.id.add_podcast:
                    listener.onAddPodcastSelected();
                    break;
                case R.id.subscribed:
                    listener.onSubscribeSelected();
                    break;
            }
            return true;
        });
    }

    public void setMainPresenterListener(MainPresenter listener) {
        this.listener = listener;
    }

    public void setDrawer(DrawerLayoutWithToggle drawerLayout) {
        this.drawerLayout = drawerLayout;
    }
}
