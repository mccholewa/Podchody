package com.podchody

/**
 * Created by Misiu on 09.01.2018.
 */


import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import com.podchody.di.DaggerAppComponent
import timber.log.Timber


class PodchodyApp : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            //timber plant
            Timber.plant(Timber.DebugTree())
        }
    }
}


////Retrieve the Dagger Component from Context
//val Context.component : AppComponent
//    get() = (applicationContext as PodchodyApp).component
//
////Retrieve the Dagger Component from Fragment
//val Fragment.component: AppComponent
//    get() = activity!!.component
