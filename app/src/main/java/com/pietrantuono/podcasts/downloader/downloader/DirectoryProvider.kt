package com.pietrantuono.podcasts.downloader.downloader


interface DirectoryProvider {
    val downloadDir: String
    val externalStorageIsAvailable: Boolean
    fun thereIsEnoughSpace(fileSize: Long): Boolean
}