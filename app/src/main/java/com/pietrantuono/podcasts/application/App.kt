package com.pietrantuono.podcasts.application

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import android.support.multidex.MultiDexApplication
import com.crashlytics.android.Crashlytics
import com.facebook.stetho.Stetho
import com.pietrantuono.podcasts.main.dagger.ImageLoaderModule
import com.squareup.leakcanary.LeakCanary
import io.fabric.sdk.android.Fabric
import io.realm.Realm
import io.realm.RealmConfiguration
import models.pojos.DataRealmLibraryModule
import javax.inject.Inject


class App : MultiDexApplication(), ServiceConnection {
    private var serviceIsBound: Boolean = false
    var applicationComponent: ApplicationComponent? = null
    @Inject lateinit var logger: DebugLogger

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this);
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        Fabric.with(applicationContext, Crashlytics())
        LeakCanary.install(this);
        Realm.init(this)
        applicationComponent = DaggerApplicationComponent.builder().appModule(AppModule(this))
                .imageLoaderModule(ImageLoaderModule(this)).build()
        applicationComponent?.inject(this)
        Realm.setDefaultConfiguration(RealmConfiguration.Builder()
                .name("app.realm")
                .modules(Realm.getDefaultModule(), DataRealmLibraryModule())
                .build())
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

    }

    override fun onServiceConnected(componentName: ComponentName?, iBinder: IBinder?) {

    }
}
