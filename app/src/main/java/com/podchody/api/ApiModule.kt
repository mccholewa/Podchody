package com.podchody.api

/**
 * Created by Misiu on 26.03.2018.
 */


import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.podchody.util.AndroidCoroutines
import com.podchody.util.Coroutines
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module class AppModule {

    @Provides
    @Singleton
    fun provideFirebaseDatabase(): FirebaseDatabase = FirebaseDatabase.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

}