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
import com.pietrantuono.podcasts.downloadfragment.di.DownloadFragmentComponent
import com.pietrantuono.podcasts.downloadfragment.di.DownloadFragmentModule
import com.pietrantuono.podcasts.fullscreenplay.di.FullscreenModule
import com.pietrantuono.podcasts.fullscreenplay.di.FullscreenSubComponent
import com.pietrantuono.podcasts.imageloader.SimpleImageLoader
import com.pietrantuono.podcasts.main.dagger.ImageLoaderModule
import com.pietrantuono.podcasts.main.dagger.MainModule
import com.pietrantuono.podcasts.main.dagger.TransitionsModule
import com.pietrantuono.podcasts.media.MediaModule
import com.pietrantuono.podcasts.player.player.service.di.ServiceComponent
import com.pietrantuono.podcasts.player.player.service.di.ServiceModule
import com.pietrantuono.podcasts.repository.RepositoryModule
import com.pietrantuono.podcasts.repository.SaveAndDowloandEpisodeIntentService
import com.pietrantuono.podcasts.settings.di.SettingsFragmentComponent
import com.pietrantuono.podcasts.settings.di.SettingsFragmentModule
import com.pietrantuono.podcasts.settings.di.SettingsModule
import com.pietrantuono.podcasts.subscribedpodcasts.list.di.SingleSubscribedComponent
import com.pietrantuono.podcasts.subscribedpodcasts.list.di.SingleSubscribedModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, ApiLevelCheckerlModule::class,
        SearchModelsModule::class, ImageLoaderModule::class, TransitionsModule::class,
        MediaModule::class, RepositoryModule::class, ApiModule::class, SettingsModule::class,
        PlayerModule::class))
interface ApplicationComponent {

    fun inject(app: App)

    fun inject(podcastsRecycler: PodcastsRecycler)

    fun inject(episodesRecycler: EpisodesRecycler)

    fun inject(saveEpisodeIntentService: SaveAndDowloandEpisodeIntentService)

    fun with(mainModule: MainModule): MainComponent

    fun with(singlePodcastModule: SinglePodcastModule): SinglePodcastComponent

    fun with(mainModule: SingleSubscribedModule): SingleSubscribedComponent

    fun with(downloadModule: DownloadModule): DownloadSubComponent

    fun simpleExoPlayer(): SimpleExoPlayer?

    fun simpleImageLoader(): SimpleImageLoader

    fun with(fullscreenModule: FullscreenModule): FullscreenSubComponent

    fun with(serviceModule: ServiceModule): ServiceComponent

    fun with(serviceModule: DownloadFragmentModule): DownloadFragmentComponent

    fun with(serviceModule: SettingsFragmentModule): SettingsFragmentComponent
}
