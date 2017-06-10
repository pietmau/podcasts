package com.pietrantuono.podcasts.singlepodcast.view.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import com.pietrantuono.podcasts.R


class SubscribedTextView(context: Context?, attrs: AttributeSet?) : TextView(context, attrs) {

    var isSubsribed: Boolean = false
        set(value) {
            if (value) {
                setText(R.string.unsubscribe)
                setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_cancel_, 0, 0, 0)
            } else {
                setText(R.string.subscribe)
                setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.add_circle, 0, 0, 0)
            }
        }
}