package com.pietrantuono.podcasts.main.presenter

import com.pietrantuono.podcasts.main.view.MainView


interface KillSwitch {
    fun checkIfNeedsToBeKilled(view: MainView?)
    fun unsubscribe()
}