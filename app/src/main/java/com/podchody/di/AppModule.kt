package com.podchody.di


import android.content.res.Resources
import com.podchody.AndroidNavigationController
import com.podchody.NavigationController
import dagger.Module
import dagger.Provides
import dagger.android.DaggerApplication
import javax.inject.Singleton
import android.app.Application
import android.content.Context
import com.podchody.PodchodyApp
import dagger.Binds
import dagger.android.AndroidInjectionModule


/**
 * Created by Misiu on 27.03.2018.
 */

@Module
class AppModule{
    @Provides
    @Singleton
    fun navigationController(androidNavigationController: AndroidNavigationController):
            NavigationController = androidNavigationController

}