package com.pietrantuono.podcasts.downloadfragment.presenter

import com.pietrantuono.podcasts.downloadfragment.model.DownloadFragmentModel
import com.pietrantuono.podcasts.downloadfragment.model.EpisodesWithDownloads
import com.pietrantuono.podcasts.downloadfragment.view.DownloadView
import rx.Observable


class DownloadFragmentPresenter(private val model: DownloadFragmentModel) {
    private var view: DownloadView? = null

    init {
        model.getEpisodesWithDownload()
    }

    fun bindView(view: DownloadView) {
        this.view = view
    }

    fun subscribe(): Observable<EpisodesWithDownloads> {

    }
}

