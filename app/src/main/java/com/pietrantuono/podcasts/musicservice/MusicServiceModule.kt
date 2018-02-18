package com.pietrantuono.podcasts.musicservice

import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import com.example.android.uamp.R
import com.example.android.uamp.model.MusicProviderRealm
import dagger.Module
import dagger.Provides
import player.OtherActions
import player.PackageValidator
import player.QueueHelperRealm
import player.model.MusicProvider
import player.model.SourceExtractor
import player.model.SourceExtractorImpl
import player.playback.*
import player.utils.QueueHelper
import repository.EpisodesRepository
import repository.PodcastRepo

@MusicServiceScope
@Module
class MusicServiceModule(private val musicService: com.pietrantuono.podcasts.musicservice.MusicService) {
    val context = musicService.applicationContext

    @MusicServiceScope
    @Provides
    fun providesMusicProvider(sourceExtractor: SourceExtractor, episodeRepo: EpisodesRepository, podcastRepo: PodcastRepo): MusicProvider = MusicProviderRealm(episodeRepo, podcastRepo, sourceExtractor)

    @Provides
    fun providesSourceExtractor(): SourceExtractor = SourceExtractorImpl()

    @Provides
    fun providesPackageValidator() = PackageValidator(context)

    @MusicServiceScope
    @Provides
    fun providesLocalPlayback(mMusicProvider: MusicProvider): Playback = LocalPlayback(context, mMusicProvider)

    @MusicServiceScope
    @Provides
    fun providesQueueHelper(): QueueHelper = QueueHelperRealm()

    @MusicServiceScope
    @Provides
    fun providesMediaSessionCompat() = MediaSessionCompat(context, "MusicService")

    @MusicServiceScope
    @Provides
    fun providesPlaybackManager(playback: Playback, mMusicProvider: MusicProvider, mSession: MediaSessionCompat, queueHelper: QueueHelper, playbackStateUpdater: PlaybackStateUpdater, customActionResolver: CustomActionResolver): PlaybackManager {
        var manager: PlaybackManager? = null
        val queueManager = QueueManager(mMusicProvider, context.getResources(),
                object : QueueManager.MetadataUpdateListener {
                    override fun onMetadataChanged(metadata: MediaMetadataCompat) {
                        mSession.setMetadata(metadata)
                    }

                    override fun onMetadataRetrieveError() {
                        manager?.updatePlaybackState(musicService.getString(R.string.error_no_metadata))
                    }

                    override fun onCurrentQueueIndexUpdated(queueIndex: Int) {
                        manager?.handlePlayRequest()
                    }

                    override fun onQueueUpdated(title: String, newQueue: List<MediaSessionCompat.QueueItem>?) {
                        mSession.setQueue(newQueue)
                        mSession.setQueueTitle(title)
                    }
                }, queueHelper)
        manager = PlaybackManager(musicService, queueManager, playback, playbackStateUpdater, customActionResolver);
        return manager!!
    }

    @MusicServiceScope
    @Provides
    fun providePlaybackStateUpdater(provider: MusicProvider, playback: Playback, repo: EpisodesRepository) = PlaybackStateUpdater(provider, context.getResources(), playback, musicService, repo)

    @Provides
    fun provideDelayedStopHandler(playback: Playback) = DelayedStopHandler(playback)

    @Provides
    fun provideMusicServicePresenter(mSession: MediaSessionCompat, mPlaybackManager: PlaybackManager, mDelayedStopHandler: DelayedStopHandler, otherActions: OtherActions)
            = MusicServicePresenter(mSession, mPlaybackManager, mDelayedStopHandler, otherActions)

    @Provides
    fun provideCustomActionResolver() = CustomActionResolver()

    @Provides
    fun provideOtherActions() = OtherActions()
}