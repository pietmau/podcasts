package com.pietrantuono.podcasts;

import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.pietrantuono.podcasts.main.MainPresenter;
import com.pietrantuono.podcasts.main.MainView;
import com.pietrantuono.podcasts.main.dagger.DaggerMainComponent;
import com.pietrantuono.podcasts.main.dagger.MainModule;
import com.pietrantuono.podcasts.main.model.ModelService;

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
		initDependencies();
		presenter.OnCreate(MainActivity.this);
		setUpActionBar();
	}

	private void initDependencies() {
		ButterKnife.bind(MainActivity.this);
		DaggerMainComponent.builder().mainModule(new MainModule(MainActivity.this)).build().inject(MainActivity.this);
	}

	private void setUpActionBar() {
		setSupportActionBar((Toolbar) findViewById(R.id.maintollbar));
	}

	@Override
	protected void onPause() {
		super.onPause();
		unbindService(modelServiceConnection);
	}

	@Override
	protected void onResume() {
		super.onResume();
		bindService(modelServiceIntent, modelServiceConnection, BIND_AUTO_CREATE);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		presenter.onDestroy();
		if (!isChangingConfigurations()) {
			stopService(modelServiceIntent);
		}
	}

}
