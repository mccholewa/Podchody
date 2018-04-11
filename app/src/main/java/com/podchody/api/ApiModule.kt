package com.podchody.api

/**
 * Created by Misiu on 26.03.2018.
 */

import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module class ApiModule {

    @Provides
    @Singleton
    fun provideFirebaseDatabase(): FirebaseDatabase {
    var firebaseDatabase = FirebaseDatabase.getInstance()
        //firebaseDatabase.setPersistenceEnabled(true)
        return firebaseDatabase
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun authFb(firebaseAuth:FirebaseAuth): AuthFb = AuthFbImpl(firebaseAuth)

    @Provides
    @Singleton
    fun proviedFireBaseAuthUI(): AuthUI = AuthUI.getInstance()
}