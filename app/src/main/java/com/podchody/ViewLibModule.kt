package com.podchody

/**
 * Created by Misiu on 08.01.2018.
 */

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.podchody.util.AndroidCoroutines
import com.podchody.util.Coroutines
import com.podchody.util.ViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module class ViewLibModule {

    @Provides @Singleton fun providePrefs(application: Application): SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(application)

    @Provides fun coroutines(): Coroutines = AndroidCoroutines()

    @Provides fun viewModelFactory() = ViewModelFactory()
}
