package com.podchody.api

/**
 * Created by Misiu on 26.03.2018.
 */

import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module class ApiModule {

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
    var firebaseFirestore = FirebaseFirestore.getInstance()
        //firebaseDatabase.setPersistenceEnabled(true)
        return firebaseFirestore
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

//    @Provides
//    @Singleton
//    fun provideFirebaseDatabaseRepository(firebaseDatabase: FirebaseDatabase) = (firebaseDatabase)

    @Provides
    @Singleton
    fun proviedFireBaseAuthUI(): AuthUI = AuthUI.getInstance()
}