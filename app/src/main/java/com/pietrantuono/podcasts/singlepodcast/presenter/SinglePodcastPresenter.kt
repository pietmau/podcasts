package com.pietrantuono.podcasts.singlepodcast.presenter


import android.content.ComponentName
import android.content.Context
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaControllerCompat
import com.pietrantuono.CrashlyticsWrapper
import com.pietrantuono.podcasts.GenericPresenter
import com.pietrantuono.podcasts.MediaPlaybackService
import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast
import com.pietrantuono.podcasts.apis.PodcastFeed
import com.pietrantuono.podcasts.singlepodcast.model.SinglePodcastModel
import com.pietrantuono.podcasts.singlepodcast.view.SinglePodcastView
import rx.Observer


class SinglePodcastPresenter(private val model: SinglePodcastModel, private val crashlyticsWrapper:
CrashlyticsWrapper, val context: Context) : GenericPresenter {
    private var view: SinglePodcastView? = null
    private var podcastFeed: PodcastFeed? = null
    private var startedWithTransition: Boolean = false
    private val observer: SimpleObserver<Boolean>
    private var mediaBrowser: MediaBrowserCompat? = null
    private val mConnectionCallbacks: MediaBrowserCompat.ConnectionCallback
    private var mediaController: MediaControllerCompat? = null

    init {
        observer = object : SimpleObserver<Boolean>() {
            override fun onNext(isSubscribedToPodcast: Boolean?) {
                view!!.setSubscribedToPodcast(isSubscribedToPodcast)
            }
        }
        mConnectionCallbacks = object : MediaBrowserCompat.ConnectionCallback() {
            override fun onConnectionFailed() {

            }

            override fun onConnectionSuspended() {

            }

            override fun onConnected() {
                // Get the token for the MediaSession
                val token = mediaBrowser?.getSessionToken()

                // Create a MediaControllerCompat
                mediaController = MediaControllerCompat(context, // Context
                        token)

                // Save the controller
                // MediaControllerCompat.setMediaController(context, mediaController)
            }
        }
    }

    override fun onDestroy() {
        view = null
    }

    override fun onStop() {
        model.unsubscribe()
        // (see "stay in sync with the MediaSession")
//        if (MediaControllerCompat.getMediaController(MediaPlayerActivity.this) != null) {
//            MediaControllerCompat.getMediaController(MediaPlayerActivity.this).unregisterCallback(controllerCallback);
//        }
        mediaBrowser?.disconnect();
    }

    override fun onStart() {
        model.subscribeToFeed(object : Observer<PodcastFeed> {
            override fun onCompleted() {
                view!!.showProgress(false)
            }

            override fun onError(e: Throwable) {
                crashlyticsWrapper.logException(e)
                view!!.showProgress(false)
            }

            override fun onNext(podcastFeed: PodcastFeed) {
                if (this@SinglePodcastPresenter.podcastFeed == null) {
                    this@SinglePodcastPresenter.podcastFeed = podcastFeed
                    setEpisodes()
                }
            }
        })
        model.subscribeToIsSubscribedToPodcast(observer)
        mediaBrowser?.connect();
    }

    fun bindView(view: SinglePodcastView) {
        this.view = view
        setEpisodes()
    }

    fun startPresenter(podcast: SinglePodcast, startedWithTransition: Boolean) {
        this.startedWithTransition = startedWithTransition
        model.init(podcast)
        if (startedWithTransition) {
            view!!.enterWithTransition()
        } else {
            view!!.enterWithoutTransition()
        }
        mediaBrowser = MediaBrowserCompat(context,
                ComponentName(context, MediaPlaybackService::class.java),
                mConnectionCallbacks,
                null) // optional Bundle
    }

    private fun setEpisodes() {
        if (view == null || podcastFeed == null) {
            return
        }
        view!!.setEpisodes(podcastFeed!!.episodes)
    }

    fun onBackPressed() {
        if (startedWithTransition) {
            view!!.exitWithSharedTrsnsition()
        } else {
            view!!.exitWithoutSharedTransition()
        }
    }

    fun onSubscribeUnsubscribeToPodcastClicked() {
        model.onSubscribeUnsubscribeToPodcastClicked()
    }

    fun onDownloadAllPressed() {

    }

    fun onListenToAllPressed() {
        //view?.listenToAll(podcastFeed);
        mediaController?.getTransportControls()?.play();
    }

    companion object {
        val TAG = SinglePodcastPresenter::class.java.simpleName
    }
}
