package com.podchody.ui.newgame

/**
 * Created by Misiu on 27.03.2018.
 */

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class NewgameModule {

    @ContributesAndroidInjector
    abstract fun bindNewgameFragment(): NewgameFragment
}
