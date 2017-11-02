package com.pietrantuono.podcasts.settings

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager


class PreferencesManagerImpl(private val context: Context) : PreferencesManager {
    override val saveOnSdCard: Boolean
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    private val DOWNLOAD_ON_MOBILE_NETWORK: String? = "download_on_mobile_network"
    val sharedPref: SharedPreferences

    init {
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
    }

    override val downloadOnMobileNetwork: Boolean
        get() = downloadOnMobileNetwork()
    override val downloadDir: String
        get() = context.getFilesDir().absolutePath

    fun downloadOnMobileNetwork() = sharedPref.getBoolean(DOWNLOAD_ON_MOBILE_NETWORK, false)

}