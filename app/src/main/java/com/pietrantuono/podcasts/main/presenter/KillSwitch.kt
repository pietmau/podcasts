package com.pietrantuono.podcasts.main.presenter

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import com.pietrantuono.podcasts.BuildConfig
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.main.killswitchactivity.KillSwitchActivity
import com.pietrantuono.podcasts.main.view.MainView

class KillSwitch(private val packageManager: PackageManager) {
    private val DEBUG: String? = "debug"
    private val GOOGLE_PLAY: String? = "com.android.vending"

    fun checkIfNeedsToBeKilled(view: MainView) {
//        if (isDebug()) {
//            return
//        }
        if (installedOutsideGooglePlay()) {
            showInstalledOutsideGooglePlay(view)
            view.finish()
            return
        }
        if (killSwitchIsTurnedOn()) {
            showkillSwitchIsTurnedOn(view)
            view.finish()
            return
        }

        if (mustUpgrade()) {
            showmustUpgrade(view)
            view.finish()
            return
        }
    }


    private fun isDebug(): Boolean = DEBUG.equals(BuildConfig.BUILD_TYPE, true)

    private fun showmustUpgrade(view: MainView) {
    }

    private fun mustUpgrade(): Boolean {
        TODO("not implemented")
    }

    private fun showkillSwitchIsTurnedOn(view: MainView) {
        TODO("not implemented")
    }

    private fun killSwitchIsTurnedOn(): Boolean {
        TODO("not implemented")
    }

    private fun showInstalledOutsideGooglePlay(view: MainView) {
        val context = view as Context
        val intent = Intent(context, KillSwitchActivity::class.java)
        intent.putExtra(KillSwitchActivity.TITLE, R.string.install_outside_google_play_title)
        intent.putExtra(KillSwitchActivity.MESSAGE, R.string.install_outside_google_play_messagge)
        context.startActivity(intent)
    }

    private fun installedOutsideGooglePlay(): Boolean = !GOOGLE_PLAY.equals(packageManager.getInstallerPackageName(BuildConfig.APPLICATION_ID), true)

}