package com.pietrantuono.podcasts.main.presenter

import com.pietrantuono.podcasts.GenericPresenter
import com.pietrantuono.podcasts.addpodcast.view.ApiLevelChecker
import com.pietrantuono.podcasts.application.PermissionManager
import com.pietrantuono.podcasts.main.view.MainView

class MainPresenter(private val checker: ApiLevelChecker,private val permissionManager: PermissionManager) : GenericPresenter {
    private var view: MainView? = null

    fun bindView(view: MainView) {
        this.view = view
    }

    override fun onDestroy() {}

    fun onAddPodcastSelected() {
        view?.navigateToAddPodcast()
    }

    override fun onStop() {}

    override fun onStart() {

    }

    fun onCreate(activityRecreated: Boolean) {
        if (!activityRecreated) {
            view!!.navigateToSubscribedPodcasts()
        }
        if (checker.isMarshmallowOrHigher) {

        }
    }

    fun onSubscribeSelected() {
        view?.navigateToSubscribedPodcasts()
    }
}

