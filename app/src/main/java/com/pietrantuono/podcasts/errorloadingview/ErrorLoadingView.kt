package com.pietrantuono.podcasts.errorloadingview

import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import butterknife.BindView
import butterknife.ButterKnife
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.State


class ErrorLoadingView<T : RecyclerView>(context: Context, attrs: AttributeSet) : SwipeRefreshLayout(context, attrs) {
    @BindView(R.id.layout) lateinit var relative: RelativeLayout
    @BindView(R.id.contaniner) lateinit var contaniner: LinearLayout
    @BindView(R.id.retry_button) lateinit var retryButton: Button

    var recycler: T? = null
        get() = field
        set(value) {
            field = value
            field?.let { addToLayout(it) }
        }

    private fun addToLayout(field: T) {
        val params = relative.layoutParams
        params.width = LayoutParams.MATCH_PARENT
        params.height = LayoutParams.MATCH_PARENT
        field.layoutParams = params
        relative.addView(field)
    }

    init {
        (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.loading_error_view, this, true);
        ButterKnife.bind(this)
    }

    fun setState(state: State) {
        when (state) {
            State.LOADING -> {
                recycler?.visibility = View.GONE
                this.isRefreshing = true
                contaniner.visibility = View.GONE
            }
            State.FULL -> {
                recycler?.visibility = View.VISIBLE
                this.isRefreshing = false
                contaniner.visibility = View.GONE
            }
            State.EMPTY -> {
                recycler?.visibility = View.GONE
                this.isRefreshing = false
                contaniner.visibility = View.VISIBLE
            }
            State.ERROR -> {
                recycler?.visibility = View.GONE
                this.isRefreshing = false
                contaniner.visibility = View.VISIBLE
            }
        }
    }

    fun thereIsAValidQuery(thereIsAValidQuery: Boolean) {
        this.isEnabled = thereIsAValidQuery
        if (thereIsAValidQuery) {
            retryButton.visibility = View.VISIBLE
        } else {
            retryButton.visibility = View.GONE
        }
    }

}