package com.podchody.ui.message

/**
 * Created by Misiu on 27.03.2018.
 */

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MessageModule {

    @ContributesAndroidInjector
    abstract fun bindLoginFragment(): MessageFragment
}
