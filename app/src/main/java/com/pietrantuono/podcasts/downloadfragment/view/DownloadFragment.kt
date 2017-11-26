package com.pietrantuono.podcasts.downloadfragment.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.pietrantuono.podcasts.R

import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.downloadfragment.di.DownloadFragmentModule
import com.pietrantuono.podcasts.downloadfragment.presenter.DownloadFragmentPresenter
import com.pietrantuono.podcasts.downloadfragment.presenter.MessageCreator
import com.pietrantuono.podcasts.downloadfragment.view.custom.DownloadRecycler
import com.pietrantuono.podcasts.downloadfragment.view.custom.DownloadedPodcast
import org.jetbrains.anko.noButton
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.yesButton
import diocan.pojos.Episode
import diocan.pojos.Podcast
import javax.inject.Inject


class DownloadFragment : Fragment(), DownloadView {
    @Inject lateinit var presenter: DownloadFragmentPresenter
    @BindView(R.id.download_recycler) lateinit var recycler: DownloadRecycler

    companion object {
        val TAG = "DownloadFragment"
        fun navigateToDownloads(fragmentManager: FragmentManager) {
            var frag = fragmentManager.findFragmentByTag(TAG) ?: DownloadFragment()
            fragmentManager.beginTransaction()?.replace(R.id.fragmentContainer, frag, TAG)?.commit()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_downloads, container, false)
        ButterKnife.bind(this, view)
        presenter.bindView(this)
        recycler.callback = presenter
        return view
    }

    override fun onPause() {
        super.onPause()
        presenter.unbind()
    }

    override fun onResume() {
        super.onResume()
        presenter.bind()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity.applicationContext as? App)?.applicationComponent?.with(DownloadFragmentModule())?.inject(this)
    }

    override fun setPodcasts(list: List<DownloadedPodcast>) {
        recycler.setData(list.toMutableList())
    }

    override fun confirmDownloadEpisode(message: MessageCreator.AlertMessage, link: String) {
        alert(message.message, message.title) {
            yesButton { presenter.onConfirmDownloadEpisode(link) }
            noButton { }
        }.show()
    }

    override fun confirmDownloadAllEpisodes(message: MessageCreator.AlertMessage, podcast: Podcast) {
        alert(message.message, message.title) {
            yesButton { presenter.onConfirmDownloadAllEpisodes(podcast) }
            noButton { }
        }.show()
    }

    override fun confirmDeleteEpisode(message: MessageCreator.AlertMessage, episode: Episode) {
        alert(message.message, message.title) {
            yesButton { presenter.onConfirmDeleteEpisode(episode) }
            noButton { }
        }.show()
    }

    override fun confirmDeleteAllEpisodes(message: MessageCreator.AlertMessage, podcast: Podcast) {
        alert(message.message, message.title) {
            yesButton { presenter.onConfirmDeleteAllEpisodes(podcast) }
            noButton { }
        }.show()
    }
}