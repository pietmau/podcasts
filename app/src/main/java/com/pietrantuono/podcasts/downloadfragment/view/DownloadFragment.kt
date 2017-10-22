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
import com.pietrantuono.podcasts.downloadfragment.view.custom.DownloadRecycler
import com.pietrantuono.podcasts.downloadfragment.view.custom.DownloadedPodcast
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
}