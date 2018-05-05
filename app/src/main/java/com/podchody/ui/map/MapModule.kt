package com.podchody.ui.map

/**
 * Created by Misiu on 27.03.2018.
 */

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MapModule {

    @ContributesAndroidInjector
    abstract fun bindMapFragment(): MapFragment
}
