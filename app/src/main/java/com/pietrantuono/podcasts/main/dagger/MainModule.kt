package com.pietrantuono.podcasts.main.dagger

import android.content.Context
import com.google.firebase.database.FirebaseDatabase
import com.pietrantuono.podcasts.BuildConfig
import com.pietrantuono.podcasts.addpodcast.view.ApiLevelChecker
import com.pietrantuono.podcasts.main.presenter.*
import dagger.Module
import dagger.Provides
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

@Module
class MainModule {

    @Provides
    internal fun provideMainPresenter(checker: ApiLevelChecker, killSwitch: KillSwitch): MainPresenter = MainPresenter(killSwitch)

    @Provides
    internal fun provideKillSwitch(context: Context, checker: BuildTypeChecker): KillSwitch
            = KillSwitchFirebase(context.packageManager, FirebaseDatabase.getInstance().reference, BuildConfig.VERSION_CODE, Schedulers.io(), AndroidSchedulers.mainThread(), checker, SubscriptionsManager())

    @Provides
    internal fun provideBuildTypeChecker(): BuildTypeChecker = BuildTypeCheckerImpl()
}
