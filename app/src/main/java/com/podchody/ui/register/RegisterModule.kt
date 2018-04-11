package com.podchody.ui.register

/**
 * Created by Misiu on 27.03.2018.
 */

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import com.podchody.ui.common.getParam

@Module
abstract class RegisterModule {

    @ContributesAndroidInjector
    abstract fun bindRegisterFragment(): RegisterFragment
}