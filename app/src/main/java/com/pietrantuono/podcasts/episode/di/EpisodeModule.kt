package com.pietrantuono.podcasts.episode.di

import android.support.v7.app.AppCompatActivity
import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.TransitionImageLoadingListener
import com.pietrantuono.podcasts.episode.EpisodePresenter
import com.pietrantuono.podcasts.main.view.TransitionsFramework
import dagger.Module
import dagger.Provides

@Module
class EpisodeModule(private val activity: AppCompatActivity) {

    @Provides
    fun provideTransitionImageLoadingListener(framework: TransitionsFramework): TransitionImageLoadingListener {
        return TransitionImageLoadingListener(activity, framework)
    }

    @Provides
    fun provideEpisodePresenter(): EpisodePresenter {
        return EpisodePresenter()
    }

}
