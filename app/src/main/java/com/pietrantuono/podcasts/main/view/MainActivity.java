package com.pietrantuono.podcasts.main.view;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;

import com.crashlytics.android.Crashlytics;
import com.pietrantuono.podcasts.R;
import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.BaseActivity;
import com.pietrantuono.podcasts.addpodcast.view.AddPodcastFragment;
import com.pietrantuono.podcasts.application.App;
import com.pietrantuono.podcasts.application.MainComponent;
import com.pietrantuono.podcasts.main.customviews.DrawerLayoutWithToggle;
import com.pietrantuono.podcasts.main.customviews.SimpleNavView;
import com.pietrantuono.podcasts.main.dagger.MainModule;
import com.pietrantuono.podcasts.main.presenter.MainPresenter;
import com.pietrantuono.podcasts.subscribedpodcasts.view.SubscribedPodcastFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends BaseActivity implements MainView {
    @Inject MainPresenter mainPresenter;
    @Inject TransitionsFramework transitionsFramework;
    @BindView(R.id.drawer) DrawerLayoutWithToggle drawerLayoutWithToggle;
    @BindView(R.id.maintoolbar) Toolbar mainToolbar;
    @BindView(R.id.nav_view) SimpleNavView simpleNavView;
    private FragmentManager fragmentManager;
    private MainComponent mainComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initDependencies();
        super.onCreate(savedInstanceState);
        Fabric.with(getApplicationContext(), new Crashlytics());
        setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);
        fragmentManager = getSupportFragmentManager();
        setUpViews();
        mainPresenter.bindView(MainActivity.this);
        mainPresenter.onCreate(savedInstanceState != null);
        transitionsFramework.initMainActivityTransitions(MainActivity.this);
    }

    private void initDependencies() {
        mainComponent = ((App) getApplicationContext()).getApplicationComponent().with(new MainModule());
        mainComponent.inject(this);
    }

    private void setUpViews() {
        setSupportActionBar(mainToolbar);
        drawerLayoutWithToggle.addToolbar(mainToolbar);
        simpleNavView.setMainPresenterListener(mainPresenter);
        simpleNavView.setDrawer(drawerLayoutWithToggle);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mainPresenter.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainPresenter.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.onDestroy();
    }

    @Override
    public void navigateToAddPodcast() {
        AddPodcastFragment.navigateTo(fragmentManager);
    }

    @Override
    public void navigateToSubscribedPodcasts() {
        SubscribedPodcastFragment.Companion.navigateTo(fragmentManager);
    }

    public MainComponent getMainComponent() {
        return mainComponent;
    }
}
