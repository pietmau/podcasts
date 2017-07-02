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
import io.realm.Realm


class App : Application(), ServiceConnection {
    var applicationComponent: ApplicationComponent? = null
        private set
    var player: Player? = null

    override fun onServiceDisconnected(componentName: ComponentName?) {
        player = null
    }

    override fun onServiceConnected(componentName: ComponentName?, iBinder: IBinder?) {
        player = iBinder as Player
    }

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        applicationComponent = DaggerApplicationComponent.builder().appModule(AppModule(this))
                .imageLoaderModule(ImageLoaderModule(this)).build()
        applicationComponent?.inject(this)
        val intent = Intent(this, PlayerService::class.java)
        bindService(intent, this, Context.BIND_AUTO_CREATE)
    }


}
