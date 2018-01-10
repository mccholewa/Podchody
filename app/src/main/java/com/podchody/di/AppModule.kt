package com.podchody.di

/**
 * Created by Misiu on 08.01.2018.
 */

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.podchody.util.AndroidCoroutines
import com.podchody.util.Coroutines
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module class AppModule {

    @Provides fun providePrefs(application: Application): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(application)

    @Provides fun coroutines(): Coroutines = AndroidCoroutines()
}
