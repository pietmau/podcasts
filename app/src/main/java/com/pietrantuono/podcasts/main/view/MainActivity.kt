package com.pietrantuono.podcasts.main.view

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.widget.Toolbar
import butterknife.BindView
import butterknife.ButterKnife
import com.crashlytics.android.Crashlytics
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.AbstractPlaybackControlsActivity
import com.pietrantuono.podcasts.addpodcast.view.AddPodcastFragment
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.application.MainComponent
import com.pietrantuono.podcasts.downloadfragment.view.DownloadFragment
import com.pietrantuono.podcasts.main.customviews.DrawerLayoutWithToggle
import com.pietrantuono.podcasts.main.customviews.SimpleNavView
import com.pietrantuono.podcasts.main.dagger.MainModule
import com.pietrantuono.podcasts.main.killswitchactivity.KillSwitchActivity
import com.pietrantuono.podcasts.main.presenter.MainPresenter
import com.pietrantuono.podcasts.playlist.view.PlaylistFragment
import com.pietrantuono.podcasts.settings.fragment.SettingsFragment
import com.pietrantuono.podcasts.subscribedpodcasts.view.SubscribedPodcastFragment
import io.fabric.sdk.android.Fabric
import javax.inject.Inject

class MainActivity : AbstractPlaybackControlsActivity(), MainView {

    @Inject lateinit var mainPresenter: MainPresenter
    @Inject lateinit var transitions: TransitionsHelper
    @BindView(R.id.drawer) lateinit var drawerLayoutWithToggle: DrawerLayoutWithToggle
    @BindView(R.id.maintoolbar) lateinit var mainToolbar: Toolbar
    @BindView(R.id.nav_view) lateinit var simpleNavView: SimpleNavView
    private var fragmentManager: FragmentManager = supportFragmentManager
    var mainComponent: MainComponent? = null
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        initDependencies()
        super.onCreate(savedInstanceState)
        Fabric.with(applicationContext, Crashlytics())
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this@MainActivity)
        setUpViews()
        mainPresenter.bindView(this@MainActivity)
        mainPresenter.onCreate(savedInstanceState != null)
        transitions.initMainActivityTransitions(this@MainActivity)
        initPlaybackControls()
    }

    private fun initDependencies() {
        mainComponent = (applicationContext as App).applicationComponent?.with(MainModule())
        mainComponent?.inject(this)
    }

    private fun setUpViews() {
        setSupportActionBar(mainToolbar)
        drawerLayoutWithToggle.addToolbar(mainToolbar)
        simpleNavView.setMainPresenterListener(mainPresenter)
        simpleNavView.setDrawer(drawerLayoutWithToggle)
    }

    override fun onPause() {
        super.onPause()
        mainPresenter.onPause()
    }

    override fun onResume() {
        super.onResume()
        mainPresenter.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        mainPresenter.onDestroy()
    }

    override fun navigateToAddPodcast() {
        AddPodcastFragment.navigateTo(fragmentManager)
        setTitle(R.string.find)
    }

    override fun navigateToSubscribedPodcasts() {
        SubscribedPodcastFragment.navigateTo(fragmentManager)
        setTitle(R.string.subscribed_podcasts)
    }

    override fun navigateToSettings() {
        var frag: Fragment? = fragmentManager.findFragmentByTag(SettingsFragment.TAG)
        if (frag == null) {
            frag = SettingsFragment()
        }
        fragmentManager.beginTransaction()?.replace(R.id.fragmentContainer, frag, SettingsFragment.TAG)?.commit()
        setTitle(R.string.settings)
    }

    override fun navigateToDownloads() {
        DownloadFragment.navigateToDownloads(fragmentManager)
        setTitle(R.string.downloads)
    }

    override fun navigateToPlaylist() {
        PlaylistFragment.navigateToPlaylist(fragmentManager)
        setTitle(R.string.playlist)
    }

    override fun startKillSwitchActivity(title: Int, messagge: Int) {
        val intent = Intent(this, KillSwitchActivity::class.java)
        intent.putExtra(KillSwitchActivity.TITLE, title)
        intent.putExtra(KillSwitchActivity.MESSAGE, messagge)
        startActivity(intent)
    }

}
