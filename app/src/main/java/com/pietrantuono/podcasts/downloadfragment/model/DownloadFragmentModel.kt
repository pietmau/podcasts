package com.pietrantuono.podcasts.downloadfragment.model

import rx.Observable

interface DownloadFragmentModel {
    fun getEpisodesWithDownload():Observable<>

}