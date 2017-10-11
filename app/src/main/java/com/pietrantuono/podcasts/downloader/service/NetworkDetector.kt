package com.pietrantuono.podcasts.downloader.service

import android.content.Context
import android.net.wifi.WifiManager


class NetworkDetector(private val context: Context) {
    val isWiFi: Boolean
        get() {
            val wifiMgr = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
            if (wifiMgr.isWifiEnabled) {
                val wifiInfo = wifiMgr.connectionInfo
                if (wifiInfo.networkId == -1) {
                    return false
                }
                return true
            } else {
                return false
            }
        }

}