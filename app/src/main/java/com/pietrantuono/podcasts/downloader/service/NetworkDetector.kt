package com.pietrantuono.podcasts.downloader.service

import android.content.Context
import android.net.wifi.WifiManager
import com.pietrantuono.podcasts.settings.PreferencesManager


class NetworkDetector(
        private val context: Context,
        private val preferencesManager: PreferencesManager) {
    private val isWiFi: Boolean?
        get() = (context.getSystemService(Context.WIFI_SERVICE) as? WifiManager)?.let {
            if (it.isWifiEnabled) {
                it.connectionInfo.networkId != -1
            } else {
                false
            }
        }

    val shouldDownload: Boolean
        get() = (isWiFi == true) || preferencesManager.downloadOnMobileNetwork


}