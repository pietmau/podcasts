package com.pietrantuono.podcasts.main.presenter

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import com.google.firebase.database.DatabaseReference
import com.kelvinapps.rxfirebase.RxFirebaseDatabase
import com.pietrantuono.podcasts.BuildConfig
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.singlepodcast.presenter.SimpleObserver
import com.pietrantuono.podcasts.main.killswitchactivity.KillSwitchActivity
import com.pietrantuono.podcasts.main.view.MainView
import rx.Scheduler
import rx.subscriptions.CompositeSubscription
import java.util.*

private const val SUPPORTED_VERSIONS = "supported_versions"
private const val APP_IS_SUPPORTED = "app_is_supported"

class KillSwitchFirebase(
        private val packageManager: PackageManager,
        private val databaseReference: DatabaseReference,
        private val appVersionCode: Int,
        private val io: Scheduler,
        private val mainThread: Scheduler
) : KillSwitch {

    private val DEBUG: String = "debug"
    private val GOOGLE_PLAY: String = "com.android.vending"
    private val compositeSubscription = CompositeSubscription()
    private var view: MainView? = null

    override fun checkIfNeedsToBeKilled(view: MainView?) {
        this.view = view
        if (isDebug()) {
            return
        }
        if (installedOutsideGooglePlay()) {
            showInstalledOutsideGooglePlay()
            return
        }
        compositeSubscription.add(RxFirebaseDatabase
                .observeSingleValueEvent(databaseReference.child(SUPPORTED_VERSIONS), { it?.value as? ArrayList<Int> })
                .flatMapIterable { it }
                .contains(appVersionCode.toLong())
                .filter { it == false }
                .observeOn(io)
                .subscribeOn(mainThread)
                .subscribe(object : SimpleObserver<Boolean>() {
                    override fun onNext(versionSupported: Boolean?) {
                        showmustUpgrade()
                    }
                }))

        compositeSubscription.add(RxFirebaseDatabase
                .observeSingleValueEvent(databaseReference.child(APP_IS_SUPPORTED), { it?.value as? Boolean })
                .filter { it == false }
                .observeOn(io)
                .subscribeOn(mainThread)
                .subscribe(object : SimpleObserver<Boolean>() {
                    override fun onNext(appIsSupported: Boolean?) {
                        showkillSwitchIsTurnedOn()
                    }
                }))
    }

    override fun unsubscribe() {
        compositeSubscription.unsubscribe()
    }

    private fun isDebug(): Boolean = DEBUG.equals(BuildConfig.BUILD_TYPE, true)

    private fun showmustUpgrade() {
        val install_outside_google_play_messagge = R.string.please_upgrade_message
        val install_outside_google_play_title = R.string.please_upgrade__title
        startActivity(install_outside_google_play_title, install_outside_google_play_messagge)
        this@KillSwitchFirebase.view?.finish()
    }

    private fun showkillSwitchIsTurnedOn() {
        val install_outside_google_play_messagge = R.string.app_not_supported_messagge
        val install_outside_google_play_title = R.string.app_not_supported_title
        startActivity(install_outside_google_play_title, install_outside_google_play_messagge)
        this@KillSwitchFirebase.view?.finish()
    }

    private fun showInstalledOutsideGooglePlay() {
        val install_outside_google_play_messagge = R.string.install_outside_google_play_messagge
        val install_outside_google_play_title = R.string.install_outside_google_play_title
        startActivity(install_outside_google_play_title, install_outside_google_play_messagge)
        view?.finish()
    }

    private fun startActivity(title: Int, messagge: Int) {
        (view as? Activity)?.let {
            val intent = Intent(it, KillSwitchActivity::class.java)
            intent.putExtra(KillSwitchActivity.TITLE, title)
            intent.putExtra(KillSwitchActivity.MESSAGE, messagge)
            it.startActivity(intent)
        }
    }

    private fun installedOutsideGooglePlay(): Boolean = !GOOGLE_PLAY.equals(packageManager.getInstallerPackageName(BuildConfig.APPLICATION_ID), true)

}