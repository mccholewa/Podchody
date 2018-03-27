package com.podchody.di

import com.podchody.AndroidNavigationController
import com.podchody.NavigationController
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Misiu on 27.03.2018.
 */

@Module
class AppModule {
    @Provides
    @Singleton
    fun navigationController(androidNavigationController: AndroidNavigationController):
            NavigationController = androidNavigationController
}