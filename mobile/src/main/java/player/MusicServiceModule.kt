package player

import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import com.example.android.uamp.R
import player.model.MusicProvider
import com.example.android.uamp.model.MusicProviderRealm
import player.playback.LocalPlayback
import player.playback.Playback
import player.playback.PlaybackManager
import player.playback.QueueManager
import player.utils.QueueHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Module
class MusicServiceModule(private val musicService: MusicService) {
    private val TAG: String = "MusicServiceModule"

    val context = musicService.applicationContext

    @Singleton
    @Provides
    fun providesMusicProvider(): MusicProvider = MusicProviderRealm()

    @Provides
    fun providesPackageValidator() = PackageValidator(context)

    @Singleton
    @Provides
    fun providesLocalPlayback(mMusicProvider: MusicProvider): Playback = LocalPlayback(context, mMusicProvider)

    @Singleton
    @Provides
    fun providesQueueHelper(): QueueHelper = QueueHelperRealm()

    @Singleton
    @Provides
    fun providesMediaSessionCompat() = MediaSessionCompat(context, "MusicService")

    @Singleton
    @Provides
    fun providesPlaybackManager(musicProvider: MusicProvider, playback: Playback, mMusicProvider:
    MusicProvider, mSession: MediaSessionCompat, queueHelper: QueueHelper): PlaybackManager {
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

                    override fun onQueueUpdated(title: String, newQueue: List<MediaSessionCompat.QueueItem>) {
                        mSession.setQueue(newQueue)
                        mSession.setQueueTitle(title)
                    }
                }, queueHelper)
        manager = PlaybackManager(musicService, context.getResources(), musicProvider, queueManager, playback);
        return manager!!
    }

    @Provides
    fun provideDelayedStopHandler() = DelayedStopHandler()

    @Provides
    fun provideMusicServicePresenter(mSession: MediaSessionCompat, mPlaybackManager: PlaybackManager,
                                     mDelayedStopHandler: DelayedStopHandler) = MusicServicePresenter(mSession, mPlaybackManager, mDelayedStopHandler)

}