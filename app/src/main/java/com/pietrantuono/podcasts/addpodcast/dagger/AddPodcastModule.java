package com.pietrantuono.podcasts.addpodcast.dagger;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.pietrantuono.podcasts.addpodcast.AddPodcastPresenter;
import com.pietrantuono.podcasts.main.model.ModelService;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@AddPodcastScope
@Module
public class AddPodcastModule {
    private AddPodcastPresenter addPodcastPresenter;

    @Provides
    AddPodcastPresenter provideAddPodcastPresenter(ServiceConnection connection) {
        addPodcastPresenter = new AddPodcastPresenter();
        return addPodcastPresenter;
    }

    @Provides
    @Named("Add")
    ServiceConnection provideServiceConnection() {
        return new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder binder) {
                ModelService mainModel = ((ModelService.ModelServiceBinder) binder).getModel();
                addPodcastPresenter.onModelConnected(mainModel);
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                addPodcastPresenter.onModelDisconnected();
            }
        };
    }

}
