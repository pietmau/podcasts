package com.pietrantuono.podcasts

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import butterknife.BindView
import butterknife.ButterKnife
import com.pietrantuono.podcasts.customcontrols.CustomControls

class Main2Activity : AppCompatActivity() {
    @BindView(R.id.controls) lateinit var controls:CustomControls

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        ButterKnife.bind(this)
    }

    override fun onStop() {
        super.onStop()
        controls.onStop()
    }

    override fun onStart() {
        super.onStart()
        controls.onStart()
    }
}
