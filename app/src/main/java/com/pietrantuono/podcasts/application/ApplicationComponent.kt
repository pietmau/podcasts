package com.pietrantuono.podcasts.application


import com.google.android.exoplayer2.SimpleExoPlayer
import com.pietrantuono.podcasts.addpodcast.customviews.PodcastsRecycler
import com.pietrantuono.podcasts.addpodcast.dagger.ApiLevelCheckerlModule
import com.pietrantuono.podcasts.addpodcast.dagger.SearchModelsModule
import com.pietrantuono.podcasts.addpodcast.singlepodcast.customviews.EpisodesRecycler
import com.pietrantuono.podcasts.addpodcast.singlepodcast.dagger.SinglePodcastModule
import com.pietrantuono.podcasts.apis.ApiModule
import com.pietrantuono.podcasts.downloader.DownloadSubComponent
import com.pietrantuono.podcasts.downloader.di.DownloadModule
import com.pietrantuono.podcasts.main.dagger.ImageLoaderModule
import com.pietrantuono.podcasts.main.dagger.MainModule
import com.pietrantuono.podcasts.main.dagger.TransitionsModule
import com.pietrantuono.podcasts.media.MediaModule
import com.pietrantuono.podcasts.repository.RepositoryModule
import com.pietrantuono.podcasts.repository.SaveEpisodeIntentService
import com.pietrantuono.podcasts.subscribedpodcasts.detail.di.SingleSubscribedComponent
import com.pietrantuono.podcasts.subscribedpodcasts.detail.di.SingleSubscribedModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, ApiLevelCheckerlModule::class,
        SearchModelsModule::class, ImageLoaderModule::class, TransitionsModule::class,
        MediaModule::class, RepositoryModule::class, ApiModule::class))
interface ApplicationComponent {

    fun inject(app: App)

    fun inject(podcastsRecycler: PodcastsRecycler)

    fun inject(episodesRecycler: EpisodesRecycler)

    fun inject(saveEpisodeIntentService: SaveEpisodeIntentService)

    fun with(mainModule: MainModule): MainComponent

    fun with(singlePodcastModule: SinglePodcastModule): SinglePodcastComponent

    fun with(mainModule: SingleSubscribedModule): SingleSubscribedComponent

    fun with(downloadModule: DownloadModule): DownloadSubComponent

    fun simpleExoPlayer(): SimpleExoPlayer?

}
