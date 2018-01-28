package com.pietrantuono.podcasts.addpodcast.view

import android.annotation.TargetApi
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.util.Pair
import android.support.v7.widget.SearchView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.SimpleOnQueryTextListener
import com.pietrantuono.podcasts.addpodcast.customviews.PodcastsRecycler
import com.pietrantuono.podcasts.addpodcast.dagger.AddPodcastModule
import com.pietrantuono.podcasts.addpodcast.presenter.AddPodcastPresenter
import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.AddSinglePodcastActivity
import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.State
import com.pietrantuono.podcasts.errorloadingview.ErrorLoadingView
import com.pietrantuono.podcasts.main.view.MainActivity
import com.pietrantuono.podcasts.main.view.TransitionsHelper
import hugo.weaving.DebugLog
import models.pojos.Podcast
import javax.inject.Inject

class AddPodcastFragment : Fragment(), AddPodcastView {

    companion object {
        const val STATE = "view_state"
        private val TAG = AddPodcastFragment::class.java.simpleName
        fun navigateTo(fragmentManager: FragmentManager) {
            val frag = fragmentManager.findFragmentByTag(AddPodcastFragment.TAG) as? AddPodcastFragment ?: AddPodcastFragment()
            fragmentManager.beginTransaction().replace(R.id.fragmentContainer, frag, AddPodcastFragment.TAG).commit()
        }
    }

    @Inject lateinit var presenter: AddPodcastPresenter
    @Inject lateinit var transitions: TransitionsHelper
    @BindView(R.id.search_view) lateinit var searchView: SearchView
    @BindView(R.id.error_loading_view) lateinit var errorLoadingView: ErrorLoadingView<PodcastsRecycler>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).mainComponent?.with(AddPodcastModule(activity))?.inject(this)
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    @DebugLog
    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    @DebugLog
    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    @DebugLog
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_add, container, false)
        view?.let { ButterKnife.bind(this, it) }
        errorLoadingView.recycler = PodcastsRecycler(context)
        errorLoadingView.recycler?.setListeners(presenter)
        searchView.setOnQueryTextListener(object : SimpleOnQueryTextListener() {
            override fun onQueryTextSubmit(query: String?): Boolean {
                hideKeyboard()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return presenter.onQueryTextSubmit(newText)
            }
        })
        errorLoadingView.setOnRefreshListener { refresh() }
        activity.setTitle(R.string.find)
        presenter.bindView(this@AddPodcastFragment, savedInstanceState)
        return view
    }

    @DebugLog
    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putSerializable(STATE, presenter.viewState)
    }

    @DebugLog
    override fun updateSearchResults(items: List<Podcast>) {
        errorLoadingView.recycler?.setItems(items)
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun startDetailActivityWithTransition(podcast: Podcast, imageView: ImageView, titleContainer: LinearLayout) {
        val intent = Intent(activity, AddSinglePodcastActivity::class.java)
        intent.putExtra(AddSinglePodcastActivity.SINGLE_PODCAST_TRACK_ID, podcast)
        intent.putExtra(AddSinglePodcastActivity.STARTED_WITH_TRANSITION, true)
        activity.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(activity, *getPairs(imageView, titleContainer)).toBundle())
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun startDetailActivityWithoutTransition(podcast: Podcast) {
        val intent = Intent(activity, AddSinglePodcastActivity::class.java)
        intent.putExtra(AddSinglePodcastActivity.SINGLE_PODCAST_TRACK_ID, podcast)
        activity.startActivity(intent)
    }

    private fun getPairs(imageView: ImageView, titleContainer: LinearLayout): Array<Pair<View, String>?> {
        return transitions.getPairs(imageView, activity, titleContainer)
    }

    override fun isPartiallyHidden(position: Int): Boolean {
        return errorLoadingView.recycler?.isPartiallyHidden(position) == true
    }

    override fun setState(state: State) {
        errorLoadingView.setState(state)
        errorLoadingView.thereIsAValidQuery(presenter.thereIsAValidQuery())
    }

    @OnClick(R.id.retry_button)
    fun refresh() {
        presenter.refresh()
    }
}
