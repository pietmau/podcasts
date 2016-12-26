package com.pietrantuono.podcasts.main.view;

import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.crashlytics.android.Crashlytics;
import com.pietrantuono.podcasts.PresenterManager;
import com.pietrantuono.podcasts.R;
import com.pietrantuono.podcasts.addpodcast.view.AddPodcastFragment;
import com.pietrantuono.podcasts.main.customviews.DrawerLayoutWithToggle;
import com.pietrantuono.podcasts.main.customviews.SimpleNavView;
import com.pietrantuono.podcasts.main.dagger.MainComponent;
import com.pietrantuono.podcasts.main.model.MainModel;
import com.pietrantuono.podcasts.main.presenter.MainPresenter;
import com.pietrantuono.podcasts.main.dagger.DaggerMainComponent;
import com.pietrantuono.podcasts.main.dagger.MainModule;

import io.fabric.sdk.android.Fabric;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainView {
    @Inject MainPresenter mainPresenter;
    @Inject PresenterManager presenterManager;
    @Inject TransitionsFramework transitionsFramework;
    @BindView(R.id.drawer) DrawerLayoutWithToggle drawerLayoutWithToggle;
    @BindView(R.id.maintoolbar) Toolbar mainToolbar;
    @BindView(R.id.nav_view) SimpleNavView simpleNavView;
    private FragmentManager fragmentManager;
    private MainComponent component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initDependencies();
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);
        fragmentManager = getSupportFragmentManager();
        setUpViews();
        mainPresenter.bindView(MainActivity.this);
        transitionsFramework.initMainActivityTransitions(MainActivity.this);
        throw new RuntimeException("foo");
    }

    private void initDependencies() {
        component = DaggerMainComponent.builder().mainModule(new MainModule(MainActivity.this)).build();
        component.inject(MainActivity.this);
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
        mainPresenter.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainPresenter.onResume();
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
    public Object onRetainCustomNonConfigurationInstance() {
        return presenterManager;
    }

    public MainComponent getComponent() {
        return component;
    }
}
