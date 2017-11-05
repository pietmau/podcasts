package com.pietrantuono.podcasts.main.killswitchactivity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.pietrantuono.podcasts.R


class KillSwitchActivity : AppCompatActivity() {

    companion object {
        val TITLE = "title"
        val MESSAGE = "message"
    }

    @BindView(R.id.kill_switch_title) lateinit var titleTextView: TextView
    @BindView(R.id.kill_switch_message) lateinit var messageTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.kill_switch);
        ButterKnife.bind(this);
        setFields()
    }

    private fun setFields() {
        val title = intent?.getIntExtra(TITLE, -1)
        if (title != null && title != -1) {
            titleTextView.setText(title)
        }
        val message = intent?.getIntExtra(MESSAGE, -1)
        if (message != null && message != -1) {
            messageTextView.setText(message)
        }
    }
}