package com.pietrantuono.podcasts.main.view;

import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.pietrantuono.podcasts.R;
import com.pietrantuono.podcasts.addpodcast.view.AddPodcastFragment;
import com.pietrantuono.podcasts.main.customviews.DrawerLayoutWithToggle;
import com.pietrantuono.podcasts.main.customviews.SimpleNavView;
import com.pietrantuono.podcasts.main.model.MainModel;
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
    @Inject TransitionsFramework transitionsFramework;
    @BindView(R.id.drawer) DrawerLayoutWithToggle drawerLayoutWithToggle;
    @BindView(R.id.maintoolbar) Toolbar mainToolbar;
    @BindView(R.id.nav_view) SimpleNavView simpleNavView;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        initDependencies();
        setUpViews();
        mainPresenter.bindView(MainActivity.this);
        transitionsFramework.initMainActivityTransitions(MainActivity.this);
    }

    private void initDependencies() {
        ButterKnife.bind(MainActivity.this);
        DaggerMainComponent.builder().mainModule(new MainModule(MainActivity.this)).build().inject(MainActivity.this);
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
