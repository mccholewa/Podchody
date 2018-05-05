package com.podchody.ui.mapplayer

/**
 * Created by Misiu on 04.05.2018.
 */
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MapplayerModule {

    @ContributesAndroidInjector
    abstract fun bindMapplayerFragment(): MapplayerFragment
}
