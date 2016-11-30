package com.pietrantuono.podcasts;

import android.content.Intent;
import android.content.ServiceConnection;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pietrantuono.podcasts.main.MainPresenter;
import com.pietrantuono.podcasts.main.MainView;
import com.pietrantuono.podcasts.main.ModelService;
import com.pietrantuono.podcasts.main.dagger.DaggerMainComponent;
import com.pietrantuono.podcasts.main.dagger.MainModule;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainView {
    @Inject
    MainPresenter presenter;
    @Inject
    Intent modelServiceIntent;
    @Inject
    ServiceConnection modelServiceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        modelServiceIntent = new Intent(MainActivity.this, ModelService.class);
        setContentView(R.layout.activity_main);
        initDependencyGraph();
        presenter.OnCreate(MainActivity.this);
    }

    private void initDependencyGraph() {
        ButterKnife.bind(MainActivity.this);
        DaggerMainComponent.builder().mainModule(new MainModule(MainActivity.this)).build().inject(MainActivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isChangingConfigurations()) {
            unbindModelService();
        } else {
            unbindModelService();
            stopModelService();
        }
    }

    private void stopModelService() {
        stopService(modelServiceIntent);
    }

    private void unbindModelService() {
        unbindService(modelServiceConnection);
    }
}
