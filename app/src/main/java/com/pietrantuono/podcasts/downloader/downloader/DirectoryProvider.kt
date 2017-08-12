package com.pietrantuono.podcasts.downloader.downloader


interface DirectoryProvider {
    val downloadDir: String
    fun thereIsEnoughSpace(fileSize: Long): Boolean
}