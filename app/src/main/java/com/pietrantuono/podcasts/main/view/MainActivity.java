package com.pietrantuono.podcasts.main.view;

import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.pietrantuono.podcasts.R;
import com.pietrantuono.podcasts.addpodcast.view.AddPodcastFragment;
import com.pietrantuono.podcasts.main.customviews.SimpleNavView;
import com.pietrantuono.podcasts.main.dagger.MainComponent;
import com.pietrantuono.podcasts.main.presenter.MainPresenter;
import com.pietrantuono.podcasts.main.dagger.DaggerMainComponent;
import com.pietrantuono.podcasts.main.dagger.MainModule;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainView {
    @Inject MainPresenter mainPresenter;
    @Inject Intent modelServiceIntent;
    @Inject ServiceConnection modelServiceConnection;
    @BindView(R.id.drawer) DrawerLayout drawerLayout;
    @BindView(R.id.maintoolbar) Toolbar mainToolbar;
    private FragmentManager fragmentManager;
    private MainModule mainModule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        initDependencies();
        setUpViews();
        mainPresenter.onCreate(MainActivity.this);
    }

    private void initDependencies() {
        ButterKnife.bind(MainActivity.this);
        mainModule = new MainModule(MainActivity.this);
        DaggerMainComponent.builder().mainModule(mainModule).build().inject(MainActivity.this);
    }

    private void setUpViews() {
        setSupportActionBar(mainToolbar);
        ActionBarDrawerToggle actionBarDrawerToggle =
                new ActionBarDrawerToggle(MainActivity.this, drawerLayout, mainToolbar, R.string.open, R.string.close);
        actionBarDrawerToggle.syncState();
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        ((SimpleNavView) findViewById(R.id.nav_view)).setMainPresenterListener(mainPresenter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mainPresenter.onPause();
        unbindService(modelServiceConnection);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainPresenter.onResume();
        bindService(modelServiceIntent, modelServiceConnection, BIND_AUTO_CREATE);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.onDestroy();
        if (!isChangingConfigurations()) {
            stopService(modelServiceIntent);
        }
    }

    @Override
    public void navigateToAddPodcast() {
        AddPodcastFragment.navigateTo(fragmentManager);
    }

}
