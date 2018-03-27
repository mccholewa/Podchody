package com.podchody.di

import com.podchody.MainActivity
import dagger.android.ContributesAndroidInjector
import dagger.Module

/**
 * Created by Misiu on 27.03.2018.
 */

@Module
abstract class AndroidInjectorActivityBindingModule {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity
}