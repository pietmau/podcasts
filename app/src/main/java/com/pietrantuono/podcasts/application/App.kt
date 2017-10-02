package com.pietrantuono.podcasts.application

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import android.support.multidex.MultiDexApplication
import com.pietrantuono.podcasts.main.dagger.ImageLoaderModule
import com.pietrantuono.podcasts.player.player.player.Player
import io.realm.Realm
import javax.inject.Inject

class App : MultiDexApplication(), ServiceConnection {
    private var serviceIsBound: Boolean = false
    var applicationComponent: ApplicationComponent? = null
    var player: Player? = null
    @Inject lateinit var logger: DebugLogger

    override fun onCreate() {
        super.onCreate()
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            return;
//        }
//        LeakCanary.install(this);
        Realm.init(this)
        applicationComponent = DaggerApplicationComponent.builder().appModule(AppModule(this))
                .imageLoaderModule(ImageLoaderModule(this)).build()
        applicationComponent?.inject(this)
        startService()
    }

    private fun startService() {

    }

    fun bindPlayerService() {

    }

    fun unbindPlayerService() {
        if (serviceIsBound) {
            unbindService(this)
            serviceIsBound = false
        }
    }

    override fun onServiceDisconnected(componentName: ComponentName?) {
        player = null
    }

    override fun onServiceConnected(componentName: ComponentName?, iBinder: IBinder?) {
        player = iBinder as? Player
    }
}
