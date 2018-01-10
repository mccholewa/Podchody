package com.podchody

/**
 * Created by Misiu on 09.01.2018.
 */


import android.app.Application
import android.content.Context
import android.support.annotation.VisibleForTesting
import android.support.v4.app.Fragment
import com.podchody.di.AppComponent
import com.podchody.di.DaggerAppComponent
import timber.log.Timber


class PodchodyApp : Application() {

    @set:VisibleForTesting
    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        component = DaggerAppComponent.builder().application(this).build()
    }
}

val Context.component: AppComponent
    get() = (applicationContext as PodchodyApp).component

val Fragment.component: AppComponent
    get() = activity!!.component
