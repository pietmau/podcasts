package com.pietrantuono.podcasts.main.customviews

import android.content.Context
import android.support.design.widget.NavigationView
import android.util.AttributeSet

import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.main.presenter.MainPresenter

class SimpleNavView : NavigationView {
    private var listener: MainPresenter? = null
    private var drawerLayout: DrawerLayoutWithToggle? = null

    //TODO use jvm overloads
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        setNavigationItemSelectedListener { item ->
            drawerLayout?.closeDrawers()
            val id = item.itemId
            when (id) {
                R.id.add_podcast -> listener?.onAddPodcastSelected()
                R.id.subscribed -> listener?.onSubscribeSelected()
                R.id.settings -> listener?.onSettingsSelected()
                R.id.downloads -> listener?.onSettingsSelected()
            }
            true
        }
    }

    fun setMainPresenterListener(listener: MainPresenter) {
        this.listener = listener
    }

    fun setDrawer(drawerLayout: DrawerLayoutWithToggle) {
        this.drawerLayout = drawerLayout
    }
}
