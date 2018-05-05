package com.podchody.ui.newgameplayer

import com.podchody.ui.newgame.NewgameFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Misiu on 23.04.2018.
 */
@Module
abstract class NewgameplayerModule{

    @ContributesAndroidInjector
    abstract fun bindNewgameplayerFragment(): NewgameplayerFragment
}
