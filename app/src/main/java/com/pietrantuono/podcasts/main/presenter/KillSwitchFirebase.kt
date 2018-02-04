package com.pietrantuono.podcasts.main.presenter

import android.content.pm.PackageManager
import com.google.firebase.database.DatabaseReference
import com.kelvinapps.rxfirebase.RxFirebaseDatabase
import com.pietrantuono.podcasts.BuildConfig
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.main.view.MainView
import com.pietrantuono.podcasts.singlepodcast.presenter.SimpleObserver
import rx.Scheduler


class KillSwitchFirebase(
        private val packageManager: PackageManager,
        private val database: DatabaseReference,
        private val appVersionCode: Int,
        private val ioScheduler: Scheduler,
        private val mainThread: Scheduler,
        private val buildTypeChecker: BuildTypeChecker,
        private val compositeSubscription: SubscriptionsManager
) : KillSwitch {

    companion object {
        const val GOOGLE_PLAY: String = "com.android.vending"
        const val SUPPORTED_VERSIONS = "supported_versions"
        const val APP_IS_SUPPORTED = "app_is_supported"
    }

    private var view: MainView? = null

    override fun checkIfNeedsToBeKilled(view: MainView?) {
        this.view = view
        if (buildTypeChecker.isDebug()) {
            return
        }
        if (installedOutsideGooglePlay()) {
            showInstalledOutsideGooglePlay()
            return
        }
        compositeSubscription.add(RxFirebaseDatabase
                .observeSingleValueEvent(database.child(SUPPORTED_VERSIONS), { it?.value as? List<Int> })
                .flatMapIterable { it }
                .contains(appVersionCode.toLong())
                .filter { it == false }
                .subscribeOn(ioScheduler)
                .observeOn(mainThread)
                .subscribe(object : SimpleObserver<Boolean>() {
                    override fun onNext(versionSupported: Boolean?) {
                        showmustUpgrade()
                    }
                }))

        compositeSubscription.add(RxFirebaseDatabase
                .observeSingleValueEvent(database.child(APP_IS_SUPPORTED), { it?.value as? Boolean })
                .filter { it == false }
                .subscribeOn(ioScheduler)
                .observeOn(mainThread)
                .subscribe(object : SimpleObserver<Boolean>() {
                    override fun onNext(appIsSupported: Boolean?) {
                        showkillSwitchIsTurnedOn()
                    }
                }))
    }

    override fun unsubscribe() {
        compositeSubscription.unsubscribe()
    }

    private fun showmustUpgrade() {
        unsubscribe()
        val install_outside_google_play_messagge = R.string.please_upgrade_message
        val install_outside_google_play_title = R.string.please_upgrade__title
        startKillSwitchActivityAndFinishCurrentOne(install_outside_google_play_title, install_outside_google_play_messagge)
    }

    private fun showkillSwitchIsTurnedOn() {
        unsubscribe()
        val install_outside_google_play_messagge = R.string.app_not_supported_messagge
        val install_outside_google_play_title = R.string.app_not_supported_title
        startKillSwitchActivityAndFinishCurrentOne(install_outside_google_play_title, install_outside_google_play_messagge)
    }

    private fun showInstalledOutsideGooglePlay() {
        unsubscribe()
        val install_outside_google_play_messagge = R.string.install_outside_google_play_messagge
        val install_outside_google_play_title = R.string.install_outside_google_play_title
        startKillSwitchActivityAndFinishCurrentOne(install_outside_google_play_title, install_outside_google_play_messagge)
    }

    private fun startKillSwitchActivityAndFinishCurrentOne(title: Int, messagge: Int) {
        view?.startKillSwitchActivity(title, messagge)
        view?.finish()
    }

    private fun installedOutsideGooglePlay(): Boolean = !GOOGLE_PLAY.equals(packageManager.getInstallerPackageName(BuildConfig.APPLICATION_ID), true)

}