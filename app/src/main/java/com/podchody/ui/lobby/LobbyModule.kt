package com.podchody.ui.lobby

import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Misiu on 03.04.2018.
 */



@Module
abstract class LobbyModule {

    @ContributesAndroidInjector
    abstract fun bindLobbyragment(): LobbyFragment
}