package com.pietrantuono.podcasts.application


import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.pietrantuono.podcasts.main.dagger.ImageLoaderModule
import com.pietrantuono.podcasts.player.player.service.Player
import com.pietrantuono.podcasts.player.player.service.PlayerService
import com.squareup.leakcanary.LeakCanary
import io.realm.Realm


class App : Application(), ServiceConnection {
    var applicationComponent: ApplicationComponent? = null
    var player: Player? = null

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
        Realm.init(this)
        applicationComponent = DaggerApplicationComponent.builder().appModule(AppModule(this))
                .imageLoaderModule(ImageLoaderModule(this)).build()
        applicationComponent?.inject(this)
        bindPlayerService()
    }

    private fun bindPlayerService() {
        val intent = Intent(this, PlayerService::class.java)
        bindService(intent, this, Context.BIND_AUTO_CREATE)
    }

    override fun onServiceDisconnected(componentName: ComponentName?) {
        player = null
    }

    override fun onServiceConnected(componentName: ComponentName?, iBinder: IBinder?) {
        player = iBinder as Player
    }
}
