package com.pietrantuono.podcasts.main.presenter

import com.pietrantuono.podcasts.GenericPresenter
import com.pietrantuono.podcasts.main.view.MainView

class MainPresenter(private val killSwich: KillSwitch) : GenericPresenter {

    private var view: MainView? = null

    fun bindView(view: MainView) {
        this.view = view
    }

    override fun onDestroy() {}

    fun onAddPodcastSelected() {
        view?.navigateToAddPodcast()
    }

    override fun onPause() {
        killSwich.unsubscribe()
    }

    override fun onResume() {
        killSwich.checkIfNeedsToBeKilled(view)
    }

    fun onCreate(activityRecreated: Boolean) {
        if (!activityRecreated) {
            view?.navigateToSubscribedPodcasts()
        }
    }

    fun onSubscribeSelected() {
        view?.navigateToSubscribedPodcasts()
    }

    fun onSettingsSelected() {
        view?.navigateToSettings()
    }

    fun onDownloadsSelected() {
        view?.navigateToDownloads()
    }

}

